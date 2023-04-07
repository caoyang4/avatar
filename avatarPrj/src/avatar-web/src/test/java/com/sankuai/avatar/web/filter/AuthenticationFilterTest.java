package com.sankuai.avatar.web.filter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

/**
 * @author caoyang
 * @create 2023-02-16 11:27
 */
@RunWith(MockitoJUnitRunner.class)
public class AuthenticationFilterTest {

    private AuthenticationFilter filter;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    FilterChain chain;

    @Before
    public void setUp(){
        filter = new AuthenticationFilter();
    }

    @Test
    public void doFilter1() throws ServletException, IOException {
        Cookie cookie = new Cookie("name", "ssoid");
        cookie.setValue("xxx");
        Cookie[] cookies = {cookie};
        when(request.getCookies()).thenReturn(cookies);
        filter.doFilter(request, response, chain);
        verify(request, times(2)).getCookies();
    }

    @Test
    public void doFilter2() throws ServletException, IOException {
        when(request.getHeader("oceanus-auth")).thenReturn("base64");
        filter.doFilter(request, response, chain);
        verify(request, times(2)).getHeader(Mockito.anyString());
    }

}