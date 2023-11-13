package com.empresa.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.empresa.entidades.Boleta;
import com.empresa.entidades.BoletaHasProducto;
import com.empresa.entidades.BoletaHasProductoPK;
import com.empresa.entidades.Cliente;
import com.empresa.entidades.Mensaje;
import com.empresa.entidades.Producto;
import com.empresa.entidades.Seleccion;
import com.empresa.entidades.Usuario;
import com.empresa.service.BoletaService;
import com.empresa.service.ClienteService;
import com.empresa.service.ProductoService;

import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Controller
public class BoletaController {

	
	@Autowired
	private ClienteService clienteService;

	@Autowired
	private ProductoService productoService;

	@Autowired
	private BoletaService boletaService;
	
	
	private List<Seleccion> seleccionados = new ArrayList<Seleccion>();
	
	
	@RequestMapping("/cargaCliente")
	@ResponseBody()
	public List<Cliente> listaCliente(String filtro){
		int page = 0;
		int size = 5; //Muestre los primer cinco elementos
		Pageable pageable = PageRequest.of(page, size);
		
		List<Cliente> lista = clienteService.listaCliente(filtro+"%", pageable);
		return lista;		
	}
	
	@RequestMapping("/cargaProducto")
	@ResponseBody()
	public List<Producto> listaProducto(String filtro){
		int page = 0;
		int size = 5; //Muestre los primer cinco elementos
		Pageable pageable = PageRequest.of(page, size);
		
		List<Producto> lista = productoService.listaproducto(filtro+"%", pageable);
		return lista;		
	}
	
	@RequestMapping("/listaSeleccion")
	@ResponseBody()
	public List<Seleccion> lista(){
		return this.seleccionados; 
	}
	
	@RequestMapping("/agregarSeleccion")
	@ResponseBody()
	public List<Seleccion> agregar(Seleccion obj){
		seleccionados.add(obj);
		return this.seleccionados; 
	}
	
	@RequestMapping("/eliminaSeleccion")
	@ResponseBody()
	public List<Seleccion> eliminar(int idProducto){
		seleccionados.removeIf ( (x) -> x.getIdProducto() == idProducto );
		return this.seleccionados; 
	}
	
	@RequestMapping("/registraBoleta")
	@ResponseBody()
	public Mensaje boleta(Cliente cliente, HttpSession session) {
		Usuario objUsuario = (Usuario)session.getAttribute("objUsuario");
		Mensaje objMensaje = new Mensaje();
		
		List<BoletaHasProducto> detalles = new ArrayList<BoletaHasProducto>();
		for (Seleccion seleccion : seleccionados) {
			
			BoletaHasProductoPK pk = new BoletaHasProductoPK();
			pk.setIdProducto(seleccion.getIdProducto());
			
			BoletaHasProducto psb = new BoletaHasProducto();
			psb.setPrecio(seleccion.getPrecio());
			psb.setCantidad(seleccion.getCantidad());
			psb.setProductoHasBoletaPK(pk);
			
			detalles.add(psb);
		}
		
		Boleta obj = new Boleta();
		obj.setCliente(cliente);
		obj.setDetallesBoleta(detalles);
		obj.setUsuario(objUsuario);
		
		Boleta objBoleta =  boletaService.insertaBoleta(obj);
		
		String salida = "-1";
		
		if (objBoleta != null) {
				salida = "Se generó la boleta con código N° : " + objBoleta.getIdBoleta() + "<br><br>";
				salida += "Cliente: " + objBoleta.getCliente().getNombre() + "<br><br>";
				salida += "<table class=\"table\"><tr><td>Producto</td><td>Precio</td><td>Cantidad</td><td>Subtotal</td></tr>";
				double monto = 0;
				for (Seleccion x : seleccionados) {
					salida += "<tr><td>"  + x.getNombre() 
							+ "</td><td>" + x.getPrecio() 
							+ "</td><td>" + x.getCantidad()
							+ "</td><td>" + x.getTotalParcial() + "</td></tr>";
					monto += x.getCantidad() * x.getPrecio();
				}
				salida += "</table><br>";
				salida += "Monto a pagar : " + monto;

				seleccionados.clear();
				objMensaje.setTexto(salida);	
		}
		
		return objMensaje;
	}
	
}
