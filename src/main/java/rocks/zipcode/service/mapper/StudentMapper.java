package rocks.zipcode.service.mapper;

import org.mapstruct.*;
import rocks.zipcode.domain.Bus;
import rocks.zipcode.domain.Student;
import rocks.zipcode.domain.User;
import rocks.zipcode.service.dto.BusDTO;
import rocks.zipcode.service.dto.StudentDTO;
import rocks.zipcode.service.dto.UserDTO;

/**
 * Mapper for the entity {@link Student} and its DTO {@link StudentDTO}.
 */
@Mapper(componentModel = "spring")
public interface StudentMapper extends EntityMapper<StudentDTO, Student> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    @Mapping(target = "bus", source = "bus", qualifiedByName = "busName")
    StudentDTO toDto(Student s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("busName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    BusDTO toDtoBusName(Bus bus);
}
