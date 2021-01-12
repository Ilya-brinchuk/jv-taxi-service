package core.basesyntax.dao.impl;

import core.basesyntax.dao.ManufacturerDao;
import core.basesyntax.db.Storage;
import core.basesyntax.lib.Dao;
import core.basesyntax.model.Manufacturer;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        Storage.addManufacturer(manufacturer);
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        return Storage.manufacturers.stream()
                .filter((s) -> Objects.equals(s.getId(), id))
                .findFirst();
    }

    @Override
    public List<Manufacturer> getAll() {
        return Storage.manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        IntStream.range(0, Storage.manufacturers.size())
                .filter(i -> Storage.manufacturers.get(i).getId() == manufacturer.getId())
                .forEach(i -> Storage.manufacturers.set(i, manufacturer));
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.manufacturers.removeIf(manufacturer -> manufacturer.getId() == id);
    }
}
