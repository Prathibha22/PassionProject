package rocks.zipcode.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link rocks.zipcode.domain.Bus} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BusDTO implements Serializable {

    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BusDTO)) {
            return false;
        }

        BusDTO busDTO = (BusDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, busDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BusDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
