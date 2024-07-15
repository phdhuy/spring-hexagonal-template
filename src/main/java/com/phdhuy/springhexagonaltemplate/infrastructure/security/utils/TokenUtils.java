package com.phdhuy.springhexagonaltemplate.infrastructure.security.utils;

import com.phdhuy.springhexagonaltemplate.application.rest.response.auth.TokenResponse;
import com.phdhuy.springhexagonaltemplate.domain.ports.outbound.auth.TokenUtilsPort;
import com.phdhuy.springhexagonaltemplate.infrastructure.databases.postgresql.entity.UserEntity;
import com.phdhuy.springhexagonaltemplate.infrastructure.databases.postgresql.repository.UserRepository;
import com.phdhuy.springhexagonaltemplate.infrastructure.mapper.TokenMapper;
import com.phdhuy.springhexagonaltemplate.infrastructure.security.config.TokenProperties;
import com.phdhuy.springhexagonaltemplate.infrastructure.security.domain.UserPrincipal;
import com.phdhuy.springhexagonaltemplate.shared.constant.MessageConstant;
import com.phdhuy.springhexagonaltemplate.shared.exception.ForbiddenException;
import com.phdhuy.springhexagonaltemplate.shared.exception.NotFoundException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenUtils implements TokenUtilsPort {

  private final TokenProperties tokenProperties;

  private final UserRepository userRepository;

  private final TokenMapper tokenMapper;

  public TokenResponse createToken(UserPrincipal userPrincipal) {
    return tokenMapper.toOauthAccessTokenResponse(
        this.createAccessToken(userPrincipal.getId()),
        this.createRefreshToken(userPrincipal.getId()),
        tokenProperties.getTokenExpirationMsec() / 1000);
  }

  public TokenResponse refreshToken(String refreshToken) {
    this.validateRefreshToken(refreshToken, tokenProperties.getRefreshTokenSecret());

    return tokenMapper.toOauthAccessTokenResponse(
        this.createAccessToken(
            this.getUUIDFromToken(refreshToken, tokenProperties.getRefreshTokenSecret())),
        refreshToken,
        tokenProperties.getTokenExpirationMsec() / 1000);
  }

  public UserEntity getUserFromToken(String token) {
    this.validateAccessToken(token, tokenProperties.getTokenSecret());
    return userRepository
        .findById(this.getUUIDFromToken(token, tokenProperties.getTokenSecret()))
        .orElseThrow(() -> new NotFoundException(MessageConstant.USER_NOT_FOUND));
  }

  private UUID getUUIDFromToken(String token, String secret) {
    Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    return UUID.fromString(claims.getSubject());
  }

  private String createAccessToken(UUID userId) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + tokenProperties.getTokenExpirationMsec());

    return Jwts.builder()
        .setSubject(userId.toString())
        .setIssuedAt(new Date())
        .setExpiration(expiryDate)
        .signWith(SignatureAlgorithm.HS512, tokenProperties.getTokenSecret())
        .compact();
  }

  private String createRefreshToken(UUID userId) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + tokenProperties.getRefreshTokenExpirationMsec());

    return Jwts.builder()
        .setSubject(userId.toString())
        .setIssuedAt(new Date())
        .setExpiration(expiryDate)
        .signWith(SignatureAlgorithm.HS512, tokenProperties.getRefreshTokenSecret())
        .compact();
  }

  private void validateAccessToken(String authToken, String secret) {
    try {
      Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
    } catch (ExpiredJwtException ex) {
      throw new ForbiddenException(MessageConstant.EXPIRED_TOKEN);
    } catch (Exception ex) {
      log.info(ex.getMessage());
      throw new ForbiddenException(MessageConstant.INVALID_TOKEN);
    }
  }

  private void validateRefreshToken(String authToken, String secret) {
    try {
      Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
    } catch (ExpiredJwtException ex) {
      throw new ForbiddenException(MessageConstant.EXPIRED_REFRESH_TOKEN);
    } catch (Exception ex) {
      throw new ForbiddenException(MessageConstant.INVALID_REFRESH_TOKEN);
    }
  }
}
