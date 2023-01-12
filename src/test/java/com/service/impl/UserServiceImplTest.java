package com.service.impl;

import com.dto.UserDto;
import com.entity.UserEntity;
import com.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;

    @Test
    void shouldSave() {

        final UserDto dto = UserDto.builder()
            .firstName(RandomStringUtils.randomAlphanumeric(3))
            .lastName(RandomStringUtils.randomAlphanumeric(3))
            .phone("0546" + RandomStringUtils.randomNumeric(7))
            .build();

        final UserEntity user = UserEntity.builder()
            .id(RandomUtils.nextLong())
            .build();

        when(userRepository.save(any(UserEntity.class))).thenReturn(user);
        final Long response = userService.save(dto);

        assertEquals(user.getId(), response);

        ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(captor.capture());
        UserEntity captorValue = captor.getValue();

        assertEquals(dto.getFirstName(), captorValue.getFirstName());
        assertEquals(dto.getLastName(), captorValue.getLastName());
        assertEquals(dto.getPhone(), captorValue.getPhone());
    }

    @Test
    void shouldDelete() {
        final Long userId = RandomUtils.nextLong();
        userService.delete(userId);
    }
}