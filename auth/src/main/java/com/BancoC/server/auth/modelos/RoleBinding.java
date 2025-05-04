package com.BancoC.server.auth.modelos;

import java.io.Serializable;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode.Exclude;

@Entity
@Table(name = "role_binding")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleBinding implements GrantedAuthority{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roleBindingId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "userId", nullable = false)
    @JsonIgnore
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "rolId", nullable = false)
    private Rol rol;

    @ManyToOne
    @JoinColumn(name = "scopeId", nullable = false)
    private Scope scope;

    @Override
    public String getAuthority() {
        return this.rol.getNombre();
    }
    
}
