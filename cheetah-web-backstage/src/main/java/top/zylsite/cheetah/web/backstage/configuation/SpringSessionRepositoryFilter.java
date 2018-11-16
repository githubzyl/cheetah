package top.zylsite.cheetah.web.backstage.configuation;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.session.ExpiringSession;
import org.springframework.session.SessionRepository;
import org.springframework.session.web.http.SessionRepositoryFilter;

/**
 * 继承SessionRepositoryFilter，过滤掉不必要创建session的url
 * 
 * @author: zhaoyl
 * @since: 2017年11月28日 下午2:30:17
 * @history:
 */
public class SpringSessionRepositoryFilter<S extends ExpiringSession> extends SessionRepositoryFilter<S> {

	public SpringSessionRepositoryFilter(SessionRepository<S> sessionRepository) {
		super(sessionRepository);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
//		String requestPath = RequestUtil.getRequestPath(request);
//		if (FilterConstant.viewLogExcludesPath.get(requestPath) != null) {
//			filterChain.doFilter(request, response);
//			return;
//		}
		super.doFilterInternal(request, response, filterChain);
	}

}
