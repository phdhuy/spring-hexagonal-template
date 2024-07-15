package com.phdhuy.springhexagonaltemplate.domain.services.user;

import com.phdhuy.springhexagonaltemplate.application.rest.request.auth.SignUpRequest;
import com.phdhuy.springhexagonaltemplate.application.rest.response.user.UserInfoResponse;
import com.phdhuy.springhexagonaltemplate.domain.model.User;
import com.phdhuy.springhexagonaltemplate.domain.ports.inbound.user.CreateUserUseCase;
import com.phdhuy.springhexagonaltemplate.domain.ports.outbound.auth.PasswordEncodePort;
import com.phdhuy.springhexagonaltemplate.domain.ports.outbound.user.CreateUserPort;
import com.phdhuy.springhexagonaltemplate.domain.ports.outbound.user.ExistsUserPort;
import com.phdhuy.springhexagonaltemplate.infrastructure.mapper.UserMapper;
import com.phdhuy.springhexagonaltemplate.shared.annotation.UseCase;
import com.phdhuy.springhexagonaltemplate.shared.constant.MessageConstant;
import com.phdhuy.springhexagonaltemplate.shared.enums.Role;
import com.phdhuy.springhexagonaltemplate.shared.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class CreateUserService implements CreateUserUseCase {

  private final CreateUserPort createUserPort;

  private final ExistsUserPort existsUserPort;

  private final PasswordEncodePort passwordEncodePort;

  private final UserMapper userMapper;

  @Override
  @Transactional
  public UserInfoResponse createUser(SignUpRequest signUpRequest) {
    User user = new User();

    if (existsUserPort.existsByEmail(signUpRequest.getEmail())) {
      throw new BadRequestException(MessageConstant.EMAIL_ALREADY_EXISTS);
    }

    if (!this.isPasswordMatch(signUpRequest.getPassword(), signUpRequest.getConfirmPassword())) {
      throw new BadRequestException(MessageConstant.PASSWORD_NOT_MATCHED_WITH_CONFIRM_PASSWORD);
    }

    user.setEmail(signUpRequest.getEmail());
    user.setIsConfirmed(true);
    user.setRole(Role.ROLE_USER);
    user.setPassword(passwordEncodePort.passwordEncoder(signUpRequest.getPassword()));

    return userMapper.toUserInfoResponse(createUserPort.createUser(user));
  }

  private boolean isPasswordMatch(String password, String confirmPassword) {
    return password.equals(confirmPassword);
  }
}
