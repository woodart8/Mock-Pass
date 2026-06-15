package com.gentle.mockpass.auth.controller;

import com.gentle.mockpass.auth.dto.PassRequestDto;
import com.gentle.mockpass.auth.dto.PassResponseDto;
import com.gentle.mockpass.auth.service.PassService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pass")
public class PassController {

    @Autowired
    public PassService passService;

    @PostMapping("/auth")
    public PassResponseDto auth(@RequestBody @Valid PassRequestDto dto) {
        return passService.authenticate(dto);
    }

}
