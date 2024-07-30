package fr.recia.mediacentre.api.web.rest.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class MediacentreWSException extends Throwable{

  @Getter
  private HttpStatus statusCode;

  public MediacentreWSException(HttpStatus statusCode) {
    this.statusCode = statusCode;
  }

  public MediacentreWSException(String message, HttpStatus statusCode) {
    super(message);
    this.statusCode = statusCode;
  }
}
