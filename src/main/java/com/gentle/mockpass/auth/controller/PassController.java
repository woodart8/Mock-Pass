package com.gentle.mockpass.auth.controller;

import com.gentle.mockpass.auth.dto.PassRequestDto;
import com.gentle.mockpass.auth.dto.PassResponseDto;
import com.gentle.mockpass.auth.service.PassService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pass")
@AllArgsConstructor
public class PassController {

    private final PassService passService;

    @PostMapping("/auth")
    public ResponseEntity<PassResponseDto> auth(@RequestBody @Valid PassRequestDto dto) {
        try {
            PassResponseDto responseDto = passService.authenticate(dto);
            return ResponseEntity.ok(responseDto);
        } catch (IllegalArgumentException e) {
            PassResponseDto errorDto = PassResponseDto.builder()
                    .success(false)
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
        }
    }

}
