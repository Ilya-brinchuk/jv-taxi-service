package core.basesyntax.db;

import core.basesyntax.model.Car;
import core.basesyntax.model.Driver;
import core.basesyntax.model.Manufacturer;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    public static final List<Manufacturer> manufacturers = new ArrayList<>();
    public static final List<Car> cars = new ArrayList<>();
    public static final List<Driver> drivers = new ArrayList<>();

    private static long manufacturesId = 0;
    private static long carsId = 0;
    private static long driversId = 0;

    public static void addManufacturer(Manufacturer manufacturer) {
        manufacturer.setId(++manufacturesId);
        manufacturers.add(manufacturer);

    }

    public static void addCar(Car car) {
        car.setId(++carsId);
        cars.add(car);
    }

    public static void addDriver(Driver driver) {
        driver.setId(++driversId);
        drivers.add(driver);
    }
}
