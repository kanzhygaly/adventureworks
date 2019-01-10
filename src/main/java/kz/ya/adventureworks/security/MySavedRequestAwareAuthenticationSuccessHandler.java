package kz.ya.adventureworks.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 *
 * @author yerlana
 */
@Component
public class MySavedRequestAwareAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    
    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp,
            Authentication authentication) throws ServletException, IOException {
        
        SavedRequest savedRequest = requestCache.getRequest(req, resp);
        if (savedRequest == null) {
            clearAuthenticationAttributes(req);
            return;
        }
        
        String targetUrlParameter = getTargetUrlParameter();
        if (isAlwaysUseDefaultTargetUrl() || (targetUrlParameter != null && StringUtils.hasText(req.getParameter(targetUrlParameter)))) {
            requestCache.removeRequest(req, resp);
            clearAuthenticationAttributes(req);
            return;
        }

        clearAuthenticationAttributes(req);
    }

    public void setRequestCache(final RequestCache requestCache) {
        this.requestCache = requestCache;
    }
}
