package core.basesyntax.dao;

import core.basesyntax.model.Car;
import java.util.List;

public interface CarDao {

    Car create(Car car);

    Car get(Long id);

    List<Car> getAll();

    Car update(Car car);

    boolean delete(Long id);

    List<Car> getAllByDriver(Long driverId);
}
