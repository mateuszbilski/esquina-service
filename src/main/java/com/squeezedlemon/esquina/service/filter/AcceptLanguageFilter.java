package com.squeezedlemon.esquina.service.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import com.squeezedlemon.esquina.service.util.SystemLanguage;

public class AcceptLanguageFilter extends OncePerRequestFilter {

	private static final String REQUEST_ERROR_MESSAGE = "Request must contain valid Accept-Language header";

	private static final String LANGUAGE_NOT_SUPPORTED_MESSAGE = "Language is not supported";
	
	private static final Logger logger = LoggerFactory.getLogger(AcceptLanguageFilter.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String lang = request.getHeader("Accept-Language");
		
		if(request.getRequestURI().equals(request.getContextPath() + "/")) {
			/**
			 * Because OpenShift send request to "/" after deployment, filter cannot send error if this
			 * request hasn't got Accept-Language header. All other valid requests must have this header 
			 */
			filterChain.doFilter(request, response);
		} else {
			if (lang == null || lang.isEmpty()) {
				response.sendError(HttpStatus.NOT_ACCEPTABLE.value(), REQUEST_ERROR_MESSAGE);
			} else if (SystemLanguage.enumFromString(request.getLocale().getLanguage()) == null) {
				response.sendError(HttpStatus.NOT_ACCEPTABLE.value(), LANGUAGE_NOT_SUPPORTED_MESSAGE);
			}
			else {
				filterChain.doFilter(request, response);
			}
		}
	}

}
