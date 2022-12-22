package rocks.zipcode.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import rocks.zipcode.domain.Bus;
import rocks.zipcode.repository.BusRepository;
import rocks.zipcode.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link rocks.zipcode.domain.Bus}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BusResource {

    private final Logger log = LoggerFactory.getLogger(BusResource.class);

    private static final String ENTITY_NAME = "bus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BusRepository busRepository;

    public BusResource(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    /**
     * {@code POST  /buses} : Create a new bus.
     *
     * @param bus the bus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bus, or with status {@code 400 (Bad Request)} if the bus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/buses")
    public ResponseEntity<Bus> createBus(@RequestBody Bus bus) throws URISyntaxException {
        log.debug("REST request to save Bus : {}", bus);
        if (bus.getId() != null) {
            throw new BadRequestAlertException("A new bus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Bus result = busRepository.save(bus);
        return ResponseEntity
            .created(new URI("/api/buses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /buses/:id} : Updates an existing bus.
     *
     * @param id the id of the bus to save.
     * @param bus the bus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bus,
     * or with status {@code 400 (Bad Request)} if the bus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/buses/{id}")
    public ResponseEntity<Bus> updateBus(@PathVariable(value = "id", required = false) final Long id, @RequestBody Bus bus)
        throws URISyntaxException {
        log.debug("REST request to update Bus : {}, {}", id, bus);
        if (bus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!busRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Bus result = busRepository.save(bus);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bus.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /buses/:id} : Partial updates given fields of an existing bus, field will ignore if it is null
     *
     * @param id the id of the bus to save.
     * @param bus the bus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bus,
     * or with status {@code 400 (Bad Request)} if the bus is not valid,
     * or with status {@code 404 (Not Found)} if the bus is not found,
     * or with status {@code 500 (Internal Server Error)} if the bus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/buses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Bus> partialUpdateBus(@PathVariable(value = "id", required = false) final Long id, @RequestBody Bus bus)
        throws URISyntaxException {
        log.debug("REST request to partial update Bus partially : {}, {}", id, bus);
        if (bus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!busRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Bus> result = busRepository
            .findById(bus.getId())
            .map(existingBus -> {
                if (bus.getName() != null) {
                    existingBus.setName(bus.getName());
                }

                return existingBus;
            })
            .map(busRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bus.getId().toString())
        );
    }

    /**
     * {@code GET  /buses} : get all the buses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of buses in body.
     */
    @GetMapping("/buses")
    public List<Bus> getAllBuses() {
        log.debug("REST request to get all Buses");
        return busRepository.findAll();
    }

    /**
     * {@code GET  /buses/:id} : get the "id" bus.
     *
     * @param id the id of the bus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/buses/{id}")
    public ResponseEntity<Bus> getBus(@PathVariable Long id) {
        log.debug("REST request to get Bus : {}", id);
        Optional<Bus> bus = busRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bus);
    }

    /**
     * {@code DELETE  /buses/:id} : delete the "id" bus.
     *
     * @param id the id of the bus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/buses/{id}")
    public ResponseEntity<Void> deleteBus(@PathVariable Long id) {
        log.debug("REST request to delete Bus : {}", id);
        busRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
