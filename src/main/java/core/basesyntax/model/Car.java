package core.basesyntax.model;

import java.util.ArrayList;
import java.util.List;

public class Car {
    private long id;
    private String model;
    private Manufacturer manufacturer;
    private List<Driver> drivers;

    public Car(String model, Manufacturer manufacturer) {
        this.model = model;
        this.manufacturer = manufacturer;
        this.drivers = new ArrayList<>();
    }
}
