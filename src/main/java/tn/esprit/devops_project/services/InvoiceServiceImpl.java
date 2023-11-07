package tn.esprit.devops_project.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.devops_project.entities.Invoice;
import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.entities.Supplier;
import tn.esprit.devops_project.repositories.InvoiceDetailRepository;
import tn.esprit.devops_project.repositories.InvoiceRepository;
import tn.esprit.devops_project.repositories.OperatorRepository;
import tn.esprit.devops_project.repositories.SupplierRepository;
import tn.esprit.devops_project.services.Iservices.IInvoiceService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class InvoiceServiceImpl implements IInvoiceService {

	final InvoiceRepository invoiceRepository;
	final OperatorRepository operatorRepository;
	final InvoiceDetailRepository invoiceDetailRepository;
	final SupplierRepository supplierRepository;


	@Override
	public List<Invoice> retrieveAllInvoices() {
		return invoiceRepository.findAll();
	}

	@Override
	public void cancelInvoice(Long invoiceId) {
		Optional<Invoice> optionalInvoice = invoiceRepository.findById(invoiceId);
		if (optionalInvoice.isPresent()) {
			Invoice invoice = optionalInvoice.get();
			invoice.setArchived(true);
			invoiceRepository.save(invoice);
		} else {
			log.error("Invoice not found for ID: {}", invoiceId);
			throw new IllegalArgumentException("Invoice not found");
		}
		// Method 02 (Avec JPQL)
		invoiceRepository.updateInvoice(invoiceId);
	}

	@Override
	public Invoice retrieveInvoice(Long invoiceId) {
		return invoiceRepository.findById(invoiceId).orElseThrow(() -> new IllegalArgumentException("Invoice not found"));
	}

	@Override
	public List<Invoice> getInvoicesBySupplier(Long idSupplier) {
		Supplier supplier = supplierRepository.findById(idSupplier).orElseThrow(() -> new IllegalArgumentException("Supplier not found"));
		return (List<Invoice>) supplier.getInvoices();
	}

	@Override
	public void assignOperatorToInvoice(Long idOperator, Long idInvoice) {
		Invoice invoice = invoiceRepository.findById(idInvoice).orElseThrow(() -> new IllegalArgumentException("Invoice not found"));
		Operator operator = operatorRepository.findById(idOperator).orElseThrow(() -> new IllegalArgumentException("Operator not found"));
		operator.getInvoices().add(invoice);
		operatorRepository.save(operator);
	}

	@Override
	public float getTotalAmountInvoiceBetweenDates(Date startDate, Date endDate) {
		return invoiceRepository.getTotalAmountInvoiceBetweenDates(startDate, endDate);
	}
}