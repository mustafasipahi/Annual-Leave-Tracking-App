package com.controller;

import com.Application;
import com.dto.UserAnnualLeaveCreateDto;
import com.dto.UserAnnualLeaveDto;
import com.dto.UserAnnualLeaveResponse;
import com.dto.UserAnnualLeaveUpdateDto;
import com.entity.AnnualLeaveEntity;
import com.entity.UserEntity;
import com.enums.AnnualLeaveStatus;
import com.exception.AnnualLeaveException;
import com.repository.AnnualLeaveRepository;
import com.repository.UserRepository;
import com.util.DateUtil;
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

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
public class UserAnnualLeaveControllerTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AnnualLeaveRepository annualLeaveRepository;

    @LocalServerPort
    private int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @After
    public void destroy() {
        annualLeaveRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void shouldCreate() {

        final UserEntity toDbUser = UserEntity.builder()
            .firstName(RandomStringUtils.randomAlphanumeric(3))
            .lastName(RandomStringUtils.randomAlphanumeric(3))
            .phone("546" + RandomStringUtils.randomNumeric(7))
            .createdDate(DateUtil.sub(DateUtil.nowAsDate(), Duration.ofDays(5)))
            .build();

        final UserEntity user = userRepository.save(toDbUser);

        assertTrue(annualLeaveRepository.findAllByUserId(user.getId()).isEmpty());

        final UserAnnualLeaveCreateDto dto = UserAnnualLeaveCreateDto.builder()
            .userId(user.getId())
            .startDate(LocalDate.of(2023, 1, 13))
            .endDate(LocalDate.of(2023, 1, 17))
            .build();

        given()
            .port(port)
            .contentType(ContentType.JSON)
            .body(dto)
            .when()
            .post("/annual-leave/create")
            .then()
            .statusCode(HttpStatus.OK.value());

        List<AnnualLeaveEntity> annualLeaveList = annualLeaveRepository.findAllByUserId(user.getId());
        assertFalse(annualLeaveList.isEmpty());
        assertEquals(1, annualLeaveList.size());
        assertEquals(dto.getStartDate(), annualLeaveList.get(0).getStartDate());
        assertEquals(dto.getEndDate(), annualLeaveList.get(0).getEndDate());
        assertEquals(3, annualLeaveList.get(0).getCount());
        assertEquals(AnnualLeaveStatus.WAITING_APPROVE, annualLeaveList.get(0).getStatus());
    }

    @Test
    public void shouldUpdate() {

        final UserEntity toDbUser = UserEntity.builder()
            .firstName(RandomStringUtils.randomAlphanumeric(3))
            .lastName(RandomStringUtils.randomAlphanumeric(3))
            .phone("546" + RandomStringUtils.randomNumeric(7))
            .createdDate(DateUtil.sub(DateUtil.nowAsDate(), Duration.ofDays(5)))
            .build();

        final UserEntity user = userRepository.save(toDbUser);

        final AnnualLeaveEntity toDbAnnualLeave = AnnualLeaveEntity.builder()
            .user(user)
            .startDate(LocalDate.of(2023, 1, 13))
            .endDate(LocalDate.of(2023, 1, 17))
            .count(3)
            .status(AnnualLeaveStatus.WAITING_APPROVE)
            .createdDate(DateUtil.sub(DateUtil.nowAsDate(), Duration.ofDays(1)))
            .build();

        final AnnualLeaveEntity annualLeave = annualLeaveRepository.save(toDbAnnualLeave);

        assertEquals(AnnualLeaveStatus.WAITING_APPROVE, annualLeave.getStatus());

        final UserAnnualLeaveUpdateDto dto = UserAnnualLeaveUpdateDto.builder()
            .annualLeaveId(annualLeave.getId())
            .status(AnnualLeaveStatus.APPROVED)
            .build();

        given()
            .port(port)
            .contentType(ContentType.JSON)
            .body(dto)
            .when()
            .put("/annual-leave/update")
            .then()
            .statusCode(HttpStatus.OK.value());

        final AnnualLeaveEntity fromDb = annualLeaveRepository.findById(annualLeave.getId())
            .orElseThrow(AnnualLeaveException::new);

        assertEquals(dto.getStatus(), fromDb.getStatus());
    }

    @Test
    public void shouldList() {

        final UserEntity toDbUser = UserEntity.builder()
            .firstName(RandomStringUtils.randomAlphanumeric(3))
            .lastName(RandomStringUtils.randomAlphanumeric(3))
            .phone("546" + RandomStringUtils.randomNumeric(7))
            .createdDate(DateUtil.sub(DateUtil.nowAsDate(), Duration.ofDays(370)))
            .build();

        final UserEntity user = userRepository.save(toDbUser);

        final UserAnnualLeaveCreateDto dto = UserAnnualLeaveCreateDto.builder()
            .userId(user.getId())
            .build();

        dto.setStartDate(LocalDate.of(2023, 2, 2));
        dto.setEndDate(LocalDate.of(2023, 2, 5));

        given()
            .port(port)
            .contentType(ContentType.JSON)
            .body(dto)
            .when()
            .post("/annual-leave/create")
            .then()
            .statusCode(HttpStatus.OK.value());

        dto.setStartDate(LocalDate.of(2023, 6, 8));
        dto.setEndDate(LocalDate.of(2023, 6, 12));

        given()
            .port(port)
            .contentType(ContentType.JSON)
            .body(dto)
            .when()
            .post("/annual-leave/create")
            .then()
            .statusCode(HttpStatus.OK.value());

        ValidatableResponse response = given()
            .port(port)
            .contentType(ContentType.JSON)
            .when()
            .get("/annual-leave/list/" + user.getId())
            .then()
            .statusCode(HttpStatus.OK.value());

        final UserAnnualLeaveResponse annualLeaveResponse = response.extract().as(UserAnnualLeaveResponse.class);
        final List<UserAnnualLeaveDto> annualLeaveList = annualLeaveResponse.getAnnualLeaveList();

        assertEquals(2, annualLeaveList.size());

        assertEquals(2, annualLeaveList.get(0).getCount());
        assertEquals(AnnualLeaveStatus.WAITING_APPROVE, annualLeaveList.get(0).getStatus());

        assertEquals(3, annualLeaveList.get(1).getCount());
        assertEquals(AnnualLeaveStatus.WAITING_APPROVE, annualLeaveList.get(1).getStatus());
    }
}