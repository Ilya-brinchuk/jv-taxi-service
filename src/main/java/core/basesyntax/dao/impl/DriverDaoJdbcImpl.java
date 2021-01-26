package core.basesyntax.dao.impl;

import core.basesyntax.dao.DriverDao;
import core.basesyntax.exception.DataProcessingException;
import core.basesyntax.lib.Dao;
import core.basesyntax.model.Driver;
import core.basesyntax.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class DriverDaoJdbcImpl implements DriverDao {
    @Override
    public Driver create(Driver driver) {
        String query = "INSERT INTO drivers (name, licence, login, password) VALUES(?, ?, ?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, driver.getName());
            preparedStatement.setString(2, driver.getLicenceNumber());
            preparedStatement.setString(3, driver.getLogin());
            preparedStatement.setString(4, driver.getPassword());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                driver.setId(resultSet.getObject(1, Long.class));
            }
            return driver;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert driver " + driver, e);
        }
    }

    @Override
    public Optional<Driver> get(Long id) {
        String query = "SELECT * FROM drivers WHERE id = ? AND deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Optional<Driver> optional = Optional.empty();
            if (resultSet.next()) {
                optional = Optional.of(getDriverFromResultSet(resultSet));
            }
            return optional;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get driver by id " + id, e);
        }
    }

    @Override
    public List<Driver> getAll() {
        String query = "SELECT * FROM drivers WHERE deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Driver> drivers = new ArrayList<>();
            while (resultSet.next()) {
                drivers.add(getDriverFromResultSet(resultSet));
            }
            return drivers;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all drivers", e);
        }
    }

    @Override
    public Driver update(Driver driver) {
        String query = "UPDATE drivers SET name = ?, licence = ?, login = ?, password = ?"
                + " WHERE id = ? AND deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, driver.getName());
            preparedStatement.setString(2, driver.getLicenceNumber());
            preparedStatement.setString(3, driver.getLogin());
            preparedStatement.setString(4, driver.getPassword());
            preparedStatement.setLong(5, driver.getId());
            int updateRows = preparedStatement.executeUpdate();
            if (updateRows > 0) {
                return driver;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update driver "
                    + driver, e);
        }
        throw new DataProcessingException("No driver with id " + driver.getId());
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE drivers  SET deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            int updateRows = preparedStatement.executeUpdate();
            return updateRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete driver by id " + id, e);
        }
    }

    public Optional<Driver> findByLogin(String login) {
        String query = "SELECT * FROM drivers WHERE login = ? AND deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            Optional<Driver> optional = Optional.empty();
            if (resultSet.next()) {
                optional = Optional.ofNullable(getDriverFromResultSet(resultSet));
            }
            return optional;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get driver by login " + login, e);
        }
    }

    private Driver getDriverFromResultSet(ResultSet resultSet) throws SQLException {
        long driverId = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String licenceNumber = resultSet.getString("licence");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        Driver driver = new Driver(name,licenceNumber);
        driver.setId(driverId);
        driver.setLogin(login);
        driver.setPassword(password);
        return driver;
    }
}
