package com.phdhuy.springhexagonaltemplate.infrastructure.security;

import com.phdhuy.springhexagonaltemplate.domain.ports.outbound.auth.PasswordEncodePort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncodeAdapter implements PasswordEncodePort {

  BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

  @Override
  public String passwordEncoder(String password) {
    return bCryptPasswordEncoder.encode(password);
  }
}
