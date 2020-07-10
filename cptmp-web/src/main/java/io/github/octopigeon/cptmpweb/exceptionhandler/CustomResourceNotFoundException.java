package io.github.octopigeon.cptmpweb.exceptionhandler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/10
 * @last-check-in anlow
 * @date 2020/7/10
 */
@RestControllerAdvice
public class CustomResourceNotFoundException {

    @ExceptionHandler(NoHandlerFoundException.class)
    public NoHandlerFoundException handleNoHandlerFoundException(NoHandlerFoundException e) {
        return e;
    }

}
