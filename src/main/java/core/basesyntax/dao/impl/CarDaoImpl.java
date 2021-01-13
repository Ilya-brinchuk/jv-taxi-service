package core.basesyntax.dao.impl;

import core.basesyntax.dao.CarDao;
import core.basesyntax.db.Storage;
import core.basesyntax.lib.Dao;
import core.basesyntax.model.Car;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Dao
public class CarDaoImpl implements CarDao {
    @Override
    public Car create(Car car) {
        Storage.addCar(car);
        return car;
    }

    @Override
    public Car get(Long id) {
        return Storage.cars.stream()
                .filter(car -> car.getId().equals(id))
                .findFirst()
                .get();
    }

    @Override
    public List<Car> getAll() {
        return Storage.cars;
    }

    @Override
    public Car update(Car car) {
        IntStream.range(0, Storage.cars.size())
                .filter(i -> Storage.cars.get(i).getId().equals(car.getId()))
                .forEach(i -> Storage.cars.set(i, car));
        return car;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.cars.removeIf(car -> car.getId().equals(id));
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        return Storage.cars.stream()
                .filter(car -> car.getDrivers()
                        .stream()
                        .anyMatch(driver -> driver.getId().equals(driverId)))
                .collect(Collectors.toList());
    }
}
