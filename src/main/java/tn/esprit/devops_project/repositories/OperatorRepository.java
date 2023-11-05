package tn.esprit.devops_project.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.devops_project.entities.Operator;

public interface OperatorRepository extends CrudRepository<Operator, Long> {

    @Query("" +
            "SELECT CASE WHEN COUNT(o) > 0 THEN " +
            "TRUE ELSE FALSE END " +
            "FROM Operator  o " +
            "WHERE o.email = ?1"
    )
    Boolean selectExistsEmail(String email);

}
