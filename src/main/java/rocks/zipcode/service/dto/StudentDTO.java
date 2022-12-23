package rocks.zipcode.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link rocks.zipcode.domain.Student} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StudentDTO implements Serializable {

    private Long id;

    private String fullName;

    private String address;

    private Integer grade;

    private String contactNo;

    private UserDTO user;

    private BusDTO bus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public BusDTO getBus() {
        return bus;
    }

    public void setBus(BusDTO bus) {
        this.bus = bus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StudentDTO)) {
            return false;
        }

        StudentDTO studentDTO = (StudentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, studentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StudentDTO{" +
            "id=" + getId() +
            ", fullName='" + getFullName() + "'" +
            ", address='" + getAddress() + "'" +
            ", grade=" + getGrade() +
            ", contactNo='" + getContactNo() + "'" +
            ", user=" + getUser() +
            ", bus=" + getBus() +
            "}";
    }
}
