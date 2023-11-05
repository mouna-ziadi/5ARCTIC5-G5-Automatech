package tn.esprit.devops_project.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import tn.esprit.devops_project.entities.Supplier;
import tn.esprit.devops_project.entities.SupplierCategory;
import tn.esprit.devops_project.repositories.SupplierRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SupplierServiceImplTest {

    @Mock
    SupplierRepository supplierRepository;

    @InjectMocks
    SupplierServiceImpl supplierService;

    private List<Supplier> supplierList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize Mockito annotations
        supplierList = new ArrayList<>();

        // Sample data
        Supplier supplier1 = new Supplier(1L, "S1", "Supplier 1", SupplierCategory.ORDINAIRE, null);
        Supplier supplier2 = new Supplier(2L, "S2", "Supplier 2", SupplierCategory.ORDINAIRE, null);
        supplierList.add(supplier1);
        supplierList.add(supplier2);
    }

    @Test
    public void testRetrieveAllSuppliers() {
        // Mock the behavior of the repository
        Mockito.when(supplierRepository.findAll()).thenReturn(supplierList);

        // Execute the service method
        List<Supplier> listSuppliers = supplierService.retrieveAllSuppliers();

        // Verify the results
        assertEquals(2, listSuppliers.size());
    }

    @Test
    public void testAddSupplier() {
        Supplier newSupplier = new Supplier(3L, "S3", "Supplier 3", SupplierCategory.ORDINAIRE, null);

        // Mock the behavior of the repository's save method
        Mockito.when(supplierRepository.save(newSupplier)).thenReturn(newSupplier);

        // Execute the service method
        Supplier addedSupplier = supplierService.addSupplier(newSupplier);

        // Verify the results
        assertNotNull(addedSupplier);
        assertEquals(newSupplier, addedSupplier);
    }

    @Test
    public void testUpdateSupplier() {
        Supplier existingSupplier = new Supplier(1L, "S1", "Updated Supplier 1", SupplierCategory.ORDINAIRE, null);

        // Mock the behavior of the repository's save method
        Mockito.when(supplierRepository.save(existingSupplier)).thenReturn(existingSupplier);

        // Execute the service method
        Supplier updatedSupplier = supplierService.updateSupplier(existingSupplier);

        // Verify the results
        assertNotNull(updatedSupplier);
        assertEquals(existingSupplier, updatedSupplier);
    }

    @Test
    public void testDeleteSupplier() {
        long supplierId = 1L;

        // Mock the behavior of the repository's deleteById method
        Mockito.doNothing().when(supplierRepository).deleteById(supplierId);

        // Execute the service method
        supplierService.deleteSupplier(supplierId);

        // Verification is done using the mocked repository methods
        Mockito.verify(supplierRepository, Mockito.times(1)).deleteById(supplierId);
    }

    @Test
    public void testRetrieveSupplier() {
        long supplierId = 1L;
        Supplier supplier = new Supplier(supplierId, "S1", "Supplier 1", SupplierCategory.ORDINAIRE, null);

        // Mock the behavior of the repository's findById method
        Mockito.when(supplierRepository.findById(supplierId)).thenReturn(Optional.of(supplier));

        // Execute the service method
        Supplier retrievedSupplier = supplierService.retrieveSupplier(supplierId);

        // Verify the results
        assertNotNull(retrievedSupplier);
        assertEquals(supplier, retrievedSupplier);
    }
}
