package org.myself.udemy.springboot.app.items.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.myself.udemy.springboot.app.items.model.Item;
import org.myself.udemy.springboot.app.items.model.Producto;
import org.myself.udemy.springboot.app.items.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ItemServiceRestTemplateImpl implements ItemService {

	private static final String REST_TEMPLATE_CLIENT = "restTemplate";
	
	@Autowired
	private RestTemplate clienteRest;
	
	@Override
	public List<Item> findAll() {
		String url = "http://servicio-productos/listar";
		
		List<Producto> productos = Arrays.asList(this.clienteRest.getForObject(url, Producto[].class));
		
		return productos.stream().map(p -> new Item(p, 1, REST_TEMPLATE_CLIENT)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer cantidad) {
		String url = "http://servicio-productos/ver/{id}";

		Producto producto = this.clienteRest.getForObject(url, Producto.class, id);
		
		return new Item(producto, cantidad, REST_TEMPLATE_CLIENT);
	}

	@Override
	public Producto save(Producto producto) {
		String url = "http://servicio-productos/crear";

		HttpEntity<Producto> entity = new HttpEntity<>(producto);
		ResponseEntity<Producto> productoResp = this.clienteRest.exchange(url, HttpMethod.POST, entity, Producto.class);
		
		return productoResp.getBody();
	}

	@Override
	public Producto update(Producto producto, Long id) {
		String url = "http://servicio-productos/editar/{id}";

		HttpEntity<Producto> entity = new HttpEntity<>(producto);
		ResponseEntity<Producto> productoResp = this.clienteRest.exchange(url, HttpMethod.PUT, entity, Producto.class, id);
		
		return productoResp.getBody();
	}

	@Override
	public void delete(Long id) {
		String url = "http://servicio-productos/eliminar/{id}";
		this.clienteRest.delete(url, id);
	}

}
