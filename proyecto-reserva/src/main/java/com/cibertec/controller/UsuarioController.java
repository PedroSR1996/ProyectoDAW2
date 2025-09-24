package com.cibertec.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;

import com.cibertec.seguridad.LoginDTO;
import com.cibertec.seguridad.jwtUtils;

import com.cibertec.model.Usuario;
import com.cibertec.repository.IUsuarioRepository;
import com.cibertec.service.UsuarioService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private  IUsuarioRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private jwtUtils jwtUtils;
    
    

    // 
   /* @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) {
        Usuario usuario = usuarioService.obtenerPorCorreo(dto.getCorreo());
        if (usuario == null || !encoder.matches(dto.getContrasena(), usuario.getContrasena())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }

        String token = jwtUtils.generateToken(usuario.getCorreo());
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }*/
    /*
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) {
        Usuario usuario = usuarioService.obtenerPorCorreo(dto.getCorreo());
        
        if (usuario == null || !encoder.matches(dto.getContrasena(), usuario.getContrasena())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }

        String token = jwtUtils.generateToken(usuario.getCorreo());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("nombre", usuario.getNombre());
        response.put("rol", usuario.getRol().name());
        response.put("idUsuario", usuario.getIdUsuario());

        return ResponseEntity.ok(response);
    }
    
    */
    
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) {
        Usuario usuario = userRepo.findByCorreo(dto.getCorreo())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!encoder.matches(dto.getContrasena(), usuario.getContrasena())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }

        String token = jwtUtils.generateToken(usuario.getCorreo(), usuario.getRol().name());

        Map<String, Object> response = new HashMap<>();
        response.put("idUsuario", usuario.getIdUsuario());
        response.put("nombre", usuario.getNombre());
        response.put("rol", usuario.getRol().name());
        response.put("token", token);

        return ResponseEntity.ok(response);
    }

    // 
    
    /*@PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody Usuario usuario) {
        if (usuarioService.existeCorreo(usuario.getCorreo())) {
            return ResponseEntity.badRequest().body("Correo ya registrado");
        }

        usuario.setContrasena(encoder.encode(usuario.getContrasena()));
        Usuario nuevo = usuarioService.guardarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Usuario registrado con ID: " + nuevo.getIdUsuario());
    }*/
    
    
    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody Usuario usuario) {
        if (usuarioService.existeCorreo(usuario.getCorreo())) {
            return ResponseEntity.badRequest().body("Correo ya registrado");
        }

        usuario.setContrasena(encoder.encode(usuario.getContrasena()));
        Usuario nuevo = usuarioService.guardarUsuario(usuario);

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Usuario registrado correctamente.");
        respuesta.put("id", nuevo.getIdUsuario());

        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }
    


    @GetMapping
    public ResponseEntity<?> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        if (usuarios.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay usuarios registrados");
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerUsuario(@PathVariable Integer id) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario con ID " + id + " no encontrado");
    }

    @PutMapping
    public ResponseEntity<?> actualizarUsuario(@RequestBody Usuario usuario) {
        Usuario usuarioExistente = usuarioService.obtenerUsuarioPorId(usuario.getIdUsuario());

        if (usuarioExistente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado para actualizar");
        }

        try {
            usuario.setContrasena(encoder.encode(usuario.getContrasena())); 
            usuarioService.guardarUsuario(usuario);
            return ResponseEntity.ok("Usuario actualizado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el usuario");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Integer id) {
        Usuario usuarioExistente = usuarioService.obtenerUsuarioPorId(id);

        if (usuarioExistente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario con ID " + id + " no encontrado");
        }

        try {
            usuarioService.eliminarUsuario(id);
            return ResponseEntity.ok("Usuario eliminado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el usuario con ID " + id);
        }
    }
}
