package com.phdhuy.springhexagonaltemplate.shared.payload.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {

  private String code;

  private String message;
}
