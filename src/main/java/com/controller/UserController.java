package com.controller;

import com.dto.UserDto;
import com.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "User")
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "Create to User Entity")
    @PostMapping("/save")
    public Long save(@RequestBody @Valid UserDto userDto) {
        return userService.save(userDto);
    }

    @ApiOperation(value = "Delete to User Entity")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @ApiOperation(value = "Detail to User Entity")
    @GetMapping("/detail/{id}")
    public UserDto detail(@PathVariable Long id) {
        return userService.info(id);
    }
}
