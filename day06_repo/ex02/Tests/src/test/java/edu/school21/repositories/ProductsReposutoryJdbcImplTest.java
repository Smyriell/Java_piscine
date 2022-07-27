package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductsReposutoryJdbcImplTest {
    private ProductsRepository repository;
    private DataSource dataSource;

    final List<Product> FIND_ALL = Arrays.asList(
            new Product(1l, "Tea", 100),
            new Product(2l, "Salt", 50),
            new Product(3l, "Sugar", 60),
            new Product(4l, "Chocolate", 85),
            new Product(5l, "Coffee", 200));

    final Product FIND_BY_ID = new Product(4l, "Chocolate", 85);
    final Product UPDATED_PRODUCT = new Product(1l, "Green Tea", 150);
    final Product SAVE_PRODUCT = new Product(6l, "Juice", 777);

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
    public void findAllTest() throws SQLException {
        List<Product> actual = repository.findAll();
        Assertions.assertEquals(FIND_ALL, actual);
    }

    @Test
    public void findByIdTest() {
        Assertions.assertEquals(FIND_BY_ID, repository.findById(4l).get());
        Assertions.assertEquals(Optional.empty(), repository.findById(100l));
        Assertions.assertEquals(Optional.empty(), repository.findById(null));
    }

    @Test
    public void updateTest() {
        repository.update(new Product(1l, "Green Tea", 150));
        Assertions.assertEquals(UPDATED_PRODUCT, repository.findById(1l).get());
    }
//
//    @Test
//    public void saveTest() {
//        repository.save(new Product(7l, "Orange", 69));
//        Assertions.assertEquals(SAVE_PRODUCT, repository.findById(7l).get());
//    }
//
//    @Test
//    public void deleteTest() throws SQLException {
//        repository.delete(3l);
//        Assertions.assertEquals(PRODUCTS_AFTER_DELETE, repository.findAll());
//        Assertions.assertFalse(repository.findById(3l).isPresent());
//    }
}
