package edu.school21.repositories;

import edu.school21.models.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {
    public static final String FIND_ALL_QUERY = "SELECT * FROM warehouse.product";
    public static final String FIND_ID_QUERY = "SELECT * FROM warehouse.product WHERE identifier = ";
    public static final String UPDATE_QUERY = "UPDATE warehouse.product SET name = ?, price = ? WHERE identifier = ?";
    public static final String SAVE_QUERY = "INSERT INTO warehouse.product(identifier, name, price) VALUES (?, ?, ?)";
    public static final String DELETE_QUERY = "DELETE FROM warehouse.product WHERE identifier = ";

    private final DataSource dataSource;

    public ProductsRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public List<Product> findAll() {
        List<Product> productList = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resSet = statement.executeQuery(FIND_ALL_QUERY);

            while (resSet.next()) {
                productList.add(new Product(resSet.getLong("identifier"),
                        resSet.getString("name"), resSet.getInt("price")));
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return productList;
    }

    @Override
    public Optional<Product> findById(Long id) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resSet = statement.executeQuery(FIND_ID_QUERY + id + ";");

            if (!resSet.next()) {
                return Optional.empty();
            }

            Product product = new Product(resSet.getLong(1),
                    resSet.getString(2), resSet.getInt(3));

            return Optional.of(product);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public void update(Product product) {
        Optional<Product> tmp = findById(product.getId());

        if (!tmp.isPresent()) {
            System.err.println("There is no product with provided to update identifier. Update failed");
            return;
        }

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, product.getName());
            statement.setInt(2, product.getPrice());
            statement.setLong(3, product.getId());
            statement.execute();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void save(Product product) {
        Optional<Product> tmp = findById(product.getId());

        if (tmp.isPresent()) {
            System.err.println("Product with provided to save identifier already exists. Save failed");
            return;
        }

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_QUERY)) {
            statement.setLong(1, product.getId());
            statement.setString(2, product.getName());
            statement.setInt(3, product.getPrice());
            statement.execute();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        Optional<Product> tmp = findById(id);

        if (!tmp.isPresent()) {
            System.err.println("Product with provided to delete identifier does not exist. Delete failed");
            return;
        }

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY + id)) {
            statement.execute();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
