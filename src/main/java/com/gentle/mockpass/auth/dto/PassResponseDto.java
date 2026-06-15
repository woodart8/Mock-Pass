package com.gentle.mockpass.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PassResponseDto {

    private String ci;
    private String name;
    private String phone;
    private Boolean success;

}
