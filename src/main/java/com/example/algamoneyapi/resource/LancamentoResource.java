package com.example.algamoneyapi.resource;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoneyapi.event.RecursoCriadoEvent;
import com.example.algamoneyapi.exceptionhandler.AlgamoneyExceptionHandler.Erro;
import com.example.algamoneyapi.model.Categoria;
import com.example.algamoneyapi.model.Lancamento;
import com.example.algamoneyapi.repository.LancamentoRepository;
import com.example.algamoneyapi.repository.filter.LancamentoFilter;
import com.example.algamoneyapi.service.LancamentoService;
import com.example.algamoneyapi.service.excpetion.PessoaInexistenteOuInativaExcpetion;

@RestController
@RequestMapping("/Lancamentos")
public class LancamentoResource {
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	

	
	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private LancamentoService lancamentoService;	
	
	@GetMapping()
	public Page<Lancamento> pesquisar(LancamentoFilter lancamentoFilter, Pageable pageable) {
		return lancamentoRepository.filtrar(lancamentoFilter, pageable);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Lancamento> buscaPeloCodigo(@PathVariable Long codigo){
		Lancamento lancamentoEncontrado = lancamentoRepository.findById(codigo).orElse(null);
		return lancamentoEncontrado != null ? ResponseEntity.ok(lancamentoEncontrado) : ResponseEntity.notFound().build();
	}
	
	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED) 
	public ResponseEntity<Lancamento> criar(@Valid @RequestBody Lancamento lancamento , HttpServletResponse response){
		Lancamento lancamentoSalvo =  lancamentoService.salvar(lancamento);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
		
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		lancamentoRepository.deleteById(codigo);
		
	}
	
	@ExceptionHandler({PessoaInexistenteOuInativaExcpetion.class})
	public ResponseEntity<Object> handlePessoaInexistenteOuInativaExcpetion(PessoaInexistenteOuInativaExcpetion ex){
		String messagemUsuario = messageSource.getMessage("pessoa.inexistente-ou-inativa", null,LocaleContextHolder.getLocale());
		String messageDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(messagemUsuario, messageDesenvolvedor));
		
		return ResponseEntity.badRequest().body(erros);
	}
	

}
