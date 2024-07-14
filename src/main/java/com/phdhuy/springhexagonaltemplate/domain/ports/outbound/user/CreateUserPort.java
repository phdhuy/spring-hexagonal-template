package com.phdhuy.springhexagonaltemplate.domain.ports.outbound.user;

import com.phdhuy.springhexagonaltemplate.domain.model.User;

public interface CreateUserPort {
  User createUser(User user);
}
