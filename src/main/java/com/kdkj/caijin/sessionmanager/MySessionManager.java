package com.kdkj.caijin.sessionmanager;

import lombok.Data;
import org.apache.catalina.session.StandardSession;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.HttpServletSession;
import org.apache.shiro.web.session.mgt.ServletContainerSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 重写shrioSessionManager
 *
 * @author lin
 * @create 2018-04-13 19:57
 **/
@Data
public class MySessionManager implements WebSessionManager{

    @Override
    public boolean isServletContainerSessions() {
        return true;
    }

    @Override
    public Session start(SessionContext context) {
        return createSession(context);
    }

    private Session createSession(SessionContext sessionContext) {
        if (!WebUtils.isHttp(sessionContext)) {
            String msg = "SessionContext must be an HTTP compatible implementation.";
            throw new IllegalArgumentException(msg);
        }

        HttpServletRequest request = WebUtils.getHttpRequest(sessionContext);

        HttpSession httpSession = request.getSession();

        String host = getHost(sessionContext);

        if (StringUtils.isEmpty(request.getHeader("token"))){
            return createSession(httpSession, host);
        }
//        StandardSession standardSession = new StandardSession();
        return null;
    }
    private String getHost(SessionContext context) {
        String host = context.getHost();
        if (host == null) {
            ServletRequest request = WebUtils.getRequest(context);
            if (request != null) {
                host = request.getRemoteHost();
            }
        }
        return host;

    }

    protected Session createSession(HttpSession httpSession, String host) {
        return new HttpServletSession(httpSession, host);
    }
    @Override
    public Session getSession(SessionKey key) throws SessionException {
        if (!WebUtils.isHttp(key)) {
            String msg = "SessionKey must be an HTTP compatible implementation.";
            throw new IllegalArgumentException(msg);
        }

        HttpServletRequest request = WebUtils.getHttpRequest(key);

        Session session = null;

        HttpSession httpSession = request.getSession(false);
        if (httpSession != null) {
            session = createSession(httpSession, request.getRemoteHost());
        }

        return session;
    }

}
