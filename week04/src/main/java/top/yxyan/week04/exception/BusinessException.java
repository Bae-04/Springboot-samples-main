package top.yxyan.week04.exception;

import lombok.Getter;

/**
 * 业务异常
 *
 * @author yxyan
 * @date 2026/3/26
 * @description 业务异常
 * */
public class BusinessException extends RuntimeException{
    @Getter
    private Integer code;

    private final String message;
    public BusinessException(String message) {
        super(message);
        this.message = message;
    }
    public BusinessException(int code, String message) {
        this.code = code;
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
