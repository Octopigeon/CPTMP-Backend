package io.github.octopigeon.cptmpweb.exceptionhandler;

import io.github.octopigeon.cptmpweb.bean.response.RespBean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * @author 魏啸冲
 * @version 1.0
 * @date 2020/7/10
 * 自定义404异常处理
 * @last-check-in 魏啸冲
 * @date 2020/7/10
 */
@RestControllerAdvice
public class CustomResourceNotFoundException {

    //TODO 与前端配合测试

    @ExceptionHandler(NoHandlerFoundException.class)
    public RespBean handleNoHandlerFoundException(NoHandlerFoundException e) {
        return RespBean.error(HttpStatus.NOT_FOUND.value(), "not found");
    }

}
