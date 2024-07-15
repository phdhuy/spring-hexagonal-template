package com.phdhuy.springhexagonaltemplate.domain.model;

import com.phdhuy.springhexagonaltemplate.shared.enums.Role;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
public class User {

  private UUID id;

  private String email;

  private String password;

  private Role role;

  private Boolean isConfirmed;

  private Timestamp createdAt;

  private Timestamp updatedAt;

  private Timestamp deletedAt;

  private Timestamp confirmedAt;
}
