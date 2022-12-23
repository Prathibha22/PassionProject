package rocks.zipcode.service.mapper;

import org.mapstruct.*;
import rocks.zipcode.domain.Bus;
import rocks.zipcode.service.dto.BusDTO;

/**
 * Mapper for the entity {@link Bus} and its DTO {@link BusDTO}.
 */
@Mapper(componentModel = "spring")
public interface BusMapper extends EntityMapper<BusDTO, Bus> {}
