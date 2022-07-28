package edu.school21.repositories;

import edu.school21.models.Product;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImplTest {
    private ProductsRepository repository;
    private EmbeddedDatabase dataSource;

    final List<Product> EXPECTED_FIND_ALL = Arrays.asList(
            new Product(1l, "Tea", 100),
            new Product(2l, "Salt", 50),
            new Product(3l, "Sugar", 60),
            new Product(4l, "Chocolate", 85),
            new Product(5l, "Coffee", 200));

    final Product EXPECTED_FIND_BY_ID = new Product(4l, "Chocolate", 85);
    final Product EXPECTED_UPDATED_PRODUCT = new Product(1l, "Green Tea", 150);
    final Product EXPECTED_SAVED_PRODUCT = new Product(6l, "Juice", 777);

    final List<Product> PRODUCTS_AFTER_DELETE = Arrays.asList(
            new Product(1l, "Tea", 100),
            new Product(3l, "Sugar", 60),
            new Product(4l, "Chocolate", 85),
            new Product(5l, "Coffee", 200));

    @BeforeEach
    private void init() {
        dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
        repository = new ProductsRepositoryJdbcImpl(dataSource);
    }

    @Test
    void testIsFindAllSucceed() {
        List<Product> result = repository.findAll();
        assertEquals(EXPECTED_FIND_ALL, result);
    }

    @Test
    void testIsFindByIdSucceed() {
        assertEquals(EXPECTED_FIND_BY_ID, repository.findById(4l).get());
        assertEquals(Optional.empty(), repository.findById(100l));
        assertEquals(Optional.empty(), repository.findById(null));
    }

    @Test
    void testIsUpdateSucceed() {

        Product updProduct = EXPECTED_UPDATED_PRODUCT;
        repository.update(updProduct);
        assertEquals(EXPECTED_UPDATED_PRODUCT, repository.findById(1l).get());
    }

    @Test
    void testIsSaveSucceed() {
        Product newProduct = EXPECTED_SAVED_PRODUCT;
        repository.save(newProduct);
        assertEquals(EXPECTED_SAVED_PRODUCT, repository.findById(6l).get());
    }

    @Test
    void testIsDeleteSucceed() {
        repository.delete(2l);
        assertEquals(PRODUCTS_AFTER_DELETE, repository.findAll());
        assertFalse(repository.findById(2l).isPresent());
    }

    @AfterEach
    void close() {
        dataSource.shutdown();
    }
}
