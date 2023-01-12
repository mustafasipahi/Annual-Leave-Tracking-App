package com.service.impl;

import com.dto.UserDto;
import com.entity.UserEntity;
import com.exception.UserNotFoundException;
import com.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.constant.ErrorCodes.USER_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

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
    void shouldThrowUserNotFoundExceptionDeleteWhenUserEntityNotFound() {
        final Long userId = RandomUtils.nextLong();

        final UserNotFoundException exception = assertThrows(
            UserNotFoundException.class,
            () -> userService.delete(userId)
        );
        assertEquals(USER_NOT_FOUND, exception.getCode());
        assertEquals(BAD_REQUEST, exception.getStatus());
        assertEquals("user.not.found", exception.getMessageKey());
    }

    @Test
    void shouldDelete() {
        final Long userId = RandomUtils.nextLong();

        final UserEntity user = UserEntity.builder()
            .id(userId)
            .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        userService.delete(userId);
        verify(userRepository).deleteById(user.getId());
    }

    @Test
    void shouldThrowUserNotFoundExceptionDetailWhenUserEntityNotFound() {
        final Long userId = RandomUtils.nextLong();

        final UserNotFoundException exception = assertThrows(
            UserNotFoundException.class,
            () -> userService.detail(userId)
        );
        assertEquals(USER_NOT_FOUND, exception.getCode());
        assertEquals(BAD_REQUEST, exception.getStatus());
        assertEquals("user.not.found", exception.getMessageKey());
    }

    @Test
    void shouldGetDetail() {
        final Long userId = RandomUtils.nextLong();

        final UserEntity user = UserEntity.builder()
            .id(userId)
            .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        final UserEntity detail = userService.detail(userId);

        assertEquals(userId, detail.getId());
        verify(userRepository).findById(user.getId());
    }
}