package rocks.zipcode.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import rocks.zipcode.domain.Bus;

/**
 * Spring Data JPA repository for the Bus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BusRepository extends JpaRepository<Bus, Long> {}
