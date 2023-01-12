package com.controller;

import com.dto.UserAnnualLeaveCreateDto;
import com.dto.UserAnnualLeaveDto;
import com.dto.UserAnnualLeaveResponse;
import com.dto.UserAnnualLeaveUpdateDto;
import com.service.AnnualLeaveService;
import com.service.impl.UserAnnualLeaveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "User Annual Leave")
@RestController
@AllArgsConstructor
@RequestMapping("/annual-leave")
public class UserAnnualLeaveController {

    private final AnnualLeaveService annualLeaveService;
    private final UserAnnualLeaveService userAnnualLeaveService;

    @ApiOperation(value = "Create to User Annual Leave")
    @PostMapping("/create")
    public void create(@RequestBody @Valid UserAnnualLeaveCreateDto dto) {
        userAnnualLeaveService.create(dto);
    }

    @ApiOperation(value = "Cancel to User Annual Leave")
    @PutMapping("/update")
    public void update(@RequestBody @Valid UserAnnualLeaveUpdateDto dto) {
        annualLeaveService.update(dto);
    }

    @ApiOperation(value = "List to User Annual Leave")
    @GetMapping("/list/{userId}")
    public UserAnnualLeaveResponse list(@PathVariable Long userId) {
        return annualLeaveService.list(userId);
    }
}
