package com.iwcn.master.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.iwcn.master.entities.Producto;
import com.iwcn.master.services.DataBaseLoader;

@RestController
public class AppController {
	
	@Autowired
	private DataBaseLoader dbloader;
	
    @RequestMapping(value = "/guardado", method = RequestMethod.POST)
    public ResponseEntity<Boolean> guardado(@RequestBody Producto prod) {
    	dbloader.guardar(prod);
    	return new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "/listaProductos", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Producto> listaProductos() {
    	return dbloader.getTodos();
    }
    
    @RequestMapping(value = "/info/{option}", method = RequestMethod.GET)
    public ResponseEntity<Producto> info(@PathVariable String option) {
    	int indice = Integer.parseInt(option);
    	Producto pr = dbloader.getProducto(indice);
    	 ResponseEntity<Producto> respuesta;
         if (pr == null) {
             respuesta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
         } else {
             respuesta = new ResponseEntity<>(pr, HttpStatus.OK);
         }
         return respuesta;
    }
    
    @RequestMapping(value = "/eliminado/{option}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminado(@PathVariable String option) {
    	int indice = Integer.parseInt(option);
    	Producto pr = dbloader.getProducto(indice);
    	dbloader.borrar(pr);
    }
    
    @RequestMapping(value = "/editado", method = RequestMethod.POST)
    public ResponseEntity<Boolean> editado(@RequestBody Producto prod) {
    	dbloader.guardar(prod);
    	return new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
    }
    

}
