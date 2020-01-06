package com.organizer.domain.common.security;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class PasswordEncryptorDelegator implements PasswordEncryptor {
    @Override
    public String encrypt(String rawPassword) {
        //TODO implementacja
        return rawPassword;
    }
}
