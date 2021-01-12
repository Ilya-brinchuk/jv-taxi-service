package core.basesyntax.db;

import core.basesyntax.model.Manufacturer;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    public static final List<Manufacturer> manufacturers = new ArrayList<>();
    private static long id = 0;

    public static void addManufacturer(Manufacturer manufacturer) {
        manufacturer.setId(++id);
        manufacturers.add(manufacturer);

    }
}
