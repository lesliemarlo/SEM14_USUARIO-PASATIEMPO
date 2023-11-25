package com.empresa.entidades;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

//SEM14
@Getter
@Setter
@Entity
@Table(name = "usuario_has_pasatiempo")
public class UsuarioHasPasatiempo {
	
	@EmbeddedId//La conexion de muchos a muchos entre entidades
	private UsuarioHasPasatiempoPK usuarioHasPasatiempoPK;
	
	//MANY TO ONE

	@ManyToOne
	@JoinColumn(name = "idUsuario", nullable = false, insertable = false, updatable = false)
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "idPasatiempo", nullable = false, insertable = false, updatable = false)
	private Pasatiempo pasatiempo;
	
	

}
