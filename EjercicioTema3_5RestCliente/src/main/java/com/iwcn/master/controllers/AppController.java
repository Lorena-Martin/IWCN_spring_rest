package com.iwcn.master.controllers;

import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.iwcn.master.entities.Producto;
import com.iwcn.master.entities.Usuario;
import com.iwcn.master.repositories.UsuarioRepository;

@Controller
public class AppController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@PostConstruct
	private void initDatabase() {
		
		// User #1: "user", with password "user" and role "USER"
		GrantedAuthority[] userRoles = { new SimpleGrantedAuthority("ROLE_USER") };  
		usuarioRepository.save(new Usuario("user", "user", Arrays.asList(userRoles)));

		// User #2: "root", with password "root" and roles "USER" and "ADMIN"  
		GrantedAuthority[] adminRoles = { new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_ADMIN") };
		usuarioRepository.save(new Usuario("root", "root", Arrays.asList(adminRoles)));

	}
	
	@RequestMapping("/")
	public ModelAndView index() {
		return new ModelAndView("indexSinLoguear_template");
	}
	

	@RequestMapping("/loguear")
	public ModelAndView loguear(Model model) {
		return new ModelAndView("loguear_template");
	}
	
	@RequestMapping("/cerrarSesion")
	public ModelAndView cerrarSesion(Model model) {
		return new ModelAndView("cerrarSesion_template");
	}
	
	@RequestMapping("/inicio")
	public ModelAndView inicio() {
		return new ModelAndView("index_template");
	}
	
	@Secured({"ROLE_ADMIN"})
    @GetMapping("/nuevoProducto")
    public ModelAndView nuevoProducto(Producto prod) {
        return new ModelAndView("nuevoProducto_template");
    }
    
    @Secured({"ROLE_ADMIN"})
    @PostMapping("/guardado")
    public ModelAndView guardado(@Valid Producto prod) {
    	RestTemplate restTemplate = new RestTemplate();
    	String url = "http://localhost:8080/guardado";
    	//String response = restTemplate.postForObject(url, prod, String.class);
    	restTemplate.postForObject(url, prod, String.class);
    	return new ModelAndView("guardado_template").addObject("nombre", prod.getNombre());
    }
    
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping("/listaProductos")
    public ModelAndView listaProductos() {
    	RestTemplate restTemplate = new RestTemplate();
    	String url = "http://localhost:8080/listaProductos";
    	 ResponseEntity<Producto[]> responseEntity = restTemplate.getForEntity(url, Producto[].class);
         Producto[] productos = responseEntity.getBody();
    	return new ModelAndView("listaProductos_template").addObject("lista", productos);
    }
    
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping("/info/{option}")
    public ModelAndView info(@PathVariable String option) {
    	int indice = Integer.parseInt(option);
    	RestTemplate restTemplate = new RestTemplate();
    	String url = "http://localhost:8080/info/"+indice;
    	Producto pr = restTemplate.getForObject(url, Producto.class);
    	return new ModelAndView("info_template").addObject("codigo", pr.getCodigo()).addObject("nombre", pr.getNombre()).addObject("descripcion", pr.getDescripcion()).addObject("precio", pr.getPrecio());
    }
    
    @Secured({"ROLE_ADMIN"})
    @RequestMapping("/eliminado/{option}")
    public ModelAndView eliminado(@PathVariable String option) {
    	int indice = Integer.parseInt(option);
    	RestTemplate restTemplate = new RestTemplate();
    	String url = "http://localhost:8080/eliminado/"+indice;
//    	Producto pr = restTemplate.getForObject(url, Producto.class);
    	restTemplate.getForObject(url, Producto.class);
    	return new ModelAndView("eliminado_template");
    }
    
    @Secured({"ROLE_ADMIN"})
    @RequestMapping("/editar/{option}")
    public ModelAndView editar(Model model, @PathVariable String option) {
    	int indice = Integer.parseInt(option);
    	RestTemplate restTemplate = new RestTemplate();
    	String url = "http://localhost:8080/info/"+indice;
    	Producto pr = restTemplate.getForObject(url, Producto.class);
    	model.addAttribute(pr);
    	return new ModelAndView("editar_template").addObject("id", pr.getId());
    }
    
    @Secured({"ROLE_ADMIN"})
    @RequestMapping("/editado")
    public ModelAndView editado(@Valid Producto prod) {
    	RestTemplate restTemplate = new RestTemplate();
    	String url = "http://localhost:8080/editado/";
    	//String respuesta = restTemplate.postForObject(url, prod, String.class);
    	restTemplate.postForObject(url, prod, String.class);
    	return new ModelAndView("editado_template").addObject("nombre", prod.getNombre());
    }
    

}
