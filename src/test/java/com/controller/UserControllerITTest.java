package com.controller;

import com.Application;
import com.dto.UserDto;
import com.entity.UserEntity;
import com.exception.UserNotFoundException;
import com.repository.UserRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
public class UserControllerITTest {

    @Autowired
    private UserRepository userRepository;

    @LocalServerPort
    private int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @After
    public void destroy() {
        userRepository.deleteAll();
    }

    @Test
    public void shouldSave() {

        assertTrue(userRepository.findAll().isEmpty());

        final UserDto userDto = UserDto.builder()
            .firstName(RandomStringUtils.randomAlphanumeric(3))
            .lastName(RandomStringUtils.randomAlphanumeric(3))
            .phone("546" + RandomStringUtils.randomNumeric(7))
            .build();

        final ValidatableResponse response = given()
            .port(port)
            .contentType(ContentType.JSON)
            .body(userDto)
            .when()
            .post("/user/save")
            .then()
            .statusCode(HttpStatus.OK.value());

        final Long userId = response.extract().as(Long.class);

        UserEntity userEntity = userRepository.findById(userId)
            .orElseThrow(UserNotFoundException::new);

        assertEquals(userId, userEntity.getId());
        assertEquals(userDto.getFirstName(), userEntity.getFirstName());
        assertEquals(userDto.getLastName(), userEntity.getLastName());
        assertEquals(userDto.getPhone(), userEntity.getPhone());
    }

    @Test
    public void shouldDelete() {

        final UserEntity toDb = UserEntity.builder()
            .firstName(RandomStringUtils.randomAlphanumeric(3))
            .lastName(RandomStringUtils.randomAlphanumeric(3))
            .phone("0546" + RandomStringUtils.randomNumeric(7))
            .build();

        UserEntity user = userRepository.save(toDb);

        assertTrue(userRepository.findById(user.getId()).isPresent());

        given()
            .port(port)
            .contentType(ContentType.JSON)
            .when()
            .delete("/user/delete/" + user.getId())
            .then()
            .statusCode(HttpStatus.OK.value());

        assertFalse(userRepository.findById(user.getId()).isPresent());
    }

    @Test
    public void shouldGetUserDetail() {

        final UserEntity toDb = UserEntity.builder()
            .firstName(RandomStringUtils.randomAlphanumeric(3))
            .lastName(RandomStringUtils.randomAlphanumeric(3))
            .phone("0546" + RandomStringUtils.randomNumeric(7))
            .build();

        UserEntity user = userRepository.save(toDb);

        ValidatableResponse response = given()
            .port(port)
            .contentType(ContentType.JSON)
            .when()
            .get("/user/detail/" + user.getId())
            .then()
            .statusCode(HttpStatus.OK.value());

        final UserDto detail = response.extract().as(UserDto.class);

        assertEquals(user.getId(), detail.getId());
        assertEquals(user.getFirstName(), detail.getFirstName());
        assertEquals(user.getLastName(), detail.getLastName());
        assertEquals(user.getPhone(), detail.getPhone());
    }
}