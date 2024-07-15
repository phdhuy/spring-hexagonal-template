package com.phdhuy.springhexagonaltemplate.application.rest.controller.auth;

import com.phdhuy.springhexagonaltemplate.application.rest.request.auth.RefreshTokenRequest;
import com.phdhuy.springhexagonaltemplate.application.rest.request.auth.SignInRequest;
import com.phdhuy.springhexagonaltemplate.application.rest.request.auth.SignUpRequest;
import com.phdhuy.springhexagonaltemplate.domain.ports.inbound.auth.CreateTokenUseCase;
import com.phdhuy.springhexagonaltemplate.domain.ports.inbound.user.CreateUserUseCase;
import com.phdhuy.springhexagonaltemplate.infrastructure.security.domain.UserPrincipal;
import com.phdhuy.springhexagonaltemplate.shared.constant.MessageConstant;
import com.phdhuy.springhexagonaltemplate.shared.exception.BadRequestException;
import com.phdhuy.springhexagonaltemplate.shared.exception.UnauthorizedException;
import com.phdhuy.springhexagonaltemplate.shared.payload.general.ResponseDataAPI;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth APIs")
public class AuthController {

  private final CreateUserUseCase createUserUseCase;

  private final CreateTokenUseCase createTokenUseCase;

  private final AuthenticationManager authenticationManager;

  @PostMapping("/sign-up")
  public ResponseEntity<ResponseDataAPI> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
    return ResponseEntity.ok(
        ResponseDataAPI.successWithoutMeta(createUserUseCase.createUser(signUpRequest)));
  }

  @PostMapping("/sign-in")
  public ResponseEntity<ResponseDataAPI> signIn(@Valid @RequestBody SignInRequest signInRequest) {
    try {
      Authentication authentication =
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                  signInRequest.getEmail().toLowerCase(), signInRequest.getPassword()));
      SecurityContextHolder.getContext().setAuthentication(authentication);
      UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

      return ResponseEntity.ok(
          ResponseDataAPI.successWithoutMeta(createTokenUseCase.createToken(userPrincipal)));
    } catch (BadCredentialsException e) {
      throw new BadRequestException(MessageConstant.INCORRECT_EMAIL_OR_PASSWORD);
    } catch (InternalAuthenticationServiceException e) {
      throw new UnauthorizedException(e.getMessage());
    } catch (DisabledException e) {
      throw new UnauthorizedException(MessageConstant.EMAIL_IS_NOT_VERIFIED);
    } catch (AuthenticationException e) {
      throw new UnauthorizedException(MessageConstant.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<ResponseDataAPI> refreshToken(
      @Valid @RequestBody RefreshTokenRequest refreshToken) {
    return ResponseEntity.ok(
        ResponseDataAPI.successWithoutMeta(createTokenUseCase.refreshToken(refreshToken)));
  }
}
