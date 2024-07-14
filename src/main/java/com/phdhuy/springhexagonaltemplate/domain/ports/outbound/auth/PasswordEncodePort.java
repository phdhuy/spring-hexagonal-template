package com.phdhuy.springhexagonaltemplate.domain.ports.outbound.auth;

public interface PasswordEncodePort {
  String passwordEncoder(String password);
}
