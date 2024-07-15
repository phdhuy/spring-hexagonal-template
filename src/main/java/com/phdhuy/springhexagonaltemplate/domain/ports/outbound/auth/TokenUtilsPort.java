package com.phdhuy.springhexagonaltemplate.domain.ports.outbound.auth;

import com.phdhuy.springhexagonaltemplate.application.rest.response.auth.TokenResponse;
import com.phdhuy.springhexagonaltemplate.infrastructure.security.domain.UserPrincipal;

public interface TokenUtilsPort {

  TokenResponse createToken(UserPrincipal userPrincipal);

  TokenResponse refreshToken(String refreshToken);
}
