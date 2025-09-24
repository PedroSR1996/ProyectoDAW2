package com.cibertec.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.model.Reserva;

public interface IReservaRepository extends JpaRepository<Reserva, Integer> {

}
