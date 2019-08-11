package com.casper.userslibrary.model.repository;

import com.casper.userslibrary.model.entity.User;
import com.casper.userslibrary.model.exception.OperationFailedException;

import java.util.List;

public interface UserRepository {
    void save(User user) throws OperationFailedException;

    void update(User user) throws OperationFailedException;

    void delete(User user) throws OperationFailedException;

    List<User> getAll() throws OperationFailedException;

    User getById(long id) throws OperationFailedException;
}
