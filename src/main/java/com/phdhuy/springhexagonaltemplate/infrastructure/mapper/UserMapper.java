package com.phdhuy.springhexagonaltemplate.infrastructure.mapper;

import com.phdhuy.springhexagonaltemplate.application.rest.response.user.UserInfoResponse;
import com.phdhuy.springhexagonaltemplate.domain.model.User;
import com.phdhuy.springhexagonaltemplate.infrastructure.databases.postgresql.entity.UserEntity;
import com.phdhuy.springhexagonaltemplate.shared.config.MapStructConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class)
public interface UserMapper {

  @Mapping(source = "user.isConfirmed", target = "isConfirmed")
  UserEntity fromUserDomain(User user);

  User toUserDomain(UserEntity userEntity);

  @Mapping(source = "user.email", target = "email")
  @Mapping(source = "user.id", target = "id")
  UserInfoResponse toUserInfoResponse(User user);
}
