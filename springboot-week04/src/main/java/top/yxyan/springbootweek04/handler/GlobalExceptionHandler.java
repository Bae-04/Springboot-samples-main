package top.yxyan.springbootweek04.handler;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.yxyan.springbootweek04.common.Result;
import top.yxyan.springbootweek04.exception.BusinessException;


import java.util.StringJoiner;

@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 统一处理请求参数校验异常
     *
     * @param e 参数校验异常
     * @return 错误信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handlerValidationException(MethodArgumentNotValidException e) {
        StringJoiner sj = new StringJoiner(";");
        for(FieldError fieldError : e.getBindingResult().getFieldErrors()){
            sj.add(fieldError.getDefaultMessage());
        }
        return Result.error(400, sj.toString());
    }

    /**
     * 统一处理业务异常
     *
     * @param e 业务异常
     * @return 错误信息
     */
    @ExceptionHandler(BusinessException.class)
    public Result<?> handlerBusinessException(BusinessException e) {
        return Result.error(e.getCode(), e.getMessage());
    }

    /**
     * 统一处理其他异常
     *
     * @param e 其他异常
     * @return 错误信息
     */
    @ExceptionHandler(Exception.class)
    public Result<?> handlerException(Exception e) {
        return Result.error(500, "服务器异常");
    }
}
