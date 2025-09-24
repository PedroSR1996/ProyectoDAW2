package com.cibertec.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.model.EstadoMesa;
import com.cibertec.repository.IEstadoMesaRepository;

@Service
public class EstadoMesaService {
    @Autowired
    private IEstadoMesaRepository repo;

    public List<EstadoMesa> listarEstadoMesas() {
        return repo.findAll();
    }

    public EstadoMesa guardarEstadoMesa(EstadoMesa estadomesas) {
        return repo.save(estadomesas);
    }

    public EstadoMesa obtenerEstadoMesaPorId(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public void eliminarEstadoMesa(Integer id) {
        repo.deleteById(id);
    }
}