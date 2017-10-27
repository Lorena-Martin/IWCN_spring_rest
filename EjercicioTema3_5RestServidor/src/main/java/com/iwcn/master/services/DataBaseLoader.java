package com.iwcn.master.services;

import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iwcn.master.entities.Producto;
import com.iwcn.master.repositories.ProductoRepository;

@Service
public class DataBaseLoader {
	
	@Autowired
	private ProductoRepository productoRepository;
	
	@PostConstruct
	public void initDatabase() {
		
	}
	
	public void guardar(Producto prod) {
		productoRepository.save(prod);
	}
	
	public void borrar(Producto prod) {
		productoRepository.delete(prod);
	}
	
	public Producto getProducto(int id) {
		return productoRepository.findById(id);
	}
	
	public ArrayList<Producto> getTodos() {
		ArrayList<Producto> productos = new ArrayList<>();
		for(Producto p : productoRepository.findAll())
			productos.add(p);
		return productos;
	}

}
