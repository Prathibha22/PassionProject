package rocks.zipcode.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rocks.zipcode.domain.Student;

/**
 * Spring Data JPA repository for the Student entity.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("select student from Student student where student.user.login = ?#{principal.username}")
    List<Student> findByUserIsCurrentUser();

    default Optional<Student> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Student> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Student> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct student from Student student left join fetch student.user left join fetch student.bus",
        countQuery = "select count(distinct student) from Student student"
    )
    Page<Student> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct student from Student student left join fetch student.user left join fetch student.bus")
    List<Student> findAllWithToOneRelationships();

    @Query("select student from Student student left join fetch student.user left join fetch student.bus where student.id =:id")
    Optional<Student> findOneWithToOneRelationships(@Param("id") Long id);
}
