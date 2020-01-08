package com.organizer.domain.common.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class PasswordEncryptorDelegate implements PasswordEncryptor {

    private PasswordEncoder passwordEncoder;

    public PasswordEncryptorDelegate (PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encrypt(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
