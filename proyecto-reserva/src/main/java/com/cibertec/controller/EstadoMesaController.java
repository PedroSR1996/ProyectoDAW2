package com.cibertec.controller;

import java.util.List;

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

import com.cibertec.model.EstadoMesa;
import com.cibertec.service.EstadoMesaService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/estadomesa")
public class EstadoMesaController {

    @Autowired
    private EstadoMesaService estadoMesaService;

    @GetMapping
    public ResponseEntity<?> listarEstadoMesas() {
        List<EstadoMesa> lista = estadoMesaService.listarEstadoMesas();
        if (lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("No hay registros de Estados de Mesa.");
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerEstadoMesa(@PathVariable Integer id) {
        EstadoMesa estado = estadoMesaService.obtenerEstadoMesaPorId(id);
        if (estado != null) {
            return ResponseEntity.ok(estado);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Estado de Mesa con ID " + id + " no encontrado.");
    }

    @PostMapping
    public ResponseEntity<?> registrarEstadoMesa(@RequestBody EstadoMesa estadoMesa) {
        try {
            EstadoMesa creado = estadoMesaService.guardarEstadoMesa(estadoMesa);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Estado de Mesa registrado exitosamente con ID: " + creado.getIdEstMesa());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar Estado de Mesa.");
        }
    }

    @PutMapping
    public ResponseEntity<?> actualizarEstadoMesa(@RequestBody EstadoMesa estadoMesa) {
        EstadoMesa existente = estadoMesaService.obtenerEstadoMesaPorId(estadoMesa.getIdEstMesa());

        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Estado de Mesa no encontrado para actualizar.");
        }

        try {
            estadoMesaService.guardarEstadoMesa(estadoMesa);
            return ResponseEntity.ok("Estado de Mesa actualizado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar Estado de Mesa.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarEstadoMesa(@PathVariable Integer id) {
        EstadoMesa existente = estadoMesaService.obtenerEstadoMesaPorId(id);

        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Estado de Mesa con ID " + id + " no encontrado.");
        }

        try {
            estadoMesaService.eliminarEstadoMesa(id);
            return ResponseEntity.ok("Estado de Mesa eliminado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar Estado de Mesa con ID " + id + ".");
        }
    }
}