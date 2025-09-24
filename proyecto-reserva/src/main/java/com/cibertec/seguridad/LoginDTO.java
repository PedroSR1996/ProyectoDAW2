package com.cibertec.seguridad;


import lombok.Data;

@Data
public class LoginDTO {
    private String correo;
    private String contrasena;
}