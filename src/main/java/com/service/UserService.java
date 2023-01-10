package com.service;

import com.dto.UserDto;

public interface UserService {

    Long save(UserDto userDto);
    void delete(Long userId);
    UserDto detail(Long userId);
}
