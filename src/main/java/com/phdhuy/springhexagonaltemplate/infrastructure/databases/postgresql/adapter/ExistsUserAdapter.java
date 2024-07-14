package com.phdhuy.springhexagonaltemplate.infrastructure.databases.postgresql.adapter;

import com.phdhuy.springhexagonaltemplate.domain.ports.outbound.user.ExistsUserPort;
import com.phdhuy.springhexagonaltemplate.infrastructure.databases.postgresql.repository.UserRepository;
import com.phdhuy.springhexagonaltemplate.shared.annotation.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class ExistsUserAdapter implements ExistsUserPort {

  private final UserRepository userRepository;

  @Override
  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }
}
