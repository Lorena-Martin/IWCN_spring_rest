package com.iwcn.master.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iwcn.master.entities.Usuario;



public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {

	Usuario findByUsuario(String usuario);

}
