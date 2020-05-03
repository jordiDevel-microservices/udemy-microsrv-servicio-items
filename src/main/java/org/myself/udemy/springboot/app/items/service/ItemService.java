package org.myself.udemy.springboot.app.items.service;

import java.util.List;

import org.myself.udemy.springboot.app.items.model.Item;
import org.myself.udemy.springboot.app.items.model.Producto;

public interface ItemService {

	public List<Item> findAll();
	
	public Item findById(Long id, Integer cantidad);
	
	public Producto save(Producto producto);
	
	public Producto update(Producto producto, Long id);
	
	public void delete(Long id);
	
}
