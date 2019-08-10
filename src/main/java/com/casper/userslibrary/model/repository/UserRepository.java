package com.casper.userslibrary.model.repository;

import com.casper.userslibrary.model.entity.User;

import java.util.List;

public interface UserRepository {
    void save(User user);

    void update(User user);

    List<User> getAll();

    User getById(long id);
}
