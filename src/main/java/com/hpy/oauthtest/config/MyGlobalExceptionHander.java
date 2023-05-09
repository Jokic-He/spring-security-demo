package com.hpy.oauthtest.config;


import com.hpy.oauthtest.domain.AjaxResult;
import com.hpy.oauthtest.exception.NoTokenException;
import com.hpy.oauthtest.exception.TokenVerifyErrorException;
import com.hpy.oauthtest.exception.UnAuthorizationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;


/**
 * 自定义异常拦截类
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class MyGlobalExceptionHander {



    @ExceptionHandler(UnAuthorizationException.class)
    public AjaxResult UnAuthorizationExceptionHandle(){
        log.info("无权访问接口");
        return AjaxResult.error("您无权访问该接口");
//        return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("您无权访问该接口"));
    }


    @ExceptionHandler(NoTokenException.class)
    public AjaxResult NoTokenExceptionHandle(){
        log.info("拦截到未携带凭证的请求");
        return AjaxResult.error("请携带凭证");
//        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("请携带凭证"));
    }


    @ExceptionHandler(TokenVerifyErrorException.class)
    public AjaxResult TokenVerifyErrorExceptionHandle(){
        log.info("凭证校验失败");
        return AjaxResult.error("凭证校验失败");
//        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("凭证校验失败"));
    }
}
