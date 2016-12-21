package model;

import annotation.*;

@Entity
@Table(name="UserInfo")
public class UserInfo {
    @Id
    @Column
    public String id;

    @Column
    public String phone;

    @Column
    public String address;

    public UserInfo() {
    }

    public UserInfo(String id, String phone, String address) {
        this.id = id;
        this.phone = phone;
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserInfo userInfo = (UserInfo) o;

        if (phone != null ? !phone.equals(userInfo.phone) : userInfo.phone != null) return false;
        return address != null ? address.equals(userInfo.address) : userInfo.address == null;
    }

    @Override
    public int hashCode() {
        int result = phone != null ? phone.hashCode() : 0;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }
}
