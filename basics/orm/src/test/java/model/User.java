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
}
