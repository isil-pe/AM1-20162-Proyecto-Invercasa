package com.vrm.invercasa.storage;

import com.vrm.invercasa.model.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private List<UserEntity> users;
    private static int identity;

    public UserRepository() {
        identity = 0;
        users = new ArrayList<>();
    }

    public void mock() {
        users.add(new UserEntity("", "Giovanni", "Vilela", "", "", "giovanni.vilela@outlook.com", "123456"));
        users.add(new UserEntity("", "Daniel", "Ponce", "", "", "daniel31_5@hotmail.com", "123456"));
        users.add(new UserEntity("", "Axel", "Rebollar", "", "", "axel_r_18@hotmail.com", "123456"));
        users.add(new UserEntity("", "Mauricio", "Navarro", "", "", "maurinava_25@hotmail.com", "123456"));
    }

    public static int getIdentity() {
        return identity;
    }

    public boolean add(UserEntity user) {
        identity++;
        if (findUserByEmail(user.getEmail()) == -1) {
            users.add(user);
            return true;
        }
        return false;
    }

    public void remove(UserEntity User) {
        users.remove(User);
    }

    public void removeById(int id) {
        int position = findUserById(id);
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

    public int findUserById(int id) {
        int position = -1;
        UserEntity user;
        if (id == -1)
            return -1;
        for (int i = 0; i < users.size() ; i++) {
            user = users.get(i);
            if(user.getId() == id) {
                position = i;
                break;
            }
        }
        return position;
    }

    public int findUserByEmail(String email) {
        int position = -1;
        UserEntity user;
        for (int i = 0; i < users.size() ; i++) {
            user = users.get(i);
            if(user.getEmail().equals(email)) {
                position = i;
                break;
            }
        }
        return position;
    }

    public UserEntity getUserById(int id) {
        int position = findUserById(id);
        UserEntity user = null;
        if (id == -1)
            return null;
        if (position != -1 && position < users.size())
            user = users.get(position);
        return user;
    }

    public UserEntity getUserByEmail(String email) {
        int position = findUserByEmail(email);
        UserEntity user = null;
        if (position != -1 && position < users.size())
            user = users.get(position);
        return user;
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
