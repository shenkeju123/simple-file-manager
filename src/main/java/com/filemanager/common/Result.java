package com.filemanager.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一响应结果封装
 */
@Data
@ApiModel(value = "统一响应结果", description = "API接口统一响应结果")
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    @ApiModelProperty(value = "状态码", example = "200")
    private Integer code;

    /**
     * 返回消息
     */
    @ApiModelProperty(value = "返回消息", example = "操作成功")
    private String message;

    /**
     * 返回数据
     */
    @ApiModelProperty(value = "返回数据")
    private T data;

    /**
     * 是否成功
     */
    @ApiModelProperty(value = "是否成功", example = "true")
    private Boolean success;

    /**
     * 时间戳
     */
    @ApiModelProperty(value = "时间戳", example = "1617955200000")
    private Long timestamp = System.currentTimeMillis();

    /**
     * 私有构造函数
     *
     * @param code    状态码
     * @param message 消息
     * @param data    数据
     */
    private Result(Integer code, String message, T data, Boolean success) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.success = success;
    }

    /**
     * 成功结果
     *
     * @return 成功的响应结果
     */
    public static <T> Result<T> success() {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null, true);
    }

    /**
     * 成功结果
     *
     * @param data 返回数据
     * @return 成功的响应结果
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data, true);
    }

    /**
     * 成功结果
     *
     * @param message 消息
     * @param data    返回数据
     * @return 成功的响应结果
     */
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(ResultCode.SUCCESS.getCode(), message, data, true);
    }

    /**
     * 失败结果
     *
     * @return 失败的响应结果
     */
    public static <T> Result<T> error() {
        return new Result<>(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMessage(), null, false);
    }

    /**
     * 失败结果
     *
     * @param message 消息
     * @return 失败的响应结果
     */
    public static <T> Result<T> error(String message) {
        return new Result<>(ResultCode.ERROR.getCode(), message, null, false);
    }

    /**
     * 失败结果
     *
     * @param code    状态码
     * @param message 消息
     * @return 失败的响应结果
     */
    public static <T> Result<T> error(Integer code, String message) {
        return new Result<>(code, message, null, false);
    }

    /**
     * 失败结果
     *
     * @param resultCode 结果码枚举
     * @return 失败的响应结果
     */
    public static <T> Result<T> error(ResultCode resultCode) {
        return new Result<>(resultCode.getCode(), resultCode.getMessage(), null, false);
    }

    /**
     * 判断是否成功
     *
     * @return 是否成功
     */
    public boolean isSuccess() {
        return ResultCode.SUCCESS.getCode().equals(code);
    }
}