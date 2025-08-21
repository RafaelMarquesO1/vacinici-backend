package com.vacinici.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "historico_vacinacao")
public class HistoricoVacinacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Usuario paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funcionario_id", nullable = false)
    private Usuario funcionario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vacina_id", nullable = false)
    private Vacina vacina;

    @NotBlank(message = "Dose é obrigatória")
    @Size(max = 50, message = "Dose deve ter no máximo 50 caracteres")
    @Column(nullable = false, length = 50)
    private String dose;

    @NotNull(message = "Data de aplicação é obrigatória")
    @Column(name = "data_aplicacao", nullable = false)
    private LocalDate dataAplicacao;

    @NotBlank(message = "Lote é obrigatório")
    @Size(max = 50, message = "Lote deve ter no máximo 50 caracteres")
    @Column(nullable = false, length = 50)
    private String lote;

    private LocalDate validade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "local_id")
    private LocalVacinacao local;

    @Size(max = 255, message = "URL do comprovante deve ter no máximo 255 caracteres")
    @Column(name = "comprovante_url", length = 255)
    private String comprovanteUrl;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String observacoes;

    // Constructors
    public HistoricoVacinacao() {}

    public HistoricoVacinacao(Usuario paciente, Usuario funcionario, Vacina vacina, String dose, LocalDate dataAplicacao, String lote) {
        this.paciente = paciente;
        this.funcionario = funcionario;
        this.vacina = vacina;
        this.dose = dose;
        this.dataAplicacao = dataAplicacao;
        this.lote = lote;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Usuario getPaciente() { return paciente; }
    public void setPaciente(Usuario paciente) { this.paciente = paciente; }

    public Usuario getFuncionario() { return funcionario; }
    public void setFuncionario(Usuario funcionario) { this.funcionario = funcionario; }

    public Vacina getVacina() { return vacina; }
    public void setVacina(Vacina vacina) { this.vacina = vacina; }

    public String getDose() { return dose; }
    public void setDose(String dose) { this.dose = dose; }

    public LocalDate getDataAplicacao() { return dataAplicacao; }
    public void setDataAplicacao(LocalDate dataAplicacao) { this.dataAplicacao = dataAplicacao; }

    public String getLote() { return lote; }
    public void setLote(String lote) { this.lote = lote; }

    public LocalDate getValidade() { return validade; }
    public void setValidade(LocalDate validade) { this.validade = validade; }

    public LocalVacinacao getLocal() { return local; }
    public void setLocal(LocalVacinacao local) { this.local = local; }

    public String getComprovanteUrl() { return comprovanteUrl; }
    public void setComprovanteUrl(String comprovanteUrl) { this.comprovanteUrl = comprovanteUrl; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
}