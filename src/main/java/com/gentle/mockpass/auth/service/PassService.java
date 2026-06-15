package com.gentle.mockpass.auth.service;

import com.gentle.mockpass.auth.dto.PassRequestDto;
import com.gentle.mockpass.auth.dto.PassResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

@Service
public class PassService {

    @Value("encryption.pass-salt")
    private String SYSTEM_SALT;

    public PassResponseDto authenticate(PassRequestDto dto) {
        String ci = generateCi(dto.getName(), dto.getPhone());

        return PassResponseDto.builder()
                .ci(ci)
                .name(dto.getName())
                .phone(dto.getPhone())
                .success(true)
                .build();
    }

    private String generateCi(String name, String phone) {
        try {
            String target = SYSTEM_SALT + name + "_" + phone + SYSTEM_SALT;

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(target.getBytes(StandardCharsets.UTF_8));

            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 알고리즘을 초기화할 수 없습니다.", e);
        }
    }

}
