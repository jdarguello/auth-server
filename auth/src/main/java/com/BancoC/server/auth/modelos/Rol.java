package com.BancoC.server.auth.modelos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rol")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Rol implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long rolId;
    
    @Column(nullable = false)
    private String nombre;

    @ManyToMany
    @JoinTable(
        name = "permisos_rol",
        joinColumns = @JoinColumn(name = "rolId"),
        inverseJoinColumns = @JoinColumn(name = "permisoId")
    )
    @Builder.Default
    private Collection<Permiso> permisos = new ArrayList<>();

}
