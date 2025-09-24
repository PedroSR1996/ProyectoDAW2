package com.cibertec.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.feign.ReservaDTO;
import com.cibertec.feign.ReservaFeign;

import com.cibertec.model.Transporte;
import com.cibertec.service.TransporteService;

import feign.FeignException;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/transporte")
public class TransporteController {

    @Autowired
    private TransporteService transporteService;

    @Autowired
    private ReservaFeign reservaFeign;

    @GetMapping
    public ResponseEntity<?> listarTransportes() {
        List<Transporte> lista = transporteService.listarTransporte();
        if (lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay transportes registrados.");
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerTransporte(@PathVariable Integer id) {
        Transporte transporte = transporteService.obtenerTransportePorId(id);
        if (transporte != null) {
            return ResponseEntity.ok(transporte);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Transporte con ID " + id + " no encontrado.");
    }

    
    @PostMapping
    public ResponseEntity<?> registrarTransporte(@RequestBody Transporte transporte) {
        try {
            // Verificar que exista la reserva
            ReservaDTO reserva = reservaFeign.obReservaDTO(transporte.getIdReserva());

            Transporte nuevo = transporteService.guardarTransporte(transporte);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Transporte registrado correctamente con ID: " + nuevo.getIdTransporte());
        } catch (FeignException.NotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("La reserva con ID " + transporte.getIdReserva() + " no existe.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar el transporte.");
        }
    }
    
    
    

    @PutMapping
    public ResponseEntity<?> actualizarTransporte(@RequestBody Transporte transporte) {
        Transporte existente = transporteService.obtenerTransportePorId(transporte.getIdTransporte());

        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Transporte no encontrado para actualizar.");
        }

        try {
            transporteService.guardarTransporte(transporte);
            return ResponseEntity.ok("Transporte actualizado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el transporte.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTransporte(@PathVariable Integer id) {
        Transporte existente = transporteService.obtenerTransportePorId(id);

        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Transporte con ID " + id + " no encontrado");
        }

        try {
            transporteService.eliminartransporte(id);
            return ResponseEntity.ok("Transporte eliminado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el transporte con ID " + id );
        }
    }
    
    
    
    @GetMapping("/test-feign/{id}")
    public ResponseEntity<?> testFeign(@PathVariable Integer id) {
        try {
            System.out.println("Consultando reserva con ID: " + id);
            ReservaDTO reserva = reservaFeign.obReservaDTO(id);
            System.out.println("Reserva obtenida: " + reserva);
            return ResponseEntity.ok(reserva);
        } catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al llamar al servicio reserva: " + e.getMessage());
        }
    }
    
    
    
}