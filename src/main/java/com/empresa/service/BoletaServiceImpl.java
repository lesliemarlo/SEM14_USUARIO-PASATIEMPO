package com.empresa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.empresa.entidades.Boleta;
import com.empresa.entidades.BoletaHasProducto;
import com.empresa.repository.BoletaHasProductoRepository;
import com.empresa.repository.BoletaRepository;
import com.empresa.repository.ProductoRepository;

@Service
public class BoletaServiceImpl implements BoletaService{

	@Autowired
	private BoletaRepository boletaRepository;
	
	@Autowired
	private BoletaHasProductoRepository boletaHasProductoRepository;
	
	@Autowired
	private ProductoRepository ProductoRepository;
	
	@Override
	@Transactional
	public Boleta insertaBoleta(Boleta obj) {
		Boleta objCabecera = boletaRepository.save(obj);
		for (BoletaHasProducto detalle: obj.getDetallesBoleta()) {
			detalle.getProductoHasBoletaPK().setIdBoleta(objCabecera.getIdBoleta());
			boletaHasProductoRepository.save(detalle);
			ProductoRepository.actualizaStock(detalle.getProductoHasBoletaPK().getIdProducto(), detalle.getCantidad());
		}
		return objCabecera;
	}

	

}
