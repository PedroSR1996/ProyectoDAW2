package com.cibertec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

import com.cibertec.model.Reserva;
import com.cibertec.service.ReservaService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/reserva")
public class ReservaController {

	@Autowired
	private ReservaService reservaService;

	@GetMapping
	public ResponseEntity<?> listarReservas() {
		List<Reserva> reservas = reservaService.listarReserva();
		if (reservas.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay reservas registradas.");
		}
		return ResponseEntity.ok(reservas);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> obtenerReserva(@PathVariable Integer id) {
		Reserva reserva = reservaService.obtenerReservaPorId(id);
		if (reserva != null) {
			return ResponseEntity.ok(reserva);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reserva con ID " + id + " no encontrada.");
	}

	@PostMapping
	public ResponseEntity<?> registrarReserva(@RequestBody Reserva reserva) {
		try {
			Reserva nueva = reservaService.guardarReserva(reserva);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body("Reserva registrada correctamente con ID: " + nueva.getIdReserva());
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar la reserva.");
		}
	}

	@PutMapping
	public ResponseEntity<?> actualizarReserva(@RequestBody Reserva reserva) {
		Reserva noExiste = reservaService.obtenerReservaPorId(reserva.getIdReserva());

		if (noExiste == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reserva no encontrada para actualizar.");
		}

		try {
			reservaService.guardarReserva(reserva);
			return ResponseEntity.ok("Reserva actualizada correctamente");
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la reserva.");
		}
	}
	
	/*

	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminarReserva(@PathVariable Integer id) {
		Reserva reservaExistente = reservaService.obtenerReservaPorId(id);

		if (reservaExistente == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reserva con ID " + id + " no encontrada.");
		}

		try {
			reservaService.eliminarReserva(id);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al eliminar la reserva con ID " + id + ".");
		}
	}
	*/
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminarReserva(@PathVariable Integer id) {
	    Reserva reservaExistente = reservaService.obtenerReservaPorId(id);

	    if (reservaExistente == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body("Reserva con ID " + id + " no encontrada.");
	    }

	    try {
	        reservaService.eliminarReserva(id);
	        return ResponseEntity.noContent().build();

	    } catch (DataIntegrityViolationException e) {
	        return ResponseEntity.status(HttpStatus.CONFLICT)  // Código 409
	                .body("No se puede eliminar la reserva porque tiene transportes asociados.Primero elimine el transporte");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Error al eliminar la reserva con ID " + id + ".");
	    }
	}

	// Envio a RabbitMQ
	@PostMapping("/enviar-todas")
	public ResponseEntity<String> enviarTodasLasReservas() {
		reservaService.enviarTodasLasReservas();
		return ResponseEntity.ok("Todas las reservas fueron enviadas a RabbitMQ");
	}

}