package com.cibertec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.model.Mesa;

public interface IMesaRepository extends JpaRepository<Mesa, Integer> {
	
	List<Mesa> findByEstadoMesaIdEstMesa(Integer idEstMesa);
	
	long countByEstadoMesaIdEstMesa(Integer idEstMesa);


}
