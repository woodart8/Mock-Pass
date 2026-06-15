package com.gentle.mockpass.auth.service;

import com.gentle.mockpass.auth.dto.PassRequestDto;
import com.gentle.mockpass.auth.dto.PassResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Objects;

@Service
public class PassService {

    @Value("encryption.pass-salt")
    private String SYSTEM_SALT;

    public PassResponseDto authenticate(PassRequestDto dto) {
        final String name = dto.getName();
        final String phone = parsePhone(dto.getPhone());
        final String ci = generateCi(name, phone);

        return PassResponseDto.builder()
                .ci(ci)
                .name(name)
                .phone(phone)
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

    private String parsePhone(String phone) {
        if (Objects.isNull(phone)) {
            throw new IllegalArgumentException("전화번호는 필수 입력 항목입니다.");
        }

        String onlyDigits = phone.replaceAll("[^0-9]", "");

        if (!onlyDigits.matches("^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$")) {
            throw new IllegalArgumentException("유효하지 않은 전화번호 형식입니다.");
        }

        return onlyDigits;
    }

}
