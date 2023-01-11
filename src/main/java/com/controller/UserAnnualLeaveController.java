package com.controller;

import com.dto.UserAnnualLeaveCreateDto;
import com.dto.UserAnnualLeaveDto;
import com.dto.UserAnnualLeaveUpdateDto;
import com.service.AnnualLeaveService;
import com.service.impl.UserAnnualLeaveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@Api(value = "User Annual Leave")
@RestController
@AllArgsConstructor
@RequestMapping("/annual-leave")
public class UserAnnualLeaveController {

    private final AnnualLeaveService annualLeaveService;
    private final UserAnnualLeaveService userAnnualLeaveService;

    @ApiOperation(value = "Create to User Annual Leave")
    @PostMapping("/create")
    public void create(@RequestBody @Valid UserAnnualLeaveCreateDto dto, Locale locale) {
        userAnnualLeaveService.create(dto, locale);
    }

    @ApiOperation(value = "Cancel to User Annual Leave")
    @PutMapping("/update")
    public Long update(@RequestBody @Valid UserAnnualLeaveUpdateDto dto) {
        return annualLeaveService.update(dto);
    }

    @ApiOperation(value = "List to User Annual Leave")
    @GetMapping("/list/{userId}")
    public List<UserAnnualLeaveDto> list(@PathVariable Long userId) {
        return annualLeaveService.list(userId);
    }
}
