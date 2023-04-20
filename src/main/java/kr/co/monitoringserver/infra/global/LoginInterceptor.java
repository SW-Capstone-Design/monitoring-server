package kr.co.monitoringserver.infra.global;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.handler.WebRequestHandlerInterceptorAdapter;

public class LoginInterceptor extends WebRequestHandlerInterceptorAdapter {

    /**
     * Create a new WebRequestHandlerInterceptorAdapter for the given WebRequestInterceptor.
     *
     * @param requestInterceptor the WebRequestInterceptor to wrap
     */
    public LoginInterceptor(WebRequestInterceptor requestInterceptor) {
        super(requestInterceptor);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {

        HttpSession session = request.getSession();

        // 로그인되어 있지 않은 경우
        if (session.getAttribute("user") == null) {
            response.sendRedirect("/signup");   // 회원 가입 페이지로 이동
            return false;   // 컨트롤러로 요청을 전달하지 않음
        }

        return true;    // 컨트롤러로 요청을 전달함
    }
}
