package com.service;

import com.dto.UserDto;

import java.util.Locale;

public interface UserService {

    Long save(UserDto userDto);
    void delete(Long userId, Locale locale);
    UserDto detail(Long userId, Locale locale);
}