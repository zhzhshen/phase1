package model;

import annotation.Column;
import annotation.Entity;
import annotation.Id;
import annotation.Table;

@Entity
@Table(name="users")
public class User {
    @Id
    @Column(name="id")
    public String userId;

    @Column
    public String firstName;

    @Column
    public String lastName;

    @Column
    public int age;

    public User() {
    }

    public User(String userId, String firstName, String lastName, int age) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (age != user.age) return false;
        if (userId != null ? !userId.equals(user.userId) : user.userId != null) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        return lastName != null ? lastName.equals(user.lastName) : user.lastName == null;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + age;
        return result;
    }
}
