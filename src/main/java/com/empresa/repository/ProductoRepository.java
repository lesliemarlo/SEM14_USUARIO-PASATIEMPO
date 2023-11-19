package com.empresa.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.empresa.entidades.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
	
	@Query("select x from Producto x where x.nombre like ?1")
	public abstract List<Producto> listaProducto(String filtro, Pageable pageable);

	@Modifying
	@Query("update Producto x set x.stock = x.stock - ?2 where x.idProducto = ?1 ")
	public abstract void actualizaStock(int idProducto, int cantidad);
	
}
