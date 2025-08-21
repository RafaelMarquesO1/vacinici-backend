package com.vacinici.backend.controller;

import com.vacinici.backend.dto.LoginRequest;
import com.vacinici.backend.dto.LoginResponse;
import com.vacinici.backend.entity.Usuario;
import com.vacinici.backend.security.JwtUtil;
import com.vacinici.backend.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        return performLogin(loginRequest, true); // Apenas funcionários
    }

    @PostMapping("/mobile-login")
    public ResponseEntity<?> mobileLogin(@Valid @RequestBody LoginRequest loginRequest) {
        return performLogin(loginRequest, false); // Todos os usuários
    }

    private ResponseEntity<?> performLogin(LoginRequest loginRequest, boolean onlyEmployees) {
        try {
            Optional<Usuario> usuarioOpt = usuarioService.findByEmail(loginRequest.getEmail());
            
            if (usuarioOpt.isEmpty()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Usuário não encontrado!");
                return ResponseEntity.badRequest().body(response);
            }

            Usuario usuario = usuarioOpt.get();
            
            // Verifica se é funcionário (apenas para login web)
            if (onlyEmployees && !"Funcionario".equals(usuario.getTipoUsuario())) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Acesso negado. Apenas funcionários podem acessar o sistema.");
                return ResponseEntity.badRequest().body(response);
            }

            if (!usuarioService.validatePassword(loginRequest.getSenha(), usuario.getSenha())) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Usuário não encontrado!");
                return ResponseEntity.badRequest().body(response);
            }

            String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getId(), usuario.getTipoUsuario());
            
            LoginResponse loginResponse = new LoginResponse(
                token,
                usuario.getId(),
                usuario.getEmail(),
                usuario.getNomeCompleto(),
                usuario.getTipoUsuario(),
                usuario.getCargo()
            );

            return ResponseEntity.ok(loginResponse);
            
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Erro interno do servidor: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody Usuario usuario) {
        try {
            if (usuarioService.existsByEmail(usuario.getEmail())) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Email já cadastrado");
                return ResponseEntity.badRequest().body(response);
            }

            if (usuarioService.existsByCpf(usuario.getCpf())) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "CPF já cadastrado");
                return ResponseEntity.badRequest().body(response);
            }

            Usuario novoUsuario = usuarioService.save(usuario);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Usuário cadastrado com sucesso");
            response.put("id", novoUsuario.getId());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Erro ao cadastrar usuário: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}