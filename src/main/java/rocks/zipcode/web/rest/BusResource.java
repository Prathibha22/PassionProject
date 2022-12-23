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
import org.springframework.web.bind.annotation.*;
import rocks.zipcode.repository.BusRepository;
import rocks.zipcode.service.BusService;
import rocks.zipcode.service.dto.BusDTO;
import rocks.zipcode.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link rocks.zipcode.domain.Bus}.
 */
@RestController
@RequestMapping("/api")
public class BusResource {

    private final Logger log = LoggerFactory.getLogger(BusResource.class);

    private static final String ENTITY_NAME = "bus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BusService busService;

    private final BusRepository busRepository;

    public BusResource(BusService busService, BusRepository busRepository) {
        this.busService = busService;
        this.busRepository = busRepository;
    }

    /**
     * {@code POST  /buses} : Create a new bus.
     *
     * @param busDTO the busDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new busDTO, or with status {@code 400 (Bad Request)} if the bus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/buses")
    public ResponseEntity<BusDTO> createBus(@RequestBody BusDTO busDTO) throws URISyntaxException {
        log.debug("REST request to save Bus : {}", busDTO);
        if (busDTO.getId() != null) {
            throw new BadRequestAlertException("A new bus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BusDTO result = busService.save(busDTO);
        return ResponseEntity
            .created(new URI("/api/buses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /buses/:id} : Updates an existing bus.
     *
     * @param id the id of the busDTO to save.
     * @param busDTO the busDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated busDTO,
     * or with status {@code 400 (Bad Request)} if the busDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the busDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/buses/{id}")
    public ResponseEntity<BusDTO> updateBus(@PathVariable(value = "id", required = false) final Long id, @RequestBody BusDTO busDTO)
        throws URISyntaxException {
        log.debug("REST request to update Bus : {}, {}", id, busDTO);
        if (busDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, busDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!busRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BusDTO result = busService.update(busDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, busDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /buses/:id} : Partial updates given fields of an existing bus, field will ignore if it is null
     *
     * @param id the id of the busDTO to save.
     * @param busDTO the busDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated busDTO,
     * or with status {@code 400 (Bad Request)} if the busDTO is not valid,
     * or with status {@code 404 (Not Found)} if the busDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the busDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/buses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BusDTO> partialUpdateBus(@PathVariable(value = "id", required = false) final Long id, @RequestBody BusDTO busDTO)
        throws URISyntaxException {
        log.debug("REST request to partial update Bus partially : {}, {}", id, busDTO);
        if (busDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, busDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!busRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BusDTO> result = busService.partialUpdate(busDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, busDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /buses} : get all the buses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of buses in body.
     */
    @GetMapping("/buses")
    public List<BusDTO> getAllBuses() {
        log.debug("REST request to get all Buses");
        return busService.findAll();
    }

    /**
     * {@code GET  /buses/:id} : get the "id" bus.
     *
     * @param id the id of the busDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the busDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/buses/{id}")
    public ResponseEntity<BusDTO> getBus(@PathVariable Long id) {
        log.debug("REST request to get Bus : {}", id);
        Optional<BusDTO> busDTO = busService.findOne(id);
        return ResponseUtil.wrapOrNotFound(busDTO);
    }

    /**
     * {@code DELETE  /buses/:id} : delete the "id" bus.
     *
     * @param id the id of the busDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/buses/{id}")
    public ResponseEntity<Void> deleteBus(@PathVariable Long id) {
        log.debug("REST request to delete Bus : {}", id);
        busService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
