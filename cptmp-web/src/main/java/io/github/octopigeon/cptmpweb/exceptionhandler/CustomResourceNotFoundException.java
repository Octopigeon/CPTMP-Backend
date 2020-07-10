package io.github.octopigeon.cptmpweb.exceptionhandler;

import io.github.octopigeon.cptmpweb.bean.response.RespBean;
import org.springframework.http.HttpStatus;
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

    //TODO 未测试
    @ExceptionHandler(NoHandlerFoundException.class)
    public RespBean handleNoHandlerFoundException(NoHandlerFoundException e) {
        return RespBean.error(HttpStatus.NOT_FOUND.value(), "not found");
    }

}
