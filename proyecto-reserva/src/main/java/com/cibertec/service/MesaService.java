package com.cibertec.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cibertec.model.Mesa;
import com.cibertec.repository.IMesaRepository;

@Service
public class MesaService {
    @Autowired
    private IMesaRepository repo;
    

    public List<Mesa> listarMesas() {
        return repo.findAll();
    }

    public Mesa guardarMesa(Mesa mesa) {
        return repo.save(mesa);
    }
    
    public List<Mesa> listarMesasDisponibles() {
        return repo.findByEstadoMesaIdEstMesa(1); 
    }

    

    public Mesa obtenerMesaPorId(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public void eliminarMesa(Integer id) {
        repo.deleteById(id);
    }
    
    public long contarMesasDisponibles() {
        return repo.countByEstadoMesaIdEstMesa(1); 
    }

}