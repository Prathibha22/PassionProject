package rocks.zipcode.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rocks.zipcode.domain.RequestTracker;
import rocks.zipcode.repository.RequestTrackerRepository;
import rocks.zipcode.service.dto.RequestTrackerDTO;
import rocks.zipcode.service.mapper.RequestTrackerMapper;

/**
 * Service Implementation for managing {@link RequestTracker}.
 */
@Service
@Transactional
public class RequestTrackerService {

    private final Logger log = LoggerFactory.getLogger(RequestTrackerService.class);

    private final RequestTrackerRepository requestTrackerRepository;

    private final RequestTrackerMapper requestTrackerMapper;

    public RequestTrackerService(RequestTrackerRepository requestTrackerRepository, RequestTrackerMapper requestTrackerMapper) {
        this.requestTrackerRepository = requestTrackerRepository;
        this.requestTrackerMapper = requestTrackerMapper;
    }

    /**
     * Save a requestTracker.
     *
     * @param requestTrackerDTO the entity to save.
     * @return the persisted entity.
     */
    public RequestTrackerDTO save(RequestTrackerDTO requestTrackerDTO) {
        log.debug("Request to save RequestTracker : {}", requestTrackerDTO);
        RequestTracker requestTracker = requestTrackerMapper.toEntity(requestTrackerDTO);
        requestTracker = requestTrackerRepository.save(requestTracker);
        return requestTrackerMapper.toDto(requestTracker);
    }

    /**
     * Update a requestTracker.
     *
     * @param requestTrackerDTO the entity to save.
     * @return the persisted entity.
     */
    public RequestTrackerDTO update(RequestTrackerDTO requestTrackerDTO) {
        log.debug("Request to update RequestTracker : {}", requestTrackerDTO);
        RequestTracker requestTracker = requestTrackerMapper.toEntity(requestTrackerDTO);
        requestTracker = requestTrackerRepository.save(requestTracker);
        return requestTrackerMapper.toDto(requestTracker);
    }

    /**
     * Partially update a requestTracker.
     *
     * @param requestTrackerDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RequestTrackerDTO> partialUpdate(RequestTrackerDTO requestTrackerDTO) {
        log.debug("Request to partially update RequestTracker : {}", requestTrackerDTO);

        return requestTrackerRepository
            .findById(requestTrackerDTO.getId())
            .map(existingRequestTracker -> {
                requestTrackerMapper.partialUpdate(existingRequestTracker, requestTrackerDTO);

                return existingRequestTracker;
            })
            .map(requestTrackerRepository::save)
            .map(requestTrackerMapper::toDto);
    }

    /**
     * Get all the requestTrackers.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<RequestTrackerDTO> findAll() {
        log.debug("Request to get all RequestTrackers");
        return requestTrackerRepository
            .findAll()
            .stream()
            .map(requestTrackerMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the requestTrackers with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<RequestTrackerDTO> findAllWithEagerRelationships(Pageable pageable) {
        return requestTrackerRepository.findAllWithEagerRelationships(pageable).map(requestTrackerMapper::toDto);
    }

    /**
     * Get one requestTracker by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RequestTrackerDTO> findOne(Long id) {
        log.debug("Request to get RequestTracker : {}", id);
        return requestTrackerRepository.findOneWithEagerRelationships(id).map(requestTrackerMapper::toDto);
    }

    /**
     * Delete the requestTracker by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RequestTracker : {}", id);
        requestTrackerRepository.deleteById(id);
    }
}
