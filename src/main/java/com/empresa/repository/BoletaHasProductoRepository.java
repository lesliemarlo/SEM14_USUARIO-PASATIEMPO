package com.empresa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.empresa.entidades.BoletaHasProducto;

public interface BoletaHasProductoRepository extends JpaRepository<BoletaHasProducto, Integer> {

	@Modifying
	@Query("update Producto x set x.stock = x.stock - :can where x.idProducto = :pro")
	public abstract void actualizaStock(@Param("can")int cantidad, @Param("pro")int idProducto);
	
	@Query("Select x from BoletaHasProducto x where x.boleta.idBoleta = :param_boleta")
	public abstract List<BoletaHasProducto> listaDetalle(@Param("param_boleta") int idBoleta);
	
}
