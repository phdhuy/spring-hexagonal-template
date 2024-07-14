package com.phdhuy.springhexagonaltemplate.domain.ports.outbound.user;

public interface ExistsUserPort {

  boolean existsByEmail(String email);
}
