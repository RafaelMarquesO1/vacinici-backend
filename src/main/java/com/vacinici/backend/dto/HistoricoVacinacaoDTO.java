package com.vacinici.backend.dto;

import java.time.LocalDate;

public class HistoricoVacinacaoDTO {
    private Long id;
    private Long pacienteId;
    private String nomePaciente;
    private Long funcionarioId;
    private String nomeFuncionario;
    private Long vacinaId;
    private String nomeVacina;
    private String fabricante;
    private String dose;
    private LocalDate dataAplicacao;
    private String lote;
    private LocalDate validade;
    private Long localId;
    private String nomeLocal;
    private String comprovanteUrl;
    private String observacoes;

    // Constructors
    public HistoricoVacinacaoDTO() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPacienteId() { return pacienteId; }
    public void setPacienteId(Long pacienteId) { this.pacienteId = pacienteId; }

    public String getNomePaciente() { return nomePaciente; }
    public void setNomePaciente(String nomePaciente) { this.nomePaciente = nomePaciente; }

    public Long getFuncionarioId() { return funcionarioId; }
    public void setFuncionarioId(Long funcionarioId) { this.funcionarioId = funcionarioId; }

    public String getNomeFuncionario() { return nomeFuncionario; }
    public void setNomeFuncionario(String nomeFuncionario) { this.nomeFuncionario = nomeFuncionario; }

    public Long getVacinaId() { return vacinaId; }
    public void setVacinaId(Long vacinaId) { this.vacinaId = vacinaId; }

    public String getNomeVacina() { return nomeVacina; }
    public void setNomeVacina(String nomeVacina) { this.nomeVacina = nomeVacina; }

    public String getFabricante() { return fabricante; }
    public void setFabricante(String fabricante) { this.fabricante = fabricante; }

    public String getDose() { return dose; }
    public void setDose(String dose) { this.dose = dose; }

    public LocalDate getDataAplicacao() { return dataAplicacao; }
    public void setDataAplicacao(LocalDate dataAplicacao) { this.dataAplicacao = dataAplicacao; }

    public String getLote() { return lote; }
    public void setLote(String lote) { this.lote = lote; }

    public LocalDate getValidade() { return validade; }
    public void setValidade(LocalDate validade) { this.validade = validade; }

    public Long getLocalId() { return localId; }
    public void setLocalId(Long localId) { this.localId = localId; }

    public String getNomeLocal() { return nomeLocal; }
    public void setNomeLocal(String nomeLocal) { this.nomeLocal = nomeLocal; }

    public String getComprovanteUrl() { return comprovanteUrl; }
    public void setComprovanteUrl(String comprovanteUrl) { this.comprovanteUrl = comprovanteUrl; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
}