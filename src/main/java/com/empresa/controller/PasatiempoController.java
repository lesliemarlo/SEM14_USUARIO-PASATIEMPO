package com.empresa.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.empresa.entidades.Pasatiempo;
import com.empresa.entidades.Usuario;
import com.empresa.entidades.UsuarioHasPasatiempo;
import com.empresa.entidades.UsuarioHasPasatiempoPK;
import com.empresa.service.PasatiempoService;
import com.empresa.service.UsuarioService;

@Controller
public class PasatiempoController {

	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private PasatiempoService pasatiempoService;
	
	@PostMapping("/registraPasatiempo")
	@ResponseBody()
	public HashMap<?, ?> registra(int idUsuario, int idPasatiempo) {
		HashMap<String, Object> salida = new HashMap<String, Object>();
		
		UsuarioHasPasatiempoPK objPk = new UsuarioHasPasatiempoPK();
		objPk.setIdPasatiempo(idPasatiempo);
		objPk.setIdUsuario(idUsuario);
		
		UsuarioHasPasatiempo obj = new UsuarioHasPasatiempo();
		obj.setUsuarioHasPasatiempoPK(objPk);
		
		if (usuarioService.buscaPasatiempo(objPk).isPresent()) {
			salida.put("mensaje", "Pasatiempo ya existe");
		}else {
			UsuarioHasPasatiempo objSalida = usuarioService.insertaPasatiempo(obj);
			if (objSalida == null) {
				salida.put("mensaje", "Error en el registro");
			}else {
				salida.put("mensaje", "Registro exitoso");
			}
		}
		List<Pasatiempo> lstSalida = usuarioService.traerPasatiempoDeUsuario(idUsuario);
		salida.put("lista", lstSalida);
		salida.put("usuario", idUsuario);
		return salida;
	}
	
	@PostMapping("/eliminaPasatiempo")
	@ResponseBody()
	public HashMap<?, ?> elimina(int idUsuario, int idPasatiempo) {
		
		UsuarioHasPasatiempoPK objPk = new UsuarioHasPasatiempoPK();
		objPk.setIdPasatiempo(idPasatiempo);
		objPk.setIdUsuario(idUsuario);
		
		UsuarioHasPasatiempo obj = new UsuarioHasPasatiempo();
		obj.setUsuarioHasPasatiempoPK(objPk);
		
		HashMap<String, Object> salida = new HashMap<String, Object>();
		usuarioService.eliminaPasatiempo(obj);
		if (usuarioService.buscaPasatiempo(objPk).isEmpty()) {
			salida.put("mensaje", "Eliminación exitosa");
		}else {
			salida.put("mensaje", "Error en la eliminación");
		}
		List<Pasatiempo> lstSalida = usuarioService.traerPasatiempoDeUsuario(idUsuario);
		salida.put("lista", lstSalida);
		salida.put("usuario", idUsuario);
		return salida;
	}
	
	
	@GetMapping("/listaPasatiempoPorUsuario")
	@ResponseBody
	public List<Pasatiempo> listaUsuario(int idUsuario) {
		List<Pasatiempo> lstSalida = usuarioService.traerPasatiempoDeUsuario(idUsuario);
		return lstSalida;
	}
	
	@GetMapping("/listaUsuario")
	@ResponseBody
	public List<Usuario> listaUsuario() {
		return usuarioService.listaUsuario();
	}
	

	@GetMapping("/listaPasatiempo")
	@ResponseBody
	public List<Pasatiempo> listaPasatiempo() {
		return pasatiempoService.listaPasatiempo();
	}
	
}
