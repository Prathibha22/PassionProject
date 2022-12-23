package rocks.zipcode.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rocks.zipcode.domain.Bus;
import rocks.zipcode.repository.BusRepository;
import rocks.zipcode.service.dto.BusDTO;
import rocks.zipcode.service.mapper.BusMapper;

/**
 * Service Implementation for managing {@link Bus}.
 */
@Service
@Transactional
public class BusService {

    private final Logger log = LoggerFactory.getLogger(BusService.class);

    private final BusRepository busRepository;

    private final BusMapper busMapper;

    public BusService(BusRepository busRepository, BusMapper busMapper) {
        this.busRepository = busRepository;
        this.busMapper = busMapper;
    }

    /**
     * Save a bus.
     *
     * @param busDTO the entity to save.
     * @return the persisted entity.
     */
    public BusDTO save(BusDTO busDTO) {
        log.debug("Request to save Bus : {}", busDTO);
        Bus bus = busMapper.toEntity(busDTO);
        bus = busRepository.save(bus);
        return busMapper.toDto(bus);
    }

    /**
     * Update a bus.
     *
     * @param busDTO the entity to save.
     * @return the persisted entity.
     */
    public BusDTO update(BusDTO busDTO) {
        log.debug("Request to update Bus : {}", busDTO);
        Bus bus = busMapper.toEntity(busDTO);
        bus = busRepository.save(bus);
        return busMapper.toDto(bus);
    }

    /**
     * Partially update a bus.
     *
     * @param busDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BusDTO> partialUpdate(BusDTO busDTO) {
        log.debug("Request to partially update Bus : {}", busDTO);

        return busRepository
            .findById(busDTO.getId())
            .map(existingBus -> {
                busMapper.partialUpdate(existingBus, busDTO);

                return existingBus;
            })
            .map(busRepository::save)
            .map(busMapper::toDto);
    }

    /**
     * Get all the buses.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BusDTO> findAll() {
        log.debug("Request to get all Buses");
        return busRepository.findAll().stream().map(busMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one bus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BusDTO> findOne(Long id) {
        log.debug("Request to get Bus : {}", id);
        return busRepository.findById(id).map(busMapper::toDto);
    }

    /**
     * Delete the bus by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Bus : {}", id);
        busRepository.deleteById(id);
    }
}
