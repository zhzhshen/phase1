package com.sid.session;

import com.sid.spi.model.User;

public interface Session {
    boolean validate();

    User currentUser();
}
