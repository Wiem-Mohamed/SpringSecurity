package com.global.hr.repository;

import com.global.hr.entity.User;
import com.global.hr.exception.UserAlreadyExistsException;
import com.global.hr.exception.UserNotFoundException;
import com.global.hr.entity.UserRecord;


import java.util.List;

public interface IUserService {
    User add(User user) throws UserAlreadyExistsException;
    List<UserRecord> getAllUsers();
    void delete(String email);
    User getUser(String email) throws UserNotFoundException;
    User update(User user);
}