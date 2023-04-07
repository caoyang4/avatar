package com.sankuai.avatar.workflow.server.handler;

import com.dianping.cat.Cat;
import com.sankuai.avatar.common.constant.ApiStatus;
import com.sankuai.avatar.common.exception.AccessDeniedException;
import com.sankuai.avatar.common.exception.BadRequestException;
import com.sankuai.avatar.common.exception.SupportErrorException;
import com.sankuai.avatar.common.vo.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局接口错误捕获统一处理
 *
 * @author zhaozhifan02
 */
@Slf4j
@RestController
@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = SupportErrorException.class)
    @ResponseBody
    public ApiResponse supportErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        Cat.logError(e);
        log.error("Support error: ", e);
        return ApiResponse.of(ApiStatus.SUPPORT_ERROR.getCode(),
                String.format("%s: %s", ApiStatus.SUPPORT_ERROR.getMessage(), e.getMessage()),
                null);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseBody
    public ApiResponse accessDeniedHandler(HttpServletRequest req, Exception e) throws Exception {
        Cat.logError(e);
        log.error("Access denied error: ", e);
        return ApiResponse.of(ApiStatus.SUPPORT_ERROR.getCode(),
                String.format("%s: %s", ApiStatus.SUPPORT_ERROR.getMessage(), e.getMessage()),
                null);
    }

    @ExceptionHandler(value = DuplicateKeyException.class)
    @ResponseBody
    public ApiResponse duplicateKeyHandler(HttpServletRequest req, Exception e) throws Exception {
        Cat.logError(e);
        log.error("Duplicate key error: ", e);

        return ApiResponse.of(ApiStatus.DUPLICATE_KEY_ERROR.getCode(),
                String.format("%s: %s", ApiStatus.DUPLICATE_KEY_ERROR.getMessage(), e.getMessage()),
                null);
    }


    @ResponseBody
    @ExceptionHandler(value = {MethodArgumentNotValidException.class, BindException.class})
    public ApiResponse argumentNotValidHandler(HttpServletRequest req, Exception e) {
        String errorMessage = null;
        if (e instanceof MethodArgumentNotValidException) {
            FieldError fieldError = ((MethodArgumentNotValidException) e).getBindingResult().getFieldError();
            errorMessage = String.format("[参数验证出错] %s <%s>", fieldError.getField(), fieldError.getDefaultMessage());
        }
        if (e instanceof BindException) {
            FieldError fieldError = ((BindException) e).getFieldError();
            errorMessage = String.format("[参数验证出错] %s <%s>", fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ApiResponse.of(ApiStatus.SUPPORT_ERROR.getCode(), errorMessage, null);
    }


    @ResponseBody
    @ExceptionHandler(value = {BadRequestException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ApiResponse badRequestExceptionHandler(HttpServletRequest request, Exception e) {
        return ApiResponse.of(ApiStatus.BAD_REQUEST.getCode(), e.getMessage(), null);
    }

    @ExceptionHandler(value = Throwable.class)
    @ResponseBody
    public ApiResponse defaultErrorHandler(HttpServletRequest req, Throwable t) {
        req.setAttribute("cat-state", "Exception");
        Cat.logError(t);
        log.error("Default error: ", t);

        return ApiResponse.of(ApiStatus.UNKNOWN_ERROR.getCode(), t.getMessage(), null);
    }
}
