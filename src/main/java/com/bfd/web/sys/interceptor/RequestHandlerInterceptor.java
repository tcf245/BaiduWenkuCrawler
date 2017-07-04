package com.bfd.web.sys.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class RequestHandlerInterceptor implements HandlerInterceptor {
	private static final  Logger log = LoggerFactory.getLogger(RequestHandlerInterceptor.class);
	private List<String> excludedUrls;

    public void setExcludedUrls(List<String> excludedUrls) {
        this.excludedUrls = excludedUrls;
    }

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String path = request.getContextPath();
		String basePath = "http://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

		// String sid = (String)
		// request.getSession().getAttribute(Constant.SESSION_USER_NAME);
		String token = request.getParameter("token");
		String requestUri = request.getRequestURI();
		for (String url : excludedUrls) {
			if (requestUri.endsWith(url)) {
				return true;
			}
		}

		if (StringUtils.isEmpty(token) || StringUtils.isBlank(token)) {
			log.info("requestUri :[ " + requestUri + " ]has be deny !!!");
			response.sendRedirect(basePath + "noToken");
			return false;
		} else {

			/*User u = userService.findByUseToken(token);
			if (null == u){
				// 用户token不存在
				response.sendRedirect(basePath + "noToken");
			return false;
		}*/
		return true;
	}

}
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
