/*
 * This is a logout request builder which allows to send the session on the request.<br/>
 * It also has more than one post processors.<br/>
 * <br/>
 * Unfortunately it won't trigger {@link org.springframework.security.core.session.SessionDestroyedEvent} because
 * that is triggered by {@link org.apache.catalina.session.StandardSessionFacade#invalidate()} in Tomcat and
 * for mocks it's handled by @{{@link MockHttpSession#invalidate()}} so the log out message won't be visible for tests.
 */
package kz.ya.adventureworks.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import org.springframework.beans.Mergeable;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.request.ConfigurableSmartRequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 *
 * @author yerlana
 */
public final class SessionLogoutRequestBuilder implements ConfigurableSmartRequestBuilder<SessionLogoutRequestBuilder>, Mergeable {

    private final List<RequestPostProcessor> postProcessors = new ArrayList<>();
    private String logoutUrl = "/logout";
    private MockHttpSession session;

    private SessionLogoutRequestBuilder() {
        this.postProcessors.add(SecurityMockMvcRequestPostProcessors.csrf());
    }

    static SessionLogoutRequestBuilder sessionLogout() {
        return new SessionLogoutRequestBuilder();
    }

    @Override
    public MockHttpServletRequest buildRequest(final ServletContext servletContext) {
        return MockMvcRequestBuilders.post(this.logoutUrl).session(session)
                .buildRequest(servletContext);
    }

    public SessionLogoutRequestBuilder logoutUrl(final String logoutUrl) {
        this.logoutUrl = logoutUrl;
        return this;
    }

    public SessionLogoutRequestBuilder session(final MockHttpSession session) {
        Assert.notNull(session, "'session' must not be null");
        this.session = session;
        return this;
    }

    @Override
    public boolean isMergeEnabled() {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object merge(final Object parent) {
        if (parent == null) {
            return this;
        }

        if (parent instanceof MockHttpServletRequestBuilder) {
            final MockHttpServletRequestBuilder parentBuilder = (MockHttpServletRequestBuilder) parent;
            if (this.session == null) {
                this.session = (MockHttpSession) ReflectionTestUtils.getField(parentBuilder, "session");
            }
            final List postProcessors = (List) ReflectionTestUtils.getField(parentBuilder, "postProcessors");
            this.postProcessors.addAll(0, (List<RequestPostProcessor>) postProcessors);
        } else if (parent instanceof SessionLogoutRequestBuilder) {
            final SessionLogoutRequestBuilder parentBuilder = (SessionLogoutRequestBuilder) parent;
            if (!StringUtils.hasText(this.logoutUrl)) {
                this.logoutUrl = parentBuilder.logoutUrl;
            }
            if (this.session == null) {
                this.session = parentBuilder.session;
            }
            this.postProcessors.addAll(0, parentBuilder.postProcessors);
        } else {
            throw new IllegalArgumentException("Cannot merge with [" + parent.getClass().getName() + "]");
        }
        return this;
    }

    @Override
    public SessionLogoutRequestBuilder with(final RequestPostProcessor postProcessor) {
        Assert.notNull(postProcessor, "postProcessor is required");
        this.postProcessors.add(postProcessor);
        return this;
    }

    @Override
    public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
        for (final RequestPostProcessor postProcessor : this.postProcessors) {
            request = postProcessor.postProcessRequest(request);
            if (request == null) {
                throw new IllegalStateException(
                        "Post-processor [" + postProcessor.getClass().getName() + "] returned null");
            }
        }
        return request;
    }
}
