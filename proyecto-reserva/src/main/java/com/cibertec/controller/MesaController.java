package com.cibertec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.model.Mesa;
import com.cibertec.service.MesaService;

@RestController
@RequestMapping("/api/mesa")
public class MesaController {

    @Autowired
    private MesaService mesaService;

    @GetMapping
    public ResponseEntity<?> listarMesas() {
        List<Mesa> lista = mesaService.listarMesas();
        if (lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay mesas registradas.");
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerMesa(@PathVariable Integer id) {
        Mesa mesa = mesaService.obtenerMesaPorId(id);
        if (mesa != null) {
            return ResponseEntity.ok(mesa);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mesa con ID " + id + " no encontrada.");
    }

    @PostMapping
    public ResponseEntity<?> registrarMesa(@RequestBody Mesa mesa) {
        try {
            Mesa nueva = mesaService.guardarMesa(mesa);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Mesa registrada exitosamente con ID: " + nueva.getIdMesa());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar la mesa.");
        }
    }

    @PutMapping
    public ResponseEntity<?> actualizarMesa(@RequestBody Mesa mesa) {
        Mesa mesaExistente = mesaService.obtenerMesaPorId(mesa.getIdMesa());

        if (mesaExistente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mesa no encontrada para actualizar.");
        }

        try {
            mesaService.guardarMesa(mesa);
            return ResponseEntity.ok("Mesa actualizada exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar la mesa.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarMesa(@PathVariable Integer id) {
        Mesa mesaExistente = mesaService.obtenerMesaPorId(id);

        if (mesaExistente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mesa con ID " + id + " no encontrada.");
        }

        try {
            mesaService.eliminarMesa(id);
            return ResponseEntity.ok("Mesa eliminada correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar la mesa con ID " + id + ".");
        }
    }

    @GetMapping("/disponibles")
    public ResponseEntity<?> listarMesasDisponibles() {
        List<Mesa> lista = mesaService.listarMesasDisponibles();
        if (lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay mesas disponibles.");
        }
        return ResponseEntity.ok(lista);
    }
}