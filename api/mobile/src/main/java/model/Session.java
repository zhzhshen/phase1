package model;

public interface Session {
    Boolean isOperator();

    User currentUser();
}
