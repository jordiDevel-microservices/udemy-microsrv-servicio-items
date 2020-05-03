package org.myself.udemy.springboot.app.items.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.myself.udemy.springboot.app.items.client.ProductoClienteRest;
import org.myself.udemy.springboot.app.items.model.Item;
import org.myself.udemy.springboot.app.items.model.Producto;
import org.myself.udemy.springboot.app.items.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceFeignImpl implements ItemService {

	private static final String FEIGN_CLIENT = "feign";
	
	@Autowired
	public ProductoClienteRest productoClienteRest;
	
	@Override
	public List<Item> findAll() {
		return this.productoClienteRest.listar().stream().map(p -> new Item(p, 1, FEIGN_CLIENT)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer cantidad) {
		return new Item(this.productoClienteRest.detalle(id), cantidad, FEIGN_CLIENT);
	}

	@Override
	public Producto save(Producto producto) {
		return this.productoClienteRest.crear(producto);
	}

	@Override
	public Producto update(Producto producto, Long id) {
		return this.productoClienteRest.editar(id, producto);
	}

	@Override
	public void delete(Long id) {
		this.productoClienteRest.eliminar(id);
	}

}
