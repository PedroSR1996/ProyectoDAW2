package com.cibertec.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.service.MesaService;
import com.cibertec.service.ReservaService;
import com.cibertec.service.UsuarioService;

@RestController
@RequestMapping("/api/contador")
@CrossOrigin(origins = "*")
public class ContadorController {

	
	@Autowired
    private ReservaService reservaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MesaService mesaService;
    
    @GetMapping("/totales")
    public ResponseEntity<Map<String, Long>> obtenerTotales() {
        Map<String, Long> datos = new HashMap<>();
        datos.put("reservas", reservaService.contarReservas());
        datos.put("usuarios", usuarioService.contarUsuarios());
        datos.put("mesasDisponibles", mesaService.contarMesasDisponibles());

        return ResponseEntity.ok(datos);
    }
}
