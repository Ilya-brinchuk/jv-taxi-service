package core.basesyntax.security;

import core.basesyntax.exception.AuthenticationException;
import core.basesyntax.lib.Inject;
import core.basesyntax.lib.Service;
import core.basesyntax.model.Driver;
import core.basesyntax.service.DriverService;
import java.util.Optional;

@Service
public class AuthenticationServiceForDriver implements AuthenticationService<Driver> {
    @Inject
    private DriverService driverService;

    @Override
    public Driver login(String login, String password) throws AuthenticationException {
        Optional<Driver> driverFromDB = driverService.findByLogin(login);
        if (driverFromDB.isPresent()
                && driverFromDB.get().getPassword().equals(password)) {
            return driverFromDB.get();
        }
        throw new AuthenticationException("Incorrect username or password");
    }
}
