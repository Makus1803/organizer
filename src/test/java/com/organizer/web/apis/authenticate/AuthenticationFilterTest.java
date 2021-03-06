package com.organizer.web.apis.authenticate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class AuthenticationFilterTest {

    @MockBean
    private AuthenticationManager authenticationManagerMock;

    @Test(expected = InsufficientAuthenticationException.class)
    public void attemptAuthentication_emptyRequestBody_shouldFail() throws IOException, ServletException {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/authentications");
        AuthenticationFilter filter = new AuthenticationFilter();
        filter.setAuthenticationManager(authenticationManagerMock);
        filter.attemptAuthentication(request, new MockHttpServletResponse());
    }

    @Test(expected = InsufficientAuthenticationException.class)
    public void attemptAuthentications_invalidJsonStringRequestBody_shouldFail() throws IOException, ServletException {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/authentications");
        request.setContent("username=testusername&password=TestPassword".getBytes());
        AuthenticationFilter filter = new AuthenticationFilter();
        filter.setAuthenticationManager(authenticationManagerMock);
        filter.attemptAuthentication(request, new MockHttpServletResponse());
    }

    @Test
    public void attemptAuthentication_validJsonStringRequestBody_shouldSucceed() throws IOException, ServletException {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/authentications");
        request.setContent("{\"username\": \"testusername\", \"password\": \"TestPassword!\"}".getBytes());
        AuthenticationFilter filter = new AuthenticationFilter();
        filter.setAuthenticationManager(authenticationManagerMock);
        filter.attemptAuthentication(request, new MockHttpServletResponse());
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken("testusername", "TestPassword!");
        verify(authenticationManagerMock).authenticate(token);
    }
}
