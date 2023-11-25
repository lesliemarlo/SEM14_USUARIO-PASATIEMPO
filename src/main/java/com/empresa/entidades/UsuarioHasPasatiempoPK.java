package com.empresa.entidades;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

//SEM 14

@Getter
@Setter
@Embeddable //integrable
@EqualsAndHashCode //compara -hash: codigo correlativo a cada objeto- 
public class UsuarioHasPasatiempoPK implements Serializable{ // guaradr a la bd ,exel, ttx, mandra u objeto a un medio fisico(pq lo mandas a disco)

	private static final long serialVersionUID = 1L; //1L es una constante long
	private int idUsuario;
    private int idPasatiempo;


}
