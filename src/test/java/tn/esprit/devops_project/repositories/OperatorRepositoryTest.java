package tn.esprit.devops_project.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tn.esprit.devops_project.entities.Operator;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class OperatorRepositoryTest {
    @Autowired
    private OperatorRepository operatorRepository;


    @Test
    void selectExistsEmail() {
        //given
        String email = "mail@esprit.tn";
        Operator operator = new Operator();
        operator.setFname("hedil");
        operator.setEmail(email);

        operatorRepository.save(operator);


        //when

        boolean expected = operatorRepository.selectExistsEmail(email);

        //then
        assertTrue(expected);
    }
}