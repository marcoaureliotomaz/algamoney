package com.example.algamoneyapi.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoneyapi.event.RecursoCriadoEvent;
import com.example.algamoneyapi.model.Categoria;
import com.example.algamoneyapi.model.Lancamento;
import com.example.algamoneyapi.repository.LancamentoRepository;

@RestController
@RequestMapping("/Lancamentos")
public class LancamentoResource {
	
	@Autowired
	LancamentoRepository lancamentoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping()
	public List<Lancamento> listar() {
		return lancamentoRepository.findAll();
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Lancamento> buscaPeloCodigo(@PathVariable Long codigo){
		Lancamento lancamentoEncontrado = lancamentoRepository.findById(codigo).orElse(null);
		return lancamentoEncontrado != null ? ResponseEntity.ok(lancamentoEncontrado) : ResponseEntity.notFound().build();
	}
	
	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED) 
	public ResponseEntity<Lancamento> criar(@Valid @RequestBody Lancamento lancamento , HttpServletResponse response){
		Lancamento lancamentoSalvo =  lancamentoRepository.save(lancamento);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
		
	}
	

}
