package rocks.zipcode.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rocks.zipcode.domain.RequestTracker;

/**
 * Spring Data JPA repository for the RequestTracker entity.
 */
@Repository
public interface RequestTrackerRepository extends JpaRepository<RequestTracker, Long> {
    default Optional<RequestTracker> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<RequestTracker> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<RequestTracker> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct requestTracker from RequestTracker requestTracker left join fetch requestTracker.student",
        countQuery = "select count(distinct requestTracker) from RequestTracker requestTracker"
    )
    Page<RequestTracker> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct requestTracker from RequestTracker requestTracker left join fetch requestTracker.student")
    List<RequestTracker> findAllWithToOneRelationships();

    @Query("select requestTracker from RequestTracker requestTracker left join fetch requestTracker.student where requestTracker.id =:id")
    Optional<RequestTracker> findOneWithToOneRelationships(@Param("id") Long id);
}
