package com.service.impl;

import com.dto.UserDto;
import com.entity.UserEntity;
import com.exception.UserNotFoundException;
import com.repository.UserRepository;
import com.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

import static com.constant.CacheConstants.USER_DETAIL_CACHE;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public Long save(UserDto userDto) {
        final UserEntity user = UserEntity.builder()
            .firstName(userDto.getFirstName())
            .lastName(userDto.getLastName())
            .phone(userDto.getPhone())
            .build();

        return userRepository.save(user).getId();
    }

    @Override
    @Transactional
    @CacheEvict(value = USER_DETAIL_CACHE, key = "#userId", allEntries = true)
    public void delete(Long userId, Locale locale) {
        final UserEntity userEntity = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(locale));

        userRepository.deleteById(userEntity.getId());
    }

    @Override
    @Cacheable(value = USER_DETAIL_CACHE, key = "#userId")
    public UserEntity detail(Long userId, Locale locale) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(locale));
    }
}
