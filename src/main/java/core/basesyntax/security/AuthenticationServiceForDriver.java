package core.basesyntax.security;

import core.basesyntax.exception.AuthenticationException;
import core.basesyntax.lib.Inject;
import core.basesyntax.lib.Service;
import core.basesyntax.model.Driver;
import core.basesyntax.service.DriverService;

@Service
public class AuthenticationServiceForDriver implements AuthenticationService<Driver> {
    @Inject
    DriverService driverService;

    @Override
    public Driver login(String login, String password) {
        Driver driver = driverService.findByLogin(login)
                .orElseThrow(() -> new AuthenticationException("Wrong login"));
        if (driver.getPassword().equals(password)) {
            return driver;
        }
        throw new AuthenticationException("Wrong password");
    }
}
