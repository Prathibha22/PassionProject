package rocks.zipcode.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import rocks.zipcode.domain.enumeration.RequestType;

/**
 * A DTO for the {@link rocks.zipcode.domain.RequestTracker} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RequestTrackerDTO implements Serializable {

    private Long id;

    private LocalDate date;

    private RequestType requestType;

    private String description;

    private StudentDTO student;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StudentDTO getStudent() {
        return student;
    }

    public void setStudent(StudentDTO student) {
        this.student = student;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequestTrackerDTO)) {
            return false;
        }

        RequestTrackerDTO requestTrackerDTO = (RequestTrackerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, requestTrackerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequestTrackerDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", requestType='" + getRequestType() + "'" +
            ", description='" + getDescription() + "'" +
            ", student=" + getStudent() +
            "}";
    }
}
