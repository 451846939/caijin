package com.kdkj.caijin.config;

import com.kdkj.caijin.util.CheckRequestSourceUtil;
import lombok.Data;
import org.springframework.boot.security.servlet.ApplicationContextRequestMatcher;
import org.springframework.session.web.http.*;
import org.springframework.web.accept.ContentNegotiationStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

/**
 * 自定义sessionid存入位置
 *
 * @author lin
 * @create 2018-04-13 23:57
 **/

public class MyHttpSessionIdStrategy implements HttpSessionIdResolver {
    private static final String WRITTEN_SESSION_ID_ATTR = CookieHttpSessionIdResolver.class
            .getName().concat(".WRITTEN_SESSION_ID_ATTR");

    private CookieSerializer cookieSerializer = new DefaultCookieSerializer();
    private  String tokenName;
    public static MyHttpSessionIdStrategy getMyHttpSessionIdStrategy(String tokenName){
        return new MyHttpSessionIdStrategy(tokenName);
    }

    public MyHttpSessionIdStrategy(String tokenName) {
        if (tokenName == null) {
            throw new IllegalArgumentException("headerName cannot be null");
        }
        this.tokenName = tokenName;
    }

    @Override
    public List<String> resolveSessionIds(HttpServletRequest request) {
        boolean b = CheckRequestSourceUtil.JudgeIsMoblie(request);
        if (!b){
            return this.cookieSerializer.readCookieValues(request);
        }
        String headerValue = request.getHeader(this.tokenName);
        return headerValue != null ? Collections.singletonList(headerValue)
                : Collections.emptyList();
    }

    @Override
    public void setSessionId(HttpServletRequest request, HttpServletResponse response, String sessionId) {
        boolean b = CheckRequestSourceUtil.JudgeIsMoblie(request);
        if (!b){
            if (sessionId.equals(request.getAttribute(WRITTEN_SESSION_ID_ATTR))) {
                return;
            }
            request.setAttribute(WRITTEN_SESSION_ID_ATTR, sessionId);
            this.cookieSerializer
                    .writeCookieValue(new CookieSerializer.CookieValue(request, response, sessionId));
        }
        response.setHeader(this.tokenName, sessionId);
    }

    @Override
    public void expireSession(HttpServletRequest request, HttpServletResponse response) {
        boolean b = CheckRequestSourceUtil.JudgeIsMoblie(request);
        if (!b){
            this.cookieSerializer.writeCookieValue(new CookieSerializer.CookieValue(request, response, ""));
        }
        response.setHeader(this.tokenName, "");
    }

    /**
     * Sets the {@link CookieSerializer} to be used.
     *
     * @param cookieSerializer the cookieSerializer to set. Cannot be null.
     */
    public void setCookieSerializer(CookieSerializer cookieSerializer) {
        if (cookieSerializer == null) {
            throw new IllegalArgumentException("cookieSerializer cannot be null");
        }
        this.cookieSerializer = cookieSerializer;
    }
}
