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
import rocks.zipcode.repository.RequestTrackerRepository;
import rocks.zipcode.service.RequestTrackerService;
import rocks.zipcode.service.dto.RequestTrackerDTO;
import rocks.zipcode.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link rocks.zipcode.domain.RequestTracker}.
 */
@RestController
@RequestMapping("/api")
public class RequestTrackerResource {

    private final Logger log = LoggerFactory.getLogger(RequestTrackerResource.class);

    private static final String ENTITY_NAME = "requestTracker";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequestTrackerService requestTrackerService;

    private final RequestTrackerRepository requestTrackerRepository;

    public RequestTrackerResource(RequestTrackerService requestTrackerService, RequestTrackerRepository requestTrackerRepository) {
        this.requestTrackerService = requestTrackerService;
        this.requestTrackerRepository = requestTrackerRepository;
    }

    /**
     * {@code POST  /request-trackers} : Create a new requestTracker.
     *
     * @param requestTrackerDTO the requestTrackerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requestTrackerDTO, or with status {@code 400 (Bad Request)} if the requestTracker has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/request-trackers")
    public ResponseEntity<RequestTrackerDTO> createRequestTracker(@RequestBody RequestTrackerDTO requestTrackerDTO)
        throws URISyntaxException {
        log.debug("REST request to save RequestTracker : {}", requestTrackerDTO);
        if (requestTrackerDTO.getId() != null) {
            throw new BadRequestAlertException("A new requestTracker cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RequestTrackerDTO result = requestTrackerService.save(requestTrackerDTO);
        return ResponseEntity
            .created(new URI("/api/request-trackers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /request-trackers/:id} : Updates an existing requestTracker.
     *
     * @param id the id of the requestTrackerDTO to save.
     * @param requestTrackerDTO the requestTrackerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestTrackerDTO,
     * or with status {@code 400 (Bad Request)} if the requestTrackerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requestTrackerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/request-trackers/{id}")
    public ResponseEntity<RequestTrackerDTO> updateRequestTracker(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RequestTrackerDTO requestTrackerDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RequestTracker : {}, {}", id, requestTrackerDTO);
        if (requestTrackerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requestTrackerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestTrackerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RequestTrackerDTO result = requestTrackerService.update(requestTrackerDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requestTrackerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /request-trackers/:id} : Partial updates given fields of an existing requestTracker, field will ignore if it is null
     *
     * @param id the id of the requestTrackerDTO to save.
     * @param requestTrackerDTO the requestTrackerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestTrackerDTO,
     * or with status {@code 400 (Bad Request)} if the requestTrackerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the requestTrackerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the requestTrackerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/request-trackers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RequestTrackerDTO> partialUpdateRequestTracker(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RequestTrackerDTO requestTrackerDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RequestTracker partially : {}, {}", id, requestTrackerDTO);
        if (requestTrackerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requestTrackerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestTrackerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RequestTrackerDTO> result = requestTrackerService.partialUpdate(requestTrackerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requestTrackerDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /request-trackers} : get all the requestTrackers.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requestTrackers in body.
     */
    @GetMapping("/request-trackers")
    public List<RequestTrackerDTO> getAllRequestTrackers(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all RequestTrackers");
        return requestTrackerService.findAll();
    }

    /**
     * {@code GET  /request-trackers/:id} : get the "id" requestTracker.
     *
     * @param id the id of the requestTrackerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requestTrackerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/request-trackers/{id}")
    public ResponseEntity<RequestTrackerDTO> getRequestTracker(@PathVariable Long id) {
        log.debug("REST request to get RequestTracker : {}", id);
        Optional<RequestTrackerDTO> requestTrackerDTO = requestTrackerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(requestTrackerDTO);
    }

    /**
     * {@code DELETE  /request-trackers/:id} : delete the "id" requestTracker.
     *
     * @param id the id of the requestTrackerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/request-trackers/{id}")
    public ResponseEntity<Void> deleteRequestTracker(@PathVariable Long id) {
        log.debug("REST request to delete RequestTracker : {}", id);
        requestTrackerService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
