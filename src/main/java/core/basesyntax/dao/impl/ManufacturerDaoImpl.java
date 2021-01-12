package core.basesyntax.dao.impl;

import core.basesyntax.dao.ManufacturerDao;
import core.basesyntax.db.Storage;
import core.basesyntax.lib.Dao;
import core.basesyntax.model.Manufacturer;
import java.util.List;
import java.util.Optional;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        Storage.addManufacturer(manufacturer);
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        for (Manufacturer manufacturer : Storage.manufacturers) {
            if (manufacturer.getId() == id) {
                return Optional.of(manufacturer);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        return Storage.manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        for (Manufacturer oldManufacturer : Storage.manufacturers) {
            if (oldManufacturer.getId() == manufacturer.getId()) {
                int index = Storage.manufacturers.indexOf(oldManufacturer);
                Storage.manufacturers.set(index, manufacturer);
                return manufacturer;
            }
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        for (Manufacturer manufacturer : Storage.manufacturers) {
            if (manufacturer.getId() == id) {
                Storage.manufacturers.remove(manufacturer);
                return true;
            }
        }
        return false;
    }
}
