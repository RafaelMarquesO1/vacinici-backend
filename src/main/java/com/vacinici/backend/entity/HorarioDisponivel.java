package com.vacinici.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "horarios_disponiveis")
public class HorarioDisponivel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "local_id")
    private Long localId;

    @Column(name = "data")
    private java.sql.Date data;

    @Column(name = "hora")
    private String hora;

    @Column(name = "disponivel")
    private Boolean disponivel;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getLocalId() { return localId; }
    public void setLocalId(Long localId) { this.localId = localId; }

    public java.sql.Date getData() { return data; }
    public void setData(java.sql.Date data) { this.data = data; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    public Boolean getDisponivel() { return disponivel; }
    public void setDisponivel(Boolean disponivel) { this.disponivel = disponivel; }
}
