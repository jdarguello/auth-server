package com.BancoC.server.auth.modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "scope")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Scope {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer scopeId;
    
    @Column(nullable = false)
    private String appName;
    @Column(nullable = false)
    private String owner;
}
