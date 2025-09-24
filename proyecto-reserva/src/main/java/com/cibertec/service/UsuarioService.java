package com.cibertec.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.model.Usuario;
import com.cibertec.repository.IUsuarioRepository;

@Service
public class UsuarioService {
    @Autowired
    
    private IUsuarioRepository repo;

    public List<Usuario> listarUsuarios() {
        return repo.findAll();
    }

    public Usuario guardarUsuario(Usuario usuario) {
        return repo.save(usuario);
    }

    public Usuario obtenerUsuarioPorId(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public void eliminarUsuario(Integer id) {
        repo.deleteById(id);
    }
    
    public Usuario obtenerPorCorreo(String correo) {
        return repo.findByCorreo(correo).orElse(null);
    }
    
    public boolean existeCorreo(String correo) {
        return repo.existsByCorreo(correo);
    }
    
    public long contarUsuarios() {
        return repo.count();
    }
}