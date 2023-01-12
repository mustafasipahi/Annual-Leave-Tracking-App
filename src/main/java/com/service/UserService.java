package com.service;

import com.dto.UserDto;
import com.entity.UserEntity;

public interface UserService {

    Long save(UserDto userDto);
    void delete(Long userId);
    UserEntity detail(Long userId);
    UserDto info(Long userId);
}
