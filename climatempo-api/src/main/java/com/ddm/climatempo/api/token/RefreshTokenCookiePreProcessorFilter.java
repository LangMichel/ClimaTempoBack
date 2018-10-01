package com.ddm.climatempo.api.token;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.catalina.util.ParameterMap;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 
 * Classe que é antes de entrar no processo o token e é utizado filtro. Serve
 * para passar o token do cookie para a requisição
 */

// será um filtro que tenha prioridade mto alta, pois a requisição será
// analizada antes de todo mundo
// por isso a utilização do highest_precedence
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RefreshTokenCookiePreProcessorFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// realiza cast para a requisição
		HttpServletRequest req = (HttpServletRequest) request;

		// verifica se a requisição é para oauth, pois é uma possibilidade de ter o
		// refresh token
		// e também só usaremos o refresh_token quando este for associado no grant_type
		// da requisição.
		if ("/oauth/token".equalsIgnoreCase(req.getRequestURI())
				&& "refresh_token".equals(req.getParameter("grant_type")) && req.getCookies() != null) {
			// percorre os cookies e verifica se é refreshToken (nome do token na classe
			// refreshTokenPostProcessor)
			for (Cookie cookie : req.getCookies()) {
				if (cookie.getName().equals("refreshToken")) {
					String refreshToken = cookie.getValue();
					req = new MyServletRequestWrapper(req, refreshToken); // substituindo a requisição para poder ser
																			// alterada.
				}

			}

		}
		// o filtro passará o refresh token para os parametros da requisiçaõ.
		chain.doFilter(req, response);

	}

	// os métodos abaixo não serão necessárias, apenas estão presentes
	// pois a classe implementa uma interface que possui os métodos.
	@Override
	public void destroy() {

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}
	// necessário criar esta classe pois não conseguimos passar o código do
	// refreshtoken para a requisição
	// pois a mesma não poderá ser alterada.

	static class MyServletRequestWrapper extends HttpServletRequestWrapper {

		private String refreshToken;

		public MyServletRequestWrapper(HttpServletRequest request, String refreshToken) {
			super(request);
			this.refreshToken = refreshToken;
		}

		@Override
		public Map<String, String[]> getParameterMap() {
			// a requisição de origem será substituída e adicionada a nova requisição o
			// refreshToken
			ParameterMap<String, String[]> map = new ParameterMap<>(getRequest().getParameterMap());
			map.put("refresh_token", new String[] { refreshToken }); // nome da variavel que o request irá usar para
																		// achar na requisçaõ
			map.setLocked(true);
			return map;

		}

	}

}
