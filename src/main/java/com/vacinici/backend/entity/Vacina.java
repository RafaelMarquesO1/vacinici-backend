package com.vacinici.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "vacinas")
public class Vacina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome da vacina é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nome;

    @Size(max = 100, message = "Fabricante deve ter no máximo 100 caracteres")
    @Column(length = 100)
    private String fabricante;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String descricao;

    @Column(name = "doses_recomendadas")
    private Integer dosesRecomendadas;

    @Column(name = "intervalo_doses")
    private Integer intervaloDoses;

    @Column(name = "idade_minima")
    private Integer idadeMinima;

    @Column(name = "idade_maxima")
    private Integer idadeMaxima;

    @Pattern(regexp = "obrigatória|opcional|sazonal", message = "Categoria deve ser 'obrigatória', 'opcional' ou 'sazonal'")
    @Column(length = 50)
    private String categoria;

    @Size(max = 255, message = "URL da imagem deve ter no máximo 255 caracteres")
    @Column(name = "imagem_url", length = 255)
    private String imagemUrl;

    // Constructors
    public Vacina() {}

    public Vacina(String nome, String fabricante, String descricao, Integer dosesRecomendadas, String categoria) {
        this.nome = nome;
        this.fabricante = fabricante;
        this.descricao = descricao;
        this.dosesRecomendadas = dosesRecomendadas;
        this.categoria = categoria;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getFabricante() { return fabricante; }
    public void setFabricante(String fabricante) { this.fabricante = fabricante; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Integer getDosesRecomendadas() { return dosesRecomendadas; }
    public void setDosesRecomendadas(Integer dosesRecomendadas) { this.dosesRecomendadas = dosesRecomendadas; }

    public Integer getIntervaloDoses() { return intervaloDoses; }
    public void setIntervaloDoses(Integer intervaloDoses) { this.intervaloDoses = intervaloDoses; }

    public Integer getIdadeMinima() { return idadeMinima; }
    public void setIdadeMinima(Integer idadeMinima) { this.idadeMinima = idadeMinima; }

    public Integer getIdadeMaxima() { return idadeMaxima; }
    public void setIdadeMaxima(Integer idadeMaxima) { this.idadeMaxima = idadeMaxima; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getImagemUrl() { return imagemUrl; }
    public void setImagemUrl(String imagemUrl) { this.imagemUrl = imagemUrl; }
}