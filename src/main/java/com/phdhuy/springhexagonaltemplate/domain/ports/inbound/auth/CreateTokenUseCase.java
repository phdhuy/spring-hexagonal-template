package com.phdhuy.springhexagonaltemplate.domain.ports.inbound.auth;

import com.phdhuy.springhexagonaltemplate.application.rest.request.auth.RefreshTokenRequest;
import com.phdhuy.springhexagonaltemplate.application.rest.response.auth.TokenResponse;
import com.phdhuy.springhexagonaltemplate.infrastructure.security.domain.UserPrincipal;

public interface CreateTokenUseCase {

  TokenResponse createToken(UserPrincipal userPrincipal);

  TokenResponse refreshToken(RefreshTokenRequest refreshToken);
}
