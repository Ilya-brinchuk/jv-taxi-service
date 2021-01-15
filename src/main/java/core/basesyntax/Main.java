package core.basesyntax;

import core.basesyntax.lib.Injector;
import core.basesyntax.model.Manufacturer;
import core.basesyntax.service.ManufacturerService;

public class Main {
    private static final Injector injector = Injector.getInstance("core.basesyntax");

    public static void main(String[] args) {
        ManufacturerService manufacturerService =
                (ManufacturerService) injector.getInstance(ManufacturerService.class);
        Manufacturer manufacturerTesla = new Manufacturer("TESla", "USA");
        Manufacturer manufacturerBmw = new Manufacturer("BMW", "Germany");
        System.out.println(manufacturerService.create(manufacturerTesla));
        System.out.println(manufacturerService.create(manufacturerBmw));
        System.out.println(manufacturerService.get(manufacturerBmw.getId()));
        manufacturerBmw.setName("BMW company");
        System.out.println(manufacturerService.update(manufacturerBmw));
        System.out.println(manufacturerService.delete(manufacturerTesla.getId()));
        System.out.println(manufacturerService.getAll());
    }
}
