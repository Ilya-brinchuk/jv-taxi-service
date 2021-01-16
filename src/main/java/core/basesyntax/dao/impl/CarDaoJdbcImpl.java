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
        String query = "INSERT cars (model, manufacturer_id) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, car.getModel());
            preparedStatement.setLong(2, car.getManufacturer().getId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                car.setId(resultSet.getObject(1, Long.class));
                addDriversForCar(car);
            }
            return car;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert car " + car, e);
        }
    }

    @Override
    public Optional<Car> get(Long id) {
        String query = "SELECT cars.car_id, cars.model, cars.manufacturer_id,"
                + " manufacturers.manufacturer_country, manufacturers.manufacturer_name "
                + "FROM service_taxi.cars "
                + "Inner join service_taxi.manufacturers "
                + "ON cars.manufacturer_id = manufacturers.manufacturer_id "
                + "Where cars.car_id = ? AND cars.deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Optional<Car> optional = Optional.empty();
            if (resultSet.next()) {
                Car car = getCarFromResultSet(resultSet);
                car.setDrivers(getDriversFromCar(car.getId()));
                return Optional.of(car);
            }

            return optional;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get car by id " + id, e);
        }
    }

    @Override
    public List<Car> getAll() {
        String query = "SELECT cars.car_id, cars.model, cars.manufacturer_id,"
                + " manufacturers.manufacturer_country, manufacturers.manufacturer_name "
                + "FROM service_taxi.cars "
                + "Inner join service_taxi.manufacturers "
                + "ON cars.manufacturer_id = manufacturers.manufacturer_id "
                + "Where cars.deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Car> cars = new ArrayList<>();
            while (resultSet.next()) {
                Car car = getCarFromResultSet(resultSet);
                car.setDrivers(getDriversFromCar(car.getId()));
                cars.add(car);
            }
            return cars;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all cars ", e);
        }
    }

    @Override
    public Car update(Car car) {
        String query = "UPDATE cars SET model = ?, manufacturer_id = ?"
                + " WHERE car_id = ? AND deleted = false ";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, car.getModel());
            preparedStatement.setLong(2, car.getManufacturer().getId());
            preparedStatement.setLong(3, car.getId());
            preparedStatement.executeUpdate();
            deleteDriversForCar(car.getId());
            addDriversForCar(car);
            int updateRows = preparedStatement.executeUpdate();
            if (updateRows > 0) {
                return car;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update this car " + car, e);
        }
        throw new DataProcessingException("No car with id " + car.getId());
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE cars SET deleted = true WHERE car_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            deleteDriversForCar(id);
            int updateRows = preparedStatement.executeUpdate();
            return updateRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete car by id " + id, e);
        }
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        String query = " SELECT cars.car_id as car_id,"
                + " cars.model as model,"
                + " cars.manufacturer_id as manufacturer_id,"
                + " manufacturers.manufacturer_name as manufacturer_name, "
                + " manufacturers.manufacturer_country as manufacturer_country "
                + "FROM cars_drivers "
                + "INNER JOIN cars "
                + "ON cars_drivers.car_id = cars.car_id "
                + "INNER JOIN manufacturers "
                + "ON cars.manufacturer_id = manufacturers.manufacturer_id "
                + "WHERE cars_drivers.driver_id = ? AND cars.deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, driverId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Car> cars = new ArrayList<>();
            while (resultSet.next()) {
                Long carId = resultSet.getObject(1, Long.class);
                String model = resultSet.getString("model");
                Long manufacturerId = resultSet.getObject(3, Long.class);
                String manufacturerName = resultSet.getString("manufacturer_name");
                String manufacturerCountry = resultSet.getString("manufacturer_country");
                Manufacturer manufacturer = new Manufacturer(manufacturerName, manufacturerCountry);
                manufacturer.setId(manufacturerId);
                Car car = new Car(model, manufacturer);
                car.setId(carId);
                car.setDrivers(getDriversFromCar(carId));
                cars.add(car);
            }
            return cars;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get cars by driver id " + driverId, e);
        }
    }

    private Car getCarFromResultSet(ResultSet resultSet) throws SQLException {
        Long carId = resultSet.getLong("car_id");
        String model = resultSet.getString("model");
        Long manufacturerId = resultSet.getLong("manufacturer_id");
        String manufacturerCountry = resultSet.getString("manufacturer_country");
        String manufacturerName = resultSet.getString("manufacturer_name");
        Manufacturer manufacturer = new Manufacturer(manufacturerName, manufacturerCountry);
        manufacturer.setId(manufacturerId);
        Car car = new Car(model, manufacturer);
        car.setId(carId);
        return car;
    }

    private List<Driver> getDriversFromCar(Long carId) {
        String query = "SELECT drivers.driver_id, drivers.driver_name, drivers.driver_licence "
                + "FROM cars_drivers "
                + "INNER JOIN drivers "
                + "ON cars_drivers.driver_id = drivers.driver_id "
                + "WHERE cars_drivers.car_id = ? AND drivers.deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, carId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Driver> drivers = new ArrayList<>();
            while (resultSet.next()) {
                Long driverID = resultSet.getObject("driver_id", Long.class);
                String driverName = resultSet.getString("driver_name");
                String driverLicence = resultSet.getString("driver_licence");
                Driver driver = new Driver(driverName, driverLicence);
                driver.setId(driverID);
                drivers.add(driver);
            }
            return drivers;
        } catch (SQLException e) {
            throw new DataProcessingException("", e);
        }
    }

    private void deleteDriversForCar(Long carId) {
        String query = "DELETE FROM cars_drivers WHERE car_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, carId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete drivers by car id " + carId, e);
        }
    }

    private void addDriversForCar(Car car) {
        String query = "INSERT cars_drivers (driver_id, car_id) VALUES (?, ?) ";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            List<Driver> drivers = car.getDrivers();
            int quantityDrivers = drivers.size();
            for (int i = 0; i < quantityDrivers; i++) {
                preparedStatement.setLong(1, drivers.get(i).getId());
                preparedStatement.setLong(2, car.getId());
                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can't add drivers for the car " + car, e);
        }
    }

}
