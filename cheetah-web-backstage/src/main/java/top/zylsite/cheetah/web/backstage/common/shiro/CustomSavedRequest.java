package top.zylsite.cheetah.web.backstage.common.shiro;

import java.io.Serializable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import top.zylsite.cheetah.base.utils.RequestUtil;

public class CustomSavedRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	private String method;
    private String queryString;
    private String requestURI;
    private boolean jump;

    /**
     * Constructs a new instance from the given HTTP request.
     *
     * @param request the current request to save.
     */
    public CustomSavedRequest(HttpServletRequest request) {
        this.method = request.getMethod();
        this.queryString = request.getQueryString();
        this.requestURI = getRequestUri(request);
        this.jump = checkJump(request);
    }

    public String getMethod() {
        return method;
    }

    public String getQueryString() {
        return queryString;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public String getRequestUrl() {
        StringBuilder requestUrl = new StringBuilder(getRequestURI());
        if (getQueryString() != null) {
            requestUrl.append("?").append(getQueryString());
        }
        return requestUrl.toString();
    }

	public boolean isJump() {
		return jump;
	}

	public void setJump(boolean jump) {
		this.jump = jump;
	}
	
	private String getRequestUri(HttpServletRequest request) {
		String uri = request.getRequestURI();
		if(null != request.getContextPath() && request.getContextPath().length() > 0) {
			uri = uri.substring(request.getContextPath().length());
        }
		return uri;
	}
	
	private boolean checkJump(HttpServletRequest request) {
        boolean isAjax = RequestUtil.isAjaxRequest(request);
        if(isAjax) {
        	return false;
        }
        Map<String,Object> paramMap = RequestUtil.getParameterMap(request);
        Object obj = paramMap.get("r");
        if(null != obj && obj.equals("1")) {
        	return true;
        }
        return false;
	}

}
