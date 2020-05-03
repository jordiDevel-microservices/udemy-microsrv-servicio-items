package org.myself.udemy.springboot.app.items.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.myself.udemy.springboot.app.items.model.Item;
import org.myself.udemy.springboot.app.items.model.Producto;
import org.myself.udemy.springboot.app.items.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RefreshScope
@RestController
public class ItemController {

	@Autowired
	@Qualifier(value = "itemServiceFeignImpl")
	//@Qualifier(value = "itemServiceRestTemplateImpl")
	private ItemService itemService;
	
	@Value("${configuracion.texto}")
	private String configuracionTexto;
	
	@GetMapping("/listar")
	public List<Item> listar() {
		return this.itemService.findAll();
	}
	
	@HystrixCommand(fallbackMethod = "detalleAlternativo")
	@GetMapping("/ver/{id}/cantidad/{cantidad}")
	public Item detalle(@PathVariable Long id, @PathVariable Integer cantidad) {
		return this.itemService.findById(id, cantidad);
	}
	
	public Item detalleAlternativo(Long id, Integer cantidad) {
		return new Item();
	}
	
	@GetMapping("/obtener-config")
	public ResponseEntity<Map<String, String>> obtenerConfig() {
		Map<String, String> json = new HashMap<>();
		json.put("texto", configuracionTexto);
		
		return new ResponseEntity<Map<String, String>>(json, HttpStatus.OK);
	}
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto crear(@RequestBody Producto producto) {
		return this.itemService.save(producto);
	}
	
	@PutMapping("/editar/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto editar(@PathVariable Long id, @RequestBody Producto producto) {
		return this.itemService.update(producto, id);
	}
	
	@DeleteMapping("/eliminar/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminar(@PathVariable Long id) {
		this.itemService.delete(id);
	}
	
}
