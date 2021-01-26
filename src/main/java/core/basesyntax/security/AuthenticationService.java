package core.basesyntax.security;

import core.basesyntax.exception.AuthenticationException;

public interface AuthenticationService<T> {
    T login(String login,String password) throws AuthenticationException;
}
