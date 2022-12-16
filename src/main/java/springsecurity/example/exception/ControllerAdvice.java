package springsecurity.example.exception;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ControllerAdvice {

    private final MessageSource messageSource;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalArgumentHandler(IllegalArgumentException e) {
        log.error("[IllegalArgumentException] ex", e);
        return new ErrorResult("400", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalStateException.class)
    public ErrorResult illegalStateHandler(IllegalStateException e) {
        log.error("[IllegalStateException] ex", e);
        return new ErrorResult("400", e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(TokenExpiredException.class)
    public ErrorResult tokenExpiredException(TokenExpiredException e) {
        log.error("[TokenExpiredException] ex", e);
        return new ErrorResult("401", messageSource.getMessage("user.auth.exception.tokenExpired",null,null));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(JWTDecodeException.class)
    public ErrorResult jWTDecodeException(JWTDecodeException e) {
        log.error("[jWTDecodeException] ex", e);
        return new ErrorResult("400", messageSource.getMessage("user.auth.exception.tokenJWTDecode",null,null));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NumberFormatException.class)
    public ErrorResult numberFormatException(NumberFormatException e) {
        log.error("[NumberFormatException] ex", e);
        return new ErrorResult("400", messageSource.getMessage(e.getMessage(),null,null));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e) {
        log.error("[exceptionHandler] ex", e.getMessage());
        return new ErrorResult("500", e.getMessage());
    }
}
