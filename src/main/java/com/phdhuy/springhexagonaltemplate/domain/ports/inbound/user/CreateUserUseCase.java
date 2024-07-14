package com.phdhuy.springhexagonaltemplate.domain.ports.inbound.user;

import com.phdhuy.springhexagonaltemplate.application.rest.request.auth.SignUpRequest;
import com.phdhuy.springhexagonaltemplate.application.rest.response.user.UserInfoResponse;

public interface CreateUserUseCase {

  UserInfoResponse createUser(SignUpRequest signUpRequest);
}
