package tn.esprit.devops_project.services;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.devops_project.entities.Invoice;
import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.repositories.InvoiceRepository;
import tn.esprit.devops_project.repositories.OperatorRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class InvoiceServiceImplMockTest {

    @Mock
    InvoiceRepository invoiceRepository;
    @Mock
    OperatorRepository operatorRepository;
    @InjectMocks
    InvoiceServiceImpl invoiceService;
    @InjectMocks
    OperatorServiceImpl operatorService;

    private List<Invoice> invoiceList;
    Invoice invoice1 = new Invoice();
    Invoice invoice2 = new Invoice();
    Operator operator1 = new Operator();

    @BeforeEach
    void setUp() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = dateFormat.parse("30/09/2024");
        Date endDate = dateFormat.parse("25/10/2024");

        invoice1 = new Invoice(1L, 0.0f, 100.0f, startDate, endDate, false, null, null);
        invoice2 = new Invoice(2L, 0.0f, 200.0f, startDate, endDate, false, null, null);

        operator1 = new Operator(1L,"mouna","ziadi","ziadi",null);
        invoiceList = new ArrayList<Invoice>() {
            {
                add(invoice1);
                add(invoice2);
            }
        };
    }

    @AfterEach
    void tearDown() {
        invoiceList.clear();

        if (operator1 != null) {
            operatorService.deleteOperator(operator1.getIdOperateur());
        }
    }

    @Test
    @Order(1)
    public void testRetrieveAllInvoices() {
        when(invoiceRepository.findAll()).thenReturn(invoiceList);
        List<Invoice> listInvoices = invoiceService.retrieveAllInvoices();
        assertEquals(2, listInvoices.size());
    }

    @Test
    @Order(2)
    public void testGetTotalAmountInvoiceBetweenDates() throws ParseException {
        when(invoiceRepository.getTotalAmountInvoiceBetweenDates(any(), any())).thenReturn(300.0f);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = dateFormat.parse("30/09/2024");
        Date endDate = dateFormat.parse("25/10/2024");

        float totalAmount = invoiceService.getTotalAmountInvoiceBetweenDates(startDate, endDate);
        assertEquals(300.0f, totalAmount);
    }

    @Test
    @Order(3)
    public void testAssignOperatorToInvoice() {
        operator1.setInvoices(new HashSet<>());

        when(invoiceRepository.findById(anyLong())).thenReturn(java.util.Optional.of(invoice1));
        when(operatorRepository.findById(anyLong())).thenReturn(java.util.Optional.of(operator1));

        invoiceService.assignOperatorToInvoice(1L, 1L);

        verify(invoiceRepository, times(1)).findById(1L);
        verify(operatorRepository, times(1)).findById(1L);
        assertTrue(operator1.getInvoices().contains(invoice1));
    }

    @Test
    @Order(4)
    public void testCancelInvoice(){
        when(invoiceRepository.findById(2L)).thenReturn(java.util.Optional.of(invoice2));
        invoiceService.cancelInvoice(2L);

        verify(invoiceRepository, times(1)).findById(2L);
        assertTrue(invoice2.getArchived());
    }

}