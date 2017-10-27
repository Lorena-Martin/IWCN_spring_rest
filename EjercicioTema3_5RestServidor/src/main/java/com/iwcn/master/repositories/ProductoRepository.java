package com.iwcn.master.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iwcn.master.entities.Producto;



public interface ProductoRepository extends CrudRepository<Producto, Integer> {

	Producto findById(int id);

}
