package org.myself.udemy.springboot.app.items.model;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Producto {

	private Long id;
	private String nombre;
	private Double precio;
	private Date createAt;
	private Integer port;
	
}
