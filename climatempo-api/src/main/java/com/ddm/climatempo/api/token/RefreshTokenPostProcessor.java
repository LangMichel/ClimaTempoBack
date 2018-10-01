package com.ddm.climatempo.api.token;
//classe para processar o refresh token após sua criação

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.ddm.climatempo.api.config.property.ClimaTempoApiProperty;

/**
 * Recebe um parametro generico, oauth2accesstoken, é o tipo do dado que será
 * interceptado quando o token estiver voltando. ex.: para interceptar os
 * controllers de agendamento, será substituido pela classe
 * Agendamento(AgendamentoResource) e sempre que criar um responseBody(corpo de
 * resposta) for agendamento, cairá nos métodos da classe
 * refreshtokenpostprocessor.
 */
@ControllerAdvice
public class RefreshTokenPostProcessor implements ResponseBodyAdvice<OAuth2AccessToken> {

	@Autowired
	private ClimaTempoApiProperty salonManagerApiProperty;
	
	@Override
	public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken body, MethodParameter returnType,
			MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
			ServerHttpRequest request, ServerHttpResponse response) {

		// faz-se um cast para poder pois é preciso httpServlet pois é recebido como
		// ServerHttp
		HttpServletRequest req = ((ServletServerHttpRequest) request).getServletRequest();
		HttpServletResponse resp = ((ServletServerHttpResponse) response).getServletResponse();

		//é realizado um cast para poder utilizar o método setRefreshToken
		DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) body;
		
		// obtem o refreshToken para a variável.
		String refreshToken = body.getRefreshToken().getValue();
		adicionarRefreshTokenCookie(refreshToken, req, resp); // adicionar o cookie.
		removerRefreshTokenBody(token);

		return body;
	}

	private void adicionarRefreshTokenCookie(String refreshToken, HttpServletRequest req, HttpServletResponse resp) {
		// Criando o cookie.
		Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
		// propriedades do cookie:
		refreshTokenCookie.setHttpOnly(true); // somente acessível via http.
		refreshTokenCookie.setSecure(salonManagerApiProperty.getSeguranca().isEnableHttps()); //em produção true, desenvolvimento false. para utilizar https.
		// qual caminho o cookie deve ser enviado pelo browser automaticamente:
		refreshTokenCookie.setPath(req.getContextPath() + "/oauth/token"); // se tiver algum contextpath será
																			// concatenado ao oauth/token
		// tempo de expiração do cookie(em dias): 30 dias no parametro.
		refreshTokenCookie.setMaxAge(2592000);
		resp.addCookie(refreshTokenCookie);

	}

	private void removerRefreshTokenBody(DefaultOAuth2AccessToken token) {
		token.setRefreshToken(null);

	}

	// o método before só será executado quando o método supports retornar true.
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return returnType.getMethod().getName().equals("postAccessToken");
	}

}
