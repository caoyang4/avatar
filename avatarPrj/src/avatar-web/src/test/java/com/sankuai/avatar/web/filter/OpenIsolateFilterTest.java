package com.sankuai.avatar.web.filter;

import com.sankuai.avatar.web.dal.entity.CasType;
import com.sankuai.avatar.web.dal.entity.CasUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OpenIsolateFilterTest {

    private OpenIsolateFilter filter;

    @Before
    public void setUp() throws Exception {
        filter = new OpenIsolateFilter();
        Field field1 = OpenIsolateFilter.class.getDeclaredField("openIsolateSwitch");
        field1.setAccessible(true);
        field1.set(filter, true);
    }

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    FilterChain chain;



    @Test
    public void doFilterMis() throws ServletException, IOException {
        CasUser casUser = new CasUser();
        casUser.setCasType(CasType.MIS);
        when(request.getRequestURI()).thenReturn("/open/api/");
        filter.doFilter(request, response, chain);
        verify(request).getRequestURI();
    }

    @Test
    public void doFilterAppkey() throws ServletException, IOException {
        CasUser casUser = new CasUser();
        casUser.setCasType(CasType.APPKEY);
        when(request.getRequestURI()).thenReturn("/open/api/");
        filter.doFilter(request, response, chain);
        verify(request).getRequestURI();
    }

}
