package com.phdhuy.springhexagonaltemplate.infrastructure.databases.postgresql.adapter;

import com.phdhuy.springhexagonaltemplate.domain.model.User;
import com.phdhuy.springhexagonaltemplate.domain.ports.outbound.user.CreateUserPort;
import com.phdhuy.springhexagonaltemplate.infrastructure.databases.postgresql.repository.UserRepository;
import com.phdhuy.springhexagonaltemplate.infrastructure.mapper.UserMapper;
import com.phdhuy.springhexagonaltemplate.shared.annotation.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class CreateUserAdapter implements CreateUserPort {

  private final UserRepository userRepository;

  private final UserMapper userMapper;

  @Override
  public User createUser(User user) {
    return userMapper.toUserDomain(userRepository.save(userMapper.fromUserDomain(user)));
  }
}
