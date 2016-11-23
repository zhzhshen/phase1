package api;

import model.User;

public interface SessionImpl {
    User currentUser();

    boolean validate(User resourceOwner);
}
