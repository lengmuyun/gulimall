package com.atguigu.gulimall.product.exception;

import com.atguigu.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice(basePackages = { "com.atguigu.gulimall.product.controller" })
public class GulimallExceptionControllerAdvice {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleValidException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        Map<String, String> errorMsg = result.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        return R.error(400, "参数不合法").put("data", errorMsg);
    }

    @ExceptionHandler(value = Exception.class)
    public R handleUnknowException(Exception e) {
        log.info("程序发生错误: {}", e.getMessage());
        e.printStackTrace();
        return R.error();
    }

}
