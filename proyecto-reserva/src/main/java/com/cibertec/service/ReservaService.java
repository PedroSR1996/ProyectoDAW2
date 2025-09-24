package com.cibertec.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.model.EstadoMesa;
import com.cibertec.model.Mesa;
import com.cibertec.model.Reserva;
import com.cibertec.rabbit.MensajeReserva;
import com.cibertec.repository.IMesaRepository;
import com.cibertec.repository.IReservaRepository;

@Service
public class ReservaService {
	
	@Autowired
    private IReservaRepository repo;
	
	
	@Autowired
    private IMesaRepository repoMesa;
	
	@Autowired
    private MensajeReserva mensajeReserva;
	
	

    public List<Reserva> listarReserva() {
        return repo.findAll();
        
     
    }
    
    
    /*public Reserva guardarReserva(Reserva reserva) {
        return repo.save(reserva);
    }*/
    
    public Reserva guardarReserva(Reserva reserva) {
        Mesa mesa = repoMesa.findById(reserva.getMesa().getIdMesa())
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada"));
        if (!mesa.getEstadoMesa().getIdEstMesa().equals(1)) {
            throw new RuntimeException("La mesa no está disponible");
        }
        reserva.setMesa(mesa);
        Reserva nuevaReserva = repo.save(reserva);
        EstadoMesa ocupado = new EstadoMesa();
        ocupado.setIdEstMesa(2);  
        mesa.setEstadoMesa(ocupado);
        repoMesa.save(mesa);

        return nuevaReserva;
    }



    public Reserva obtenerReservaPorId(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public void eliminarReserva(Integer id) {
        repo.deleteById(id);
    }
    
    
    
    
    public long contarReservas() {
        return repo.count();
    }
    
  //Para enviar todas las reservas a Rabbit
    public void enviarTodasLasReservas() {
        List<Reserva> reservas = repo.findAll();
        for (Reserva reserva : reservas) {
            mensajeReserva.enviarReserva(reserva);
        }
    }

    
    
}


