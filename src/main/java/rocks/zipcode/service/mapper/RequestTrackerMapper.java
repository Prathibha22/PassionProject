package rocks.zipcode.service.mapper;

import org.mapstruct.*;
import rocks.zipcode.domain.RequestTracker;
import rocks.zipcode.domain.Student;
import rocks.zipcode.service.dto.RequestTrackerDTO;
import rocks.zipcode.service.dto.StudentDTO;

/**
 * Mapper for the entity {@link RequestTracker} and its DTO {@link RequestTrackerDTO}.
 */
@Mapper(componentModel = "spring")
public interface RequestTrackerMapper extends EntityMapper<RequestTrackerDTO, RequestTracker> {
    @Mapping(target = "student", source = "student", qualifiedByName = "studentFullName")
    RequestTrackerDTO toDto(RequestTracker s);

    @Named("studentFullName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "fullName", source = "fullName")
    StudentDTO toDtoStudentFullName(Student student);
}
