package tn.esprit.devops_project.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.repositories.OperatorRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OperatorServiceImplTest {
    @Mock
    private OperatorRepository operatorRepository;
    private OperatorServiceImpl operatorService;
   private  AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        operatorService = new OperatorServiceImpl(operatorRepository);
    }

    @AfterEach
    void tearDown() throws  Exception{
        autoCloseable.close();
    }

    @Test
    void addOperator() {
        //given
        String email = "mail@esprit.tn";
        Operator operator = new Operator();
        operator.setFname("hedil");
        operator.setEmail(email);

        operatorService.addOperator(operator);
        ArgumentCaptor<Operator> operatorArgumentCaptor =
                ArgumentCaptor.forClass(Operator.class);
        verify(operatorRepository).save(operatorArgumentCaptor.capture());

        Operator capturedOp  = operatorArgumentCaptor.getValue();
        assertEquals(capturedOp, operator);
    }
}