package com.phdhuy.springhexagonaltemplate.application.rest.controller.auth;

import com.phdhuy.springhexagonaltemplate.application.rest.request.auth.SignUpRequest;
import com.phdhuy.springhexagonaltemplate.domain.ports.inbound.user.CreateUserUseCase;
import com.phdhuy.springhexagonaltemplate.shared.payload.general.ResponseDataAPI;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final CreateUserUseCase createUserUseCase;

  @PostMapping("/sign-up")
  public ResponseEntity<ResponseDataAPI> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
    return ResponseEntity.ok(
        ResponseDataAPI.successWithoutMeta(createUserUseCase.createUser(signUpRequest)));
  }
}
