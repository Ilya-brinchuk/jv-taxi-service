package core.basesyntax.security;

public interface AuthenticationService<T> {
    T login(String login,String password);
}
