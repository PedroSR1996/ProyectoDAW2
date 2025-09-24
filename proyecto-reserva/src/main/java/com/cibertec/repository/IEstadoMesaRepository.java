package com.cibertec.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.model.EstadoMesa;

public interface IEstadoMesaRepository  extends JpaRepository<EstadoMesa, Integer> {
	
	
}
