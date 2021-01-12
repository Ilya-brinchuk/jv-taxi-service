package core.basesyntax;

import core.basesyntax.lib.Injector;
import core.basesyntax.model.Manufacturer;
import core.basesyntax.service.ManufacturerService;

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
        System.out.println(manufacturerService.getAll());
        manufacturerService.delete(1L);
        System.out.println(manufacturerService.getAll());
        Manufacturer updateBmw = manufacturerService.get(3L);
        updateBmw.setName("BMW company");
        manufacturerService.update(updateBmw);
        System.out.println(manufacturerService.getAll());

    }
}
