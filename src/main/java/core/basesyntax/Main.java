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
        Manufacturer manufacturerTesla = new Manufacturer("TESla", "USA");
        Manufacturer manufacturerBmw = new Manufacturer("BMW new company", "Germany");
        System.out.println(manufacturerService.create(manufacturerTesla));
        System.out.println(manufacturerService.create(manufacturerBmw));
        System.out.println(manufacturerService.get(manufacturerTesla.getId()));
        System.out.println(manufacturerService.getAll());
        manufacturerTesla.setName("Tesla Mask");
        System.out.println(manufacturerService.update(manufacturerTesla));

        DriverService driverService = (DriverService) injector.getInstance(DriverService.class);
        Driver driverBill = new Driver("Bill", "77728");
        Driver driverJohn = new Driver("John", "22273");
        driverService.create(driverBill);
        driverService.create(driverJohn);
        System.out.println(driverService.getAll());
        driverJohn.setName("John Alison");
        System.out.println(driverService.update(driverJohn));
        System.out.println(driverService.get(driverJohn.getId()));

        CarService carService = (CarService) injector.getInstance(CarService.class);
        Car carBmw = new Car("X89", manufacturerBmw);
        carService.create(carBmw);
        List<Driver> driversForBmw = new ArrayList<>();
        driversForBmw.add(driverBill);
        driversForBmw.add(driverJohn);
        carBmw.setDrivers(driversForBmw);
        System.out.println(carService.update(carBmw));
        System.out.println(carService.getAllByDriver(driverJohn.getId()));
        System.out.println(carService.getAll());
        System.out.println(carService.get(carBmw.getId()));

        System.out.println(carService.delete(carBmw.getId()));
        System.out.println(driverService.delete(driverJohn.getId()));
        System.out.println(driverService.delete(driverJohn.getId()));
        System.out.println(manufacturerService.delete(manufacturerTesla.getId()));
        System.out.println(manufacturerService.delete(manufacturerTesla.getId()));
    }
}
