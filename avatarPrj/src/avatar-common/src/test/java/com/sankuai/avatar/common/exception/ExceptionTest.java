package com.sankuai.avatar.common.exception;

import com.sankuai.avatar.common.exception.general.BadRequestException;
import com.sankuai.avatar.common.exception.general.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ExceptionTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testException() {
        BadRequestException badRequestExceptionUnderTest = new BadRequestException("msg", new Exception("message"));
        BadRequestException badRequestExceptionUnderTest2 = new BadRequestException("msg");
        Assert.assertNotNull(badRequestExceptionUnderTest.getMessage());
        Assert.assertNotNull(badRequestExceptionUnderTest2.getMessage());

        InterfaceNotFoundException InterfaceNotFoundExceptionTest = new InterfaceNotFoundException("msg", new Exception("message"));
        InterfaceNotFoundException InterfaceNotFoundExceptionTest2 = new InterfaceNotFoundException("msg");
        Assert.assertNotNull(InterfaceNotFoundExceptionTest.getMessage());
        Assert.assertNotNull(InterfaceNotFoundExceptionTest2.getMessage());

        IpNotAllowedException IpNotAllowedExceptionTest = new IpNotAllowedException("msg", new Exception("message"));
        IpNotAllowedException IpNotAllowedExceptionTest2 = new IpNotAllowedException("msg");
        Assert.assertNotNull(IpNotAllowedExceptionTest.getMessage());
        Assert.assertNotNull(IpNotAllowedExceptionTest2.getMessage());

        IpRequestExceededException IpRequestExceededExceptionTest = new IpRequestExceededException("msg", new Exception("message"));
        IpRequestExceededException IpRequestExceededExceptionTest2 = new IpRequestExceededException("msg");
        Assert.assertNotNull(IpRequestExceededExceptionTest.getMessage());
        Assert.assertNotNull(IpRequestExceededExceptionTest2.getMessage());

        MethodNotAllowedException MethodNotAllowedExceptionTest = new MethodNotAllowedException("msg", new Exception("message"));
        MethodNotAllowedException MethodNotAllowedExceptionTest2 = new MethodNotAllowedException("msg");
        Assert.assertNotNull(MethodNotAllowedExceptionTest.getMessage());
        Assert.assertNotNull(MethodNotAllowedExceptionTest2.getMessage());

        ParameterIllegalException ParameterIllegalExceptionTest = new ParameterIllegalException("msg", new Exception("message"));
        ParameterIllegalException ParameterIllegalExceptionTest2 = new ParameterIllegalException("msg");
        Assert.assertNotNull(ParameterIllegalExceptionTest.getMessage());
        Assert.assertNotNull(ParameterIllegalExceptionTest2.getMessage());

        RequestLengthExceededException RequestLengthExceededExceptionTest = new RequestLengthExceededException("msg", new Exception("message"));
        RequestLengthExceededException RequestLengthExceededExceptionTest2 = new RequestLengthExceededException("msg");
        Assert.assertNotNull(RequestLengthExceededExceptionTest.getMessage());
        Assert.assertNotNull(RequestLengthExceededExceptionTest2.getMessage());

        RequestLimitException RequestLimitExceptionTest = new RequestLimitException("msg", new Exception("message"));
        RequestLimitException RequestLimitExceptionTest2 = new RequestLimitException("msg");
        Assert.assertNotNull(RequestLimitExceptionTest.getMessage());
        Assert.assertNotNull(RequestLimitExceptionTest2.getMessage());

        ServiceNotAvailableException ServiceNotAvailableExceptionTest = new ServiceNotAvailableException("msg", new Exception("message"));
        ServiceNotAvailableException ServiceNotAvailableExceptionTest2 = new ServiceNotAvailableException("msg");
        Assert.assertNotNull(ServiceNotAvailableExceptionTest.getMessage());
        Assert.assertNotNull(ServiceNotAvailableExceptionTest2.getMessage());

        SystemErrorException SystemErrorExceptionTest = new SystemErrorException("msg", new Exception("message"));
        SystemErrorException SystemErrorExceptionTest2 = new SystemErrorException("msg");
        Assert.assertNotNull(SystemErrorExceptionTest.getMessage());
        Assert.assertNotNull(SystemErrorExceptionTest2.getMessage());

        TimeoutException TimeoutExceptionTest = new TimeoutException("msg", new Exception("message"));
        TimeoutException TimeoutExceptionTest2 = new TimeoutException("msg");
        Assert.assertNotNull(TimeoutExceptionTest.getMessage());
        Assert.assertNotNull(TimeoutExceptionTest2.getMessage());

        UnAuthorizedException UnAuthorizedExceptionTest = new UnAuthorizedException("msg", new Exception("message"));
        UnAuthorizedException UnAuthorizedExceptionTest2 = new UnAuthorizedException("msg");
        Assert.assertNotNull(UnAuthorizedExceptionTest.getMessage());
        Assert.assertNotNull(UnAuthorizedExceptionTest2.getMessage());

        UserNotAllowedException UserNotAllowedExceptionTest = new UserNotAllowedException("msg", new Exception("message"));
        UserNotAllowedException UserNotAllowedExceptionTest2 = new UserNotAllowedException("msg");
        Assert.assertNotNull(UserNotAllowedExceptionTest.getMessage());
        Assert.assertNotNull(UserNotAllowedExceptionTest2.getMessage());

        UserRequestExceededException UserRequestExceededExceptionTest = new UserRequestExceededException("msg", new Exception("message"));
        UserRequestExceededException UserRequestExceededExceptionTest2 = new UserRequestExceededException("msg");
        Assert.assertNotNull(UserRequestExceededExceptionTest.getMessage());
        Assert.assertNotNull(UserRequestExceededExceptionTest2.getMessage());

    }
}
