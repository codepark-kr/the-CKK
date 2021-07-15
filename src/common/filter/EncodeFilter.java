package common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;


@WebFilter(urlPatterns = {
		"/*"
		
})
public class EncodeFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		
		//전처리
		request.setCharacterEncoding("UTF-8");
		System.out.println("[UTF-8] 인코딩 처리됨.");
		
		//FilterChain의 다음 필터의 doFilter메소드를 호출한다.
		//다음 필터 객체가 없다면, Servlet의 service메소드를 호출한다.
		chain.doFilter(request, response);
		
		//후처리
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	
}
