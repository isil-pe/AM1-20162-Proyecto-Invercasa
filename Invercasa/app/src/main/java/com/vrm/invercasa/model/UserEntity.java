package com.vrm.invercasa.model;

public class UserEntity {
    private int id;
    private boolean type;
    private String dni;
    private String name;
    private String lastName;
    private String address;
    private String phone;
    private String email;
    private String password;

    public UserEntity(int id) {
        this.id = id;
    }

    public UserEntity(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserEntity(int id, String dni, String name, String lastName, String address, String phone, String email, String password) {
        type = true;
        this.id = id;
        this.dni = dni;
        this.name = name;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public boolean isType() {
        return type;
    }

    public String getDni() {
        return dni;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return name + " " + lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}