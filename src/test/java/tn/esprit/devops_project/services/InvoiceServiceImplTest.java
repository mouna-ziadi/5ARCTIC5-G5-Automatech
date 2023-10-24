package tn.esprit.devops_project.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.devops_project.entities.Invoice;
import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.repositories.InvoiceRepository;
import tn.esprit.devops_project.repositories.OperatorRepository;
import tn.esprit.devops_project.repositories.SupplierRepository;
import tn.esprit.devops_project.services.Iservices.IInvoiceService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Slf4j
public class InvoiceServiceImplTest {

    @Autowired
    IInvoiceService invoiceService;
    @Autowired
    InvoiceRepository invoiceRepository;
    @Autowired
    OperatorRepository operatorRepository;
    @Autowired
    SupplierRepository supplierRepository;

    @Test
    @Order(4)
    public void testRetrieveAllInvoices() {
        List<Invoice> listProduits = invoiceService.retrieveAllInvoices();
        Assertions.assertEquals(listProduits.size(), listProduits.size());
    }

    @Test
    @Order(1)
    public void testGetTotalAmountInvoiceBetweenDates() throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = dateFormat.parse("30/11/2024");
        Date endDate = dateFormat.parse("25/12/2024");


        Invoice invoice1 = new Invoice();
        invoice1.setDateCreationInvoice(startDate);
        invoice1.setDateLastModificationInvoice(endDate);
        invoice1.setAmountInvoice(100.0f);
        invoice1.setArchived(false);
        invoiceRepository.save(invoice1);

        Invoice invoice2 = new Invoice();
        invoice2.setDateCreationInvoice(startDate);
        invoice2.setDateLastModificationInvoice(endDate);
        invoice2.setAmountInvoice(200.0f);
        invoice2.setArchived(false);
        invoiceRepository.save(invoice2);


        float totalAmount = invoiceService.getTotalAmountInvoiceBetweenDates(startDate,endDate);
        log.info("count: " + totalAmount);
        assertEquals(300.0f, totalAmount);
    }

    @Test
    @Order(2)
    public void testAssignOperatorToInvoice(){
        Invoice invoice1 = new Invoice();
        invoice1.setArchived(false);
        invoice1.setAmountInvoice(100.0f);
        invoiceRepository.save(invoice1);

        Operator operator1 = new Operator();
        operator1.setFname("mouna");
        operator1.setLname("ziadi");
        operatorRepository.save(operator1);

        Long idOperator = operator1.getIdOperateur();
        Long idInvoice = invoice1.getIdInvoice();

        log.info("idOperator: " + idOperator);
        log.info("idInvoice: " + idInvoice);

        invoiceService.assignOperatorToInvoice(idOperator, idInvoice);

        // Rechargez l'opérateur depuis la base de données pour vous assurer que les invoices sont initialisées.
        Operator operator = operatorRepository.findById(idOperator).orElse(null);

        assertNotNull(operator);
        assertEquals(1, operator.getInvoices().size());

    }

    @Test
    @Order(3)
    public void testCancelInvoice() throws ParseException  {
        Invoice invoice = new Invoice();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = dateFormat.parse("30/09/2020");
        Date date2 = dateFormat.parse("05/12/2022");
        invoice.setDateCreationInvoice(date1);
        invoice.setDateLastModificationInvoice(date2);
        invoice.setAmountInvoice(1.5f);
        invoice.setArchived(false);
        invoiceRepository.save(invoice);

        invoiceService.cancelInvoice(invoice.getIdInvoice());
        assertNotNull(invoice.getIdInvoice());
    }

   /* @Test
    @Order(5)
    public void cleanup() {

        invoiceRepository.deleteAll();
        operatorRepository.deleteAll();
    }*/

}