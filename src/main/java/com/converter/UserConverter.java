package com.converter;

import com.dto.UserDto;
import com.entity.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserConverter {

    public static UserDto convert(UserEntity user) {
        return UserDto.builder()
            .id(user.getId())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .phone(user.getPhone())
            .createdDate(user.getCreatedDate())
            .build();
    }
}
