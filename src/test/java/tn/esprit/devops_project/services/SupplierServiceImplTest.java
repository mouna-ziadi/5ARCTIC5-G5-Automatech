package tn.esprit.devops_project.services;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.devops_project.entities.Supplier;
import tn.esprit.devops_project.entities.SupplierCategory;
import tn.esprit.devops_project.repositories.SupplierRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SupplierServiceImplTest {

    @Mock
    SupplierRepository supplierRepository;

    @InjectMocks
    SupplierServiceImpl supplierService;

    private List<Supplier> supplierList;
    Supplier supplier1 = new Supplier();
    Supplier supplier2 = new Supplier();

    @BeforeEach
    void setUp() {
        supplier1 = new Supplier(1L, "S1", "Supplier 1", SupplierCategory.ORDINAIRE, null);
        supplier2 = new Supplier(2L, "S2", "Supplier 2", SupplierCategory.ORDINAIRE, null);

        supplierList = new ArrayList<Supplier>() {
            {
                add(supplier1);
                add(supplier2);
            }
        };
    }

    @AfterEach
    void tearDown() {
        supplierList.clear();
    }

    @Test
    @Order(1)
    public void testRetrieveAllSuppliers() {
        when(supplierRepository.findAll()).thenReturn(supplierList);
        List<Supplier> listSuppliers = supplierService.retrieveAllSuppliers();
        assertEquals(2, listSuppliers.size());
    }

    @Test
    @Order(2)
    public void testAddSupplier() {
        when(supplierRepository.save(any(Supplier.class))).thenReturn(supplier1);
        Supplier addedSupplier = supplierService.addSupplier(supplier1);
        assertNotNull(addedSupplier);
        assertEquals(supplier1, addedSupplier);
    }

    @Test
    @Order(3)
    public void testUpdateSupplier() {
        when(supplierRepository.save(any(Supplier.class))).thenReturn(supplier1);
        Supplier updatedSupplier = supplierService.updateSupplier(supplier1);
        assertNotNull(updatedSupplier);
        assertEquals(supplier1, updatedSupplier);
    }

    @Test
    @Order(4)
    public void testDeleteSupplier() {
        doNothing().when(supplierRepository).deleteById(1L);
        supplierService.deleteSupplier(1L);
        verify(supplierRepository, times(1)).deleteById(1L);
    }

    @Test
    @Order(5)
    public void testRetrieveSupplier() {
        when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier1));
        Supplier retrievedSupplier = supplierService.retrieveSupplier(1L);
        assertNotNull(retrievedSupplier);
        assertEquals(supplier1, retrievedSupplier);
    }
}
