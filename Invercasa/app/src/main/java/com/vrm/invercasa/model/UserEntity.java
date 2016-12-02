package com.vrm.invercasa.model;

import com.vrm.invercasa.storage.UserRepository;

import java.io.Serializable;

public class UserEntity implements Serializable{
    private int id;
    private String dni;
    private String name;
    private String lastName;
    private String address;
    private String phone;
    private String email;
    private String password;

    public UserEntity(String dni, String name, String lastName, String address, String phone, String email, String password) {
        this.id = UserRepository.getIdentity() + 1;
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
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return name + " " + lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}