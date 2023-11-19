package com.empresa.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.empresa.entidades.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

	
	@Query("select x from Cliente x where x.nombre like ?1 or x.apellido like ?1")
	public abstract List<Cliente> listaCliente (String filtro, Pageable pageable);
	
}