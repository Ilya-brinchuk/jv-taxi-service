package core.basesyntax;

import core.basesyntax.lib.Injector;
import core.basesyntax.model.Car;
import core.basesyntax.model.Driver;
import core.basesyntax.model.Manufacturer;
import core.basesyntax.service.CarService;
import core.basesyntax.service.DriverService;
import core.basesyntax.service.ManufacturerService;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final Injector injector = Injector.getInstance("core.basesyntax");

    public static void main(String[] args) {
        ManufacturerService manufacturerService =
                (ManufacturerService) injector.getInstance(ManufacturerService.class);
        Manufacturer toyota = new Manufacturer("Toyota", "Japan");
        Manufacturer ford = new Manufacturer("Ford", "USA");
        Manufacturer bmw = new Manufacturer("BMW", "Germany");
        manufacturerService.create(toyota);
        manufacturerService.create(ford);
        manufacturerService.create(bmw);
        System.out.println(manufacturerService.get(1L));
        bmw.setName("BMW company");
        manufacturerService.update(bmw);
        manufacturerService.delete(1L);
        System.out.println(manufacturerService.getAll());

        DriverService driverService =
                (DriverService) injector.getInstance(DriverService.class);
        Driver driverBob = new Driver("Bob", "555544");
        Driver driverAlice = new Driver("Alice", "666554");
        driverService.create(driverAlice);
        driverService.create(driverBob);
        driverBob.setName("BobAlison");
        driverService.update(driverBob);
        List<Driver> drivers = new ArrayList<>();
        drivers.add(driverAlice);
        drivers.add(driverBob);

        CarService carService = (CarService) injector.getInstance(CarService.class);
        Car carFord = new Car("Ford",manufacturerService.get(2L));
        Car carBmw = new Car("BMW", manufacturerService.get(3L));
        carService.create(carFord);
        carService.create(carBmw);
        carFord.setDrivers(drivers);
        carBmw.setDrivers(drivers);
        carBmw.setModel("X5");
        carService.update(carBmw);
        carService.removeDriverFromCar(driverAlice, carFord);
        carService.removeDriverFromCar(driverBob, carFord);
        carService.addDriverToCar(driverAlice, carFord);
        System.out.println(carFord);
        System.out.println(carService.getAllByDriver(1L));
        System.out.println(carService.get(1L));
        carService.delete(1L);
        System.out.println(carService.getAll());
        System.out.println(driverService.get(1L));
        driverService.delete(1L);
        System.out.println(driverService.getAll());

    }
}
