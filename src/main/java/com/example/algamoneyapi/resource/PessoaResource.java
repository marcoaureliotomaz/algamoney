package com.example.algamoneyapi.resource;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.algamoneyapi.model.Categoria;
import com.example.algamoneyapi.model.Pessoa;
import com.example.algamoneyapi.repository.PessoaRepository;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@GetMapping
	public List<Pessoa> listar(){
		return pessoaRepository.findAll();
	}

	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
		Pessoa pessoaSalva =  pessoaRepository.save(pessoa);
		URI uri =  ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
		.buildAndExpand(pessoaSalva.getCodigo()).toUri();

		response.setHeader("Location",uri.toASCIIString());
		return ResponseEntity.created(uri).body(pessoaSalva);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Pessoa> buscaPeloCodigo(@PathVariable Long codigo) {
		Pessoa pessoaEncontrada = pessoaRepository.findById(codigo).orElse(null);
		return pessoaEncontrada != null ? ResponseEntity.ok(pessoaEncontrada) : ResponseEntity.notFound().build();
	}
}
