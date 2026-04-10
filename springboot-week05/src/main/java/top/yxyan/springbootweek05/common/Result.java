package top.yxyan.springbootweek05.common;

import lombok.Data;

    @Data
    public class Result<T> {
        private Integer code;    // 状态码：200成功，其他失败
        private String message;  // 提示信息
        private T data;          // 返回的数据

        // 私有构造方法，禁止外部直接创建
        private Result() {
        }

        // 成功响应（无数据）
        public static <T> Result<T> success() {
            Result<T> result = new Result<>();
            result.setCode(200);
            result.setMessage("操作成功");
            return result;
        }

        // 成功响应（带数据）
        public static <T> Result<T> success(T data) {
            Result<T> result = new Result<>();
            result.setCode(200);
            result.setMessage("操作成功");
            result.setData(data);
            return result;
        }

        // 成功响应（自定义消息）
        public static <T> Result<T> success(String message, T data) {
            Result<T> result = new Result<>();
            result.setCode(200);
            result.setMessage(message);
            result.setData(data);
            return result;
        }

        // 失败响应（默认消息）
        public static <T> Result<T> error() {
            Result<T> result = new Result<>();
            result.setCode(500);
            result.setMessage("操作失败");
            return result;
        }

        // 失败响应（自定义消息）
        public static <T> Result<T> error(String message) {
            Result<T> result = new Result<>();
            result.setCode(500);
            result.setMessage(message);
            return result;
        }

        // 失败响应（自定义状态码和消息）
        public static <T> Result<T> error(Integer code, String message) {
            Result<T> result = new Result<>();
            result.setCode(code);
            result.setMessage(message);
            return result;
        }
    }

