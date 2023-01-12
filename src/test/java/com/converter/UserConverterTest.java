package com.converter;

import com.dto.UserDto;
import com.entity.UserEntity;
import com.util.DateUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserConverterTest {

    @Test
    void shouldConvert() {

        final UserEntity user = UserEntity.builder()
            .id(RandomUtils.nextLong())
            .firstName(RandomStringUtils.randomAlphanumeric(3))
            .lastName(RandomStringUtils.randomAlphanumeric(3))
            .phone("0546" + RandomStringUtils.randomNumeric(7))
            .createdDate(DateUtil.nowAsDate())
            .build();

        final UserDto userDto = UserConverter.convert(user);

        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getFirstName(), userDto.getFirstName());
        assertEquals(user.getLastName(), userDto.getLastName());
        assertEquals(user.getPhone(), userDto.getPhone());
        assertEquals(user.getCreatedDate(), userDto.getCreatedDate());
    }
}