package com.vacinici.backend.dto;

public class LoginResponse {
    private String token;
    private Long id;
    private String email;
    private String nomeCompleto;
    private String tipoUsuario;
    private String cargo;
    private Integer nivelPermissao;

    // Constructors
    public LoginResponse() {}

    public LoginResponse(String token, Long id, String email, String nomeCompleto, String tipoUsuario, String cargo, Integer nivelPermissao) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.nomeCompleto = nomeCompleto;
        this.tipoUsuario = tipoUsuario;
        this.cargo = cargo;
        this.nivelPermissao = nivelPermissao;
    }

    // Getters and Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }

    public String getTipoUsuario() { return tipoUsuario; }
    public void setTipoUsuario(String tipoUsuario) { this.tipoUsuario = tipoUsuario; }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public Integer getNivelPermissao() { return nivelPermissao; }
    public void setNivelPermissao(Integer nivelPermissao) { this.nivelPermissao = nivelPermissao; }
}