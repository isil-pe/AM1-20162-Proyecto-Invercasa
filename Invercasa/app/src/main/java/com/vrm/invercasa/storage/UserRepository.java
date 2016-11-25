package com.vrm.invercasa.storage;

import com.vrm.invercasa.model.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private List<UserEntity> users;

    public UserRepository() {
        users = new ArrayList<>();
    }

    public void mock() {
        users.add(new UserEntity(1, "", "Giovanni", "Vilela", "", "", "giovanni.vilela@outlook.com", "123456"));
        users.add(new UserEntity(2, "", "Daniel", "Ponce", "", "", "daniel31_5@hotmail.com", "123456"));
        users.add(new UserEntity(3, "", "Axel", "Rebollar", "", "", "axel_r_18@hotmail.com", "123456"));
        users.add(new UserEntity(4, "", "Mauricio", "Navarro", "", "", "maurinava_25@hotmail.com", "123456"));
    }

    public void add(UserEntity User) {
        users.add(User);
    }

    public void remove(UserEntity User) {
        users.remove(User);
    }

    public void removeById(int id) {
        int position = -1;
        UserEntity user;
        for (int i = 0; i < users.size() ; i++) {
            user = users.get(i);
            if(user.getId() == id) {
                position = i;
                break;
            }
        }
        if(position >= 0 && position < users.size())
            users.remove(position);
    }

    public void update(UserEntity user) {
        int position = -1;
        UserEntity rest;
        for (int i = 0; i < users.size() ; i++) {
            rest = users.get(i);
            if(rest.getId() == user.getId()) {
                position = i;
                break;
            }
        }
        if(position>=0 && position< users.size())
            users.set(position, user);
    }

    public List<UserEntity> list() {
        return users;
    }

    public int count() {
        return users.size();
    }

    public UserEntity last() {
        if(users.size() > 0)
            return users.get(users.size() - 1);
        return null;
    }
}
