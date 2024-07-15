package com.phdhuy.springhexagonaltemplate.infrastructure.security.service;

import com.phdhuy.springhexagonaltemplate.infrastructure.databases.postgresql.entity.UserEntity;
import com.phdhuy.springhexagonaltemplate.infrastructure.databases.postgresql.repository.UserRepository;
import com.phdhuy.springhexagonaltemplate.infrastructure.security.domain.UserPrincipal;
import com.phdhuy.springhexagonaltemplate.shared.constant.MessageConstant;
import com.phdhuy.springhexagonaltemplate.shared.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String email) {
    UserEntity user =
        userRepository
            .findByEmail(email)
            .orElseThrow(() -> new NotFoundException(MessageConstant.USER_NOT_FOUND));
    return UserPrincipal.create(user);
  }
}
