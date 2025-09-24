package com.cibertec.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tb_estado_mesa")
@Data
public class EstadoMesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEstMesa;
    private String descripcion;
}
