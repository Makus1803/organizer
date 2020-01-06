package com.organizer.domain.common.security;

public interface PasswordEncryptor {
    String encrypt(String rawPassword);
}
