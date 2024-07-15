package com.phdhuy.springhexagonaltemplate.domain.services.auth;

import com.phdhuy.springhexagonaltemplate.application.rest.request.auth.RefreshTokenRequest;
import com.phdhuy.springhexagonaltemplate.application.rest.response.auth.TokenResponse;
import com.phdhuy.springhexagonaltemplate.domain.ports.inbound.auth.CreateTokenUseCase;
import com.phdhuy.springhexagonaltemplate.domain.ports.outbound.auth.TokenUtilsPort;
import com.phdhuy.springhexagonaltemplate.infrastructure.security.domain.UserPrincipal;
import com.phdhuy.springhexagonaltemplate.shared.annotation.UseCase;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CreateTokenService implements CreateTokenUseCase {

  private final TokenUtilsPort tokenUtilsPort;

  @Override
  public TokenResponse createToken(UserPrincipal userPrincipal) {
    return tokenUtilsPort.createToken(userPrincipal);
  }

  @Override
  public TokenResponse refreshToken(RefreshTokenRequest refreshToken) {
    return tokenUtilsPort.refreshToken(refreshToken.getRefreshToken());
  }
}
