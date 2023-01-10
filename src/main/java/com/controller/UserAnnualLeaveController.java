package com.controller;

import com.dto.UserAnnualLeaveCreateDto;
import com.dto.UserAnnualLeaveDto;
import com.dto.UserAnnualLeaveUpdateDto;
import com.service.UserAnnualLeaveService;
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

    private final UserAnnualLeaveService userAnnualLeaveService;

    @ApiOperation(value = "Create to User Annual Leave")
    @PostMapping("/create")
    public Long create(@RequestBody @Valid UserAnnualLeaveCreateDto dto) {
        return userAnnualLeaveService.create(dto);
    }

    @ApiOperation(value = "Cancel to User Annual Leave")
    @PutMapping("/update")
    public Long update(@RequestBody @Valid UserAnnualLeaveUpdateDto dto) {
        return userAnnualLeaveService.update(dto);
    }

    @ApiOperation(value = "List to User Annual Leave")
    @GetMapping("/list/{userId}")
    public List<UserAnnualLeaveDto> list(@PathVariable Long userId) {
        return userAnnualLeaveService.list(userId);
    }
}
