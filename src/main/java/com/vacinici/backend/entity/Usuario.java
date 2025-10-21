package com.vacinici.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome completo é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    @Column(name = "nome_completo", nullable = false, length = 100)
    private String nomeCompleto;

    @Email(message = "Email deve ser válido")
    @Size(max = 100, message = "Email deve ter no máximo 100 caracteres")
    @Column(unique = true, length = 100)
    private String email;

    @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
    @Column(length = 20)
    private String telefone;

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF deve estar no formato XXX.XXX.XXX-XX")
    @Column(unique = true, nullable = false, length = 20)
    private String cpf;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Size(max = 20, message = "Gênero deve ter no máximo 20 caracteres")
    @Column(length = 20)
    private String genero;

    @NotBlank(message = "Tipo de usuário é obrigatório")
    @Pattern(regexp = "Paciente|Funcionario", message = "Tipo deve ser 'Paciente' ou 'Funcionario'")
    @Column(name = "tipo_usuario", nullable = false, length = 20)
    private String tipoUsuario;

    @Size(max = 50, message = "Cargo deve ter no máximo 50 caracteres")
    @Column(length = 50)
    private String cargo;

    @Column(name = "nivel_permissao")
    private Integer nivelPermissao = 1;

    @Size(max = 255, message = "URL da foto deve ter no máximo 255 caracteres")
    @Column(name = "foto_perfil", length = 255)
    private String fotoPerfil;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
    @Column(nullable = false)
    private String senha;

    @PrePersist
    protected void onCreate() {
        dataCadastro = LocalDateTime.now();
    }

    // Constructors
    public Usuario() {}

    public Usuario(String nomeCompleto, String email, String cpf, String tipoUsuario, String senha) {
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.cpf = cpf;
        this.tipoUsuario = tipoUsuario;
        this.senha = senha;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public String getTipoUsuario() { return tipoUsuario; }
    public void setTipoUsuario(String tipoUsuario) { this.tipoUsuario = tipoUsuario; }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public Integer getNivelPermissao() { return nivelPermissao; }
    public void setNivelPermissao(Integer nivelPermissao) { this.nivelPermissao = nivelPermissao; }

    public String getFotoPerfil() { return fotoPerfil; }
    public void setFotoPerfil(String fotoPerfil) { this.fotoPerfil = fotoPerfil; }

    public LocalDateTime getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(LocalDateTime dataCadastro) { this.dataCadastro = dataCadastro; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}