package kr.co.monitoringserver.infra.config;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class AlertInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        return true;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        Object lists = modelAndView.getModel().get("alerts");
        Object count = modelAndView.getModel().get("count");

        if (lists != null) {
            request.getSession().setAttribute("alerts", lists);
            request.getSession().setAttribute("count", count);
        }
    }

}
