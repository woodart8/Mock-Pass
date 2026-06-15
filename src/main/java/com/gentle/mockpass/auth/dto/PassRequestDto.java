package com.gentle.mockpass.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PassRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    private String phone;

}
