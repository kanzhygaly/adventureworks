/*
 * The Entry Point will not redirect to any sort of Login - it will return the 401
 */
package kz.ya.adventureworks.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 *
 * @author yerlana
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse resp,
            AuthenticationException ex) throws IOException, ServletException {
        
        resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}
