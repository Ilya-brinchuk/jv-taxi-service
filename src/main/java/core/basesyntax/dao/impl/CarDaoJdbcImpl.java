package core.basesyntax.dao.impl;

import core.basesyntax.dao.CarDao;
import core.basesyntax.exception.DataProcessingException;
import core.basesyntax.lib.Dao;
import core.basesyntax.model.Car;
import core.basesyntax.model.Driver;
import core.basesyntax.model.Manufacturer;
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
public class CarDaoJdbcImpl implements CarDao {
    @Override
    public Car create(Car car) {
        String query = "INSERT INTO cars (model, manufacturer_id) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, car.getModel());
            preparedStatement.setLong(2, car.getManufacturer().getId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                car.setId(resultSet.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert car " + car, e);
        }
        addDriversToCar(car);
        return car;
    }

    @Override
    public Optional<Car> get(Long id) {
        String query = "SELECT c.id as car_id, "
                + "c.model as car_model, c.manufacturer_id,"
                + " m.country as  manufacturer_country,"
                + " m.name as manufacturer_name "
                + "FROM cars c "
                + "INNER JOIN manufacturers m "
                + "ON c.manufacturer_id = m.id "
                + "WHERE c.id = ? AND c.deleted = false";
        Car car = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                car = getCarFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get car by id " + id, e);
        }
        if (car != null) {
            car.setDrivers(getDriversFromCar(car.getId()));
        }
        return Optional.ofNullable(car);
    }

    @Override
    public List<Car> getAll() {
        String query = "SELECT c.id as car_id, c.model as car_model, "
                + "c.manufacturer_id, "
                + "m.country as manufacturer_country, "
                + "m.name as manufacturer_name "
                + "FROM cars c "
                + "Inner join manufacturers m "
                + "ON c.manufacturer_id = m.id "
                + "WHERE c.deleted = false";
        List<Car> cars;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            cars = getCars(resultSet);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all cars ", e);
        }
        for (Car car : cars) {
            car.setDrivers(getDriversFromCar(car.getId()));
        }
        return cars;
    }

    @Override
    public Car update(Car car) {
        String query = "UPDATE cars SET model = ?, manufacturer_id = ?"
                + " WHERE id = ? AND deleted = false ";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, car.getModel());
            preparedStatement.setLong(2, car.getManufacturer().getId());
            preparedStatement.setLong(3, car.getId());
            int updateRows = preparedStatement.executeUpdate();
            if (updateRows <= 0) {

                throw new DataProcessingException("No car with id " + car.getId());
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update this car " + car, e);
        }
        deleteDriversFromCar(car.getId());
        addDriversToCar(car);
        return car;

    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE cars SET deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            deleteDriversFromCar(id);
            int updateRows = preparedStatement.executeUpdate();
            return updateRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete car by id " + id, e);
        }
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        String query = " SELECT c.id as car_id, "
                + "c.model as car_model, "
                + "c.id as manufacturer_id, "
                + "m.country as manufacturer_country, "
                + "m.name as manufacturer_name "
                + "FROM cars_drivers cd "
                + "INNER JOIN cars c "
                + "ON cd.car_id = c.id "
                + "INNER JOIN manufacturers m "
                + "ON c.manufacturer_id = m.id "
                + "INNER JOIN drivers d "
                + "ON d.id = cd.driver_id "
                + "WHERE cd.driver_id = ? AND c.deleted = false AND d.deleted = false";
        List<Car> cars;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, driverId);
            ResultSet resultSet = preparedStatement.executeQuery();
            cars = getCars(resultSet);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get cars by driver id " + driverId, e);
        }
        for (Car car : cars) {
            car.setDrivers(getDriversFromCar(car.getId()));
        }
        return cars;
    }

    private List<Car> getCars(ResultSet resultSet) throws SQLException {
        List<Car> cars = new ArrayList<>();
        while (resultSet.next()) {
            Car car = getCarFromResultSet(resultSet);
            cars.add(car);
        }
        return cars;
    }

    private Car getCarFromResultSet(ResultSet resultSet) throws SQLException {
        Long carId = resultSet.getObject("car_id", Long.class);
        String model = resultSet.getString("car_model");
        Long manufacturerId = resultSet.getObject("manufacturer_id", Long.class);
        String manufacturerCountry = resultSet.getString("manufacturer_country");
        String manufacturerName = resultSet.getString("manufacturer_name");
        Manufacturer manufacturer = new Manufacturer(manufacturerName, manufacturerCountry);
        manufacturer.setId(manufacturerId);
        Car car = new Car(model, manufacturer);
        car.setId(carId);
        return car;
    }

    private List<Driver> getDriversFromCar(Long carId) {
        String query = "SELECT d.id as driver_id, d.name as driver_name, "
                + "d.licence as driver_licence "
                + "FROM cars_drivers cd "
                + "INNER JOIN drivers d "
                + "ON cd.driver_id = d.id "
                + "WHERE cd.car_id = ? AND d.deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, carId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Driver> drivers = new ArrayList<>();
            while (resultSet.next()) {
                Long driverId = resultSet.getObject("driver_id", Long.class);
                String driverName = resultSet.getString("driver_name");
                String driverLicence = resultSet.getString("driver_licence");
                Driver driver = new Driver(driverName, driverLicence);
                driver.setId(driverId);
                drivers.add(driver);
            }
            return drivers;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get drivers by car id " + carId, e);
        }
    }

    private void deleteDriversFromCar(Long carId) {
        String query = "DELETE FROM cars_drivers WHERE car_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, carId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete drivers by car id " + carId, e);
        }
    }

    private void addDriversToCar(Car car) {
        String query = "INSERT cars_drivers (driver_id, car_id) VALUES (?, ?) ";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            List<Driver> drivers = car.getDrivers();
            for (Driver driver : drivers) {
                preparedStatement.setLong(1, driver.getId());
                preparedStatement.setLong(2, car.getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add drivers for the car " + car, e);
        }
    }
}
