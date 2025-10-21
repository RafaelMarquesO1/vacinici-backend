package com.vacinici.backend.controller;

import com.vacinici.backend.entity.Usuario;
import com.vacinici.backend.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.findAll();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.findById(id);
        return usuario.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tipo/{tipoUsuario}")
    public ResponseEntity<List<Usuario>> getUsuariosByTipo(@PathVariable String tipoUsuario) {
        List<Usuario> usuarios = usuarioService.findByTipoUsuario(tipoUsuario);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/pacientes")
    public ResponseEntity<List<Usuario>> getAllPacientes() {
        List<Usuario> pacientes = usuarioService.findAllPacientes();
        return ResponseEntity.ok(pacientes);
    }

    @GetMapping("/funcionarios")
    public ResponseEntity<List<Usuario>> getAllFuncionarios() {
        List<Usuario> funcionarios = usuarioService.findAllFuncionarios();
        return ResponseEntity.ok(funcionarios);
    }
    
    @GetMapping("/{id}/dados-publicos")
    public ResponseEntity<Usuario> getDadosPublicos(@PathVariable Long id, @RequestParam(required = false) Integer nivelSolicitante) {
        Optional<Usuario> usuarioOpt = usuarioService.findById(id);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Usuario usuario = usuarioOpt.get();
        
        // Se o solicitante for enfermeiro (nível 3) ou menor, ocultar informações sigilosas
        if (nivelSolicitante != null && nivelSolicitante <= 3 && "Paciente".equals(usuario.getTipoUsuario())) {
            Usuario usuarioPublico = new Usuario();
            usuarioPublico.setId(usuario.getId());
            usuarioPublico.setNomeCompleto(usuario.getNomeCompleto());
            usuarioPublico.setTipoUsuario(usuario.getTipoUsuario());
            usuarioPublico.setFotoPerfil(usuario.getFotoPerfil());
            // Não incluir CPF, email, telefone, data de nascimento para enfermeiros
            return ResponseEntity.ok(usuarioPublico);
        }
        
        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@Valid @RequestBody Usuario usuario) {
        try {
            if (usuarioService.existsByEmail(usuario.getEmail())) {
                return ResponseEntity.badRequest().build();
            }
            if (usuarioService.existsByCpf(usuario.getCpf())) {
                return ResponseEntity.badRequest().build();
            }
            
            // Definir nível de permissão baseado no tipo e cargo
            if ("Paciente".equals(usuario.getTipoUsuario())) {
                usuario.setNivelPermissao(1);
                usuario.setCargo(null);
            } else if ("Funcionario".equals(usuario.getTipoUsuario())) {
                switch (usuario.getCargo()) {
                    case "Técnico em Enfermagem":
                        usuario.setNivelPermissao(2);
                        break;
                    case "Enfermeiro":
                        usuario.setNivelPermissao(3);
                        break;
                    case "Médico":
                        usuario.setNivelPermissao(4);
                        break;
                    default:
                        usuario.setNivelPermissao(2);
                }
            } else if ("Administrador".equals(usuario.getTipoUsuario())) {
                usuario.setNivelPermissao(5);
                usuario.setCargo("Administrador");
            }
            
            Usuario novoUsuario = usuarioService.save(usuario);
            return ResponseEntity.ok(novoUsuario);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @Valid @RequestBody Usuario usuario) {
        try {
            Usuario usuarioAtualizado = usuarioService.update(id, usuario);
            return ResponseEntity.ok(usuarioAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        try {
            usuarioService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}