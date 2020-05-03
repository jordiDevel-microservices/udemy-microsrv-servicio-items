package org.myself.udemy.springboot.app.items.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

	private Producto producto;
	private Integer cantidad;
	private String client;
	
	public Double getTotal() {
		if (producto == null || producto.getPrecio() == null  || this.cantidad == null) {
			return 0D;
		}
		
		return this.producto.getPrecio() * this.cantidad.doubleValue();
	}
	
}
