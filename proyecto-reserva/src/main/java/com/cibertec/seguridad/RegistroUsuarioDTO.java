package com.cibertec.seguridad;

import lombok.Data;

@Data
public class RegistroUsuarioDTO {

    private String nombre;
    private String correo;
    private String contrasena;
    private String telefono;
}