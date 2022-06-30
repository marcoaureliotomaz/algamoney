package com.example.algamoneyapi.exceptionhandler;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;



@ControllerAdvice
public class AlgamoneyExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String messagemUsuario = messageSource.getMessage("mensagem.invalida", null,LocaleContextHolder.getLocale());
		String messageDesenvolvedor = ex.getCause().toString();
		return handleExceptionInternal(ex, new Erro(messagemUsuario, messageDesenvolvedor), headers, HttpStatus.BAD_REQUEST, request) ;
	}
	
	public static class Erro{
		private String messagemUsuario;
		private String messagemDesenvolvedor;
		
		public String getMessagemUsuario() {
			return messagemUsuario;
		}

		public void setMessagemUsuario(String messagemUsuario) {
			this.messagemUsuario = messagemUsuario;
		}

		public String getMessagemDesenvolvedor() {
			return messagemDesenvolvedor;
		}

		public void setMessagemDesenvolvedor(String messagemDesenvolvedor) {
			this.messagemDesenvolvedor = messagemDesenvolvedor;
		}

		public Erro(String messagemUsuario, String messagemDesenvolvedor) {
			this.messagemUsuario = messagemUsuario;
			this.messagemDesenvolvedor = messagemDesenvolvedor;
		}
		
	}
}
