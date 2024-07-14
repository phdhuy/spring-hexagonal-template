package com.phdhuy.springhexagonaltemplate.shared.exception;

import com.phdhuy.springhexagonaltemplate.shared.common.CommonFunction;
import com.phdhuy.springhexagonaltemplate.shared.payload.error.ErrorResponse;
import com.phdhuy.springhexagonaltemplate.shared.payload.general.ResponseDataAPI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ResponseDataAPI> notFoundException(NotFoundException ex) {
    ErrorResponse errorResponse = CommonFunction.getExceptionError(ex.getMessage());
    ResponseDataAPI responseDataAPI = ResponseDataAPI.error(errorResponse);
    return new ResponseEntity<>(responseDataAPI, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ResponseDataAPI> badRequestException(BadRequestException ex) {
    ErrorResponse errorResponse = CommonFunction.getExceptionError(ex.getMessage());
    ResponseDataAPI responseDataAPI = ResponseDataAPI.error(errorResponse);
    return new ResponseEntity<>(responseDataAPI, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ForbiddenException.class)
  public ResponseEntity<ResponseDataAPI> forbiddenException(ForbiddenException ex) {
    ErrorResponse errorResponse = CommonFunction.getExceptionError(ex.getMessage());
    ResponseDataAPI responseDataAPI = ResponseDataAPI.error(errorResponse);
    return new ResponseEntity<>(responseDataAPI, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(InternalServerException.class)
  public ResponseEntity<ResponseDataAPI> internalServerException(InternalServerException ex) {
    ErrorResponse errorResponse = CommonFunction.getExceptionError(ex.getMessage());
    ResponseDataAPI responseDataAPI = ResponseDataAPI.error(errorResponse);
    return new ResponseEntity<>(responseDataAPI, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<ResponseDataAPI> unauthorizedException(UnauthorizedException ex) {
    ErrorResponse errorResponse = CommonFunction.getExceptionError(ex.getMessage());
    ResponseDataAPI responseDataAPI = ResponseDataAPI.error(errorResponse);
    return new ResponseEntity<>(responseDataAPI, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
    List<ObjectError> objectErrors = ex.getBindingResult().getAllErrors();
    ObjectError objectError = objectErrors.get(objectErrors.size() - 1);
    String error = CommonFunction.convertToSnakeCase(Objects.requireNonNull(objectError.getCode()));
    String fieldName = CommonFunction.convertToSnakeCase(((FieldError) objectError).getField());
    String resource = CommonFunction.convertToSnakeCase(objectError.getObjectName());

    ErrorResponse errorResponse = CommonFunction.getValidationError(resource, fieldName, error);
    ResponseDataAPI responseDataAPI = ResponseDataAPI.error(errorResponse);

    return new ResponseEntity<>(responseDataAPI, HttpStatus.BAD_REQUEST);
  }
}
