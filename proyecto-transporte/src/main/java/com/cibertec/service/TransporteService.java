package com.cibertec.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.model.Transporte;
import com.cibertec.repository.ITransporteRepository;

@Service
public class TransporteService {

    @Autowired
    private ITransporteRepository repo;

    public List<Transporte> listarTransporte() {
        return repo.findAll();
    }

    public Transporte guardarTransporte(Transporte transporte) {
        return repo.save(transporte);
    }

    public Transporte obtenerTransportePorId(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public void eliminartransporte(Integer id) {
        repo.deleteById(id);
    }
}
