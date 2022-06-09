package lt.codeacademy.data;

import org.bson.types.ObjectId;

import java.math.BigDecimal;

public class User {
    private ObjectId id;
    private String name;
    private String surname;
    private Double salary;

    public User(ObjectId id, String name, String surname, Double accMoney) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.salary = accMoney;
    }

    public User() {
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name='" + name + '\'' + ", surname='" + surname + '\'' + ", accMoney=" + salary + '}';
    }
}

