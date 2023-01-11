package com.service;

import com.dto.UserDto;
import com.entity.UserEntity;

import java.util.Locale;

public interface UserService {

    Long save(UserDto userDto);
    void delete(Long userId, Locale locale);
    UserEntity detail(Long userId, Locale locale);
}
