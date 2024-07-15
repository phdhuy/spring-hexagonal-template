package com.phdhuy.springhexagonaltemplate.infrastructure.security.adapters;

import com.phdhuy.springhexagonaltemplate.domain.ports.outbound.auth.PasswordEncodePort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordEncodeAdapter implements PasswordEncodePort {

  private final PasswordEncoder passwordEncoder;

  @Override
  public String passwordEncoder(String password) {
    return passwordEncoder.encode(password);
  }
}
