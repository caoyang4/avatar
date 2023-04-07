package com.sankuai.avatar.web.handler;

import com.dianping.cat.Cat;
import com.sankuai.avatar.common.constant.ResponseStatusEnum;
import com.sankuai.avatar.common.exception.JsonSerializationException;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import com.sankuai.avatar.common.exception.general.RequestLimitException;
import com.sankuai.avatar.resource.exception.OrgNotExistException;
import com.sankuai.avatar.web.constant.ApiStatus;
import com.sankuai.avatar.web.exception.SupportErrorException;
import com.sankuai.avatar.web.vo.ApiResponse;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

/**
 * 全局接口错误捕获统一处理
 */

@RestController
@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = SupportErrorException.class)
    @ResponseBody
    public ApiResponse supportErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        Cat.logError(e);
        return ApiResponse.of(ApiStatus.SUPPORT_ERROR.getCode(), ApiStatus.SUPPORT_ERROR.getMessage() + e.getMessage(), e.getMessage());
    }

    @ExceptionHandler(value = DuplicateKeyException.class)
    @ResponseBody
    public ApiResponse duplicateKeyErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        Cat.logError(e);
        return ApiResponse.of(ApiStatus.SUPPORT_ERROR.getCode(), ApiStatus.DUPLICATE_ERROR.getMessage(), e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ApiResponse defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        req.setAttribute("cat-state", "Exception");
        e.printStackTrace();
        Cat.logError(e);
        return ApiResponse.of(ApiStatus.UNKNOWN_ERROR.getCode(), e.getMessage(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ApiResponse exceptionHandler(MissingServletRequestParameterException e) {
        return ApiResponse.of(400, e.getMessage(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ApiResponse httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
        return ApiResponse.of(400, e.getMessage(), e.getMessage());
    }

    @ExceptionHandler(value = ValidationException.class)
    @ResponseBody
    public ApiResponse handleValidationException(ValidationException e){
        Cat.logError(e);
        return ApiResponse.of(ApiStatus.SUPPORT_ERROR.getCode(), ApiStatus.SUPPORT_ERROR.getMessage()+"数据校验不通过", e.getMessage());
    }

    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public ApiResponse handleBindException(BindException e){
        Cat.logError(e);
        return ApiResponse.of(ApiStatus.SUPPORT_ERROR.getCode(), ApiStatus.SUPPORT_ERROR.getMessage()+"数据校验不通过", e.getMessage());
    }

    @ExceptionHandler(value = OrgNotExistException.class)
    @ResponseBody
    public ApiResponse handleOrgNotExistException(OrgNotExistException e) {
        Cat.logError(e);
        return ApiResponse.of(ApiStatus.SUPPORT_ERROR.getCode(), ApiStatus.SUPPORT_ERROR.getMessage(), e.getMessage());
    }


        // ------------------------ SDK调用异常类型 ------------------------

    @ExceptionHandler(value = SdkBusinessErrorException.class)
    @ResponseBody
    public ApiResponse handleSdkBusinessErrorException(HttpServletRequest req, SdkBusinessErrorException e){
        Cat.logError(e);
        return ApiResponse.of(ResponseStatusEnum.SDK_BUSINESS_ERROR.getCode(),
                ResponseStatusEnum.SDK_BUSINESS_ERROR.getMessage(), e.getMessage());
    }

    @ExceptionHandler(value = SdkCallException.class)
    @ResponseBody
    public ApiResponse handleSdkCallErrorException(HttpServletRequest req, SdkCallException e){
        req.setAttribute("cat-state", "SdkCallException");
        Cat.logError(e);
        return ApiResponse.of(ResponseStatusEnum.SDK_CALL_ERROR.getCode(),
                ResponseStatusEnum.SDK_CALL_ERROR.getMessage(), e.getMessage());
    }

    // ------------------------ 通用异常类型 ------------------------

    @ExceptionHandler(value = RequestLimitException.class)
    @ResponseBody
    public ApiResponse handleRequestLimitException(HttpServletRequest req, RequestLimitException e){
        req.setAttribute("cat-state", "RequestLimitException");
        Cat.logError(e);
        return ApiResponse.of(ResponseStatusEnum.REQUEST_LIMIT.getCode(),
                ResponseStatusEnum.REQUEST_LIMIT.getMessage(), e.getMessage());
    }

    @ExceptionHandler(value = JsonSerializationException.class)
    @ResponseBody
    public ApiResponse handleJsonSerializationException(HttpServletRequest req, JsonSerializationException e){
        req.setAttribute("cat-state", "JsonSerializationException");
        Cat.logError(e);
        return ApiResponse.of(ApiStatus.SUPPORT_ERROR.getCode(),
                ApiStatus.SUPPORT_ERROR.getMessage() + "JSON序列化异常", e.getMessage());
    }
}

