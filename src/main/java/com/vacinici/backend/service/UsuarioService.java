package com.vacinici.backend.service;

import com.vacinici.backend.entity.Usuario;
import com.vacinici.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public List<Usuario> findByTipoUsuario(String tipoUsuario) {
        return usuarioRepository.findByTipoUsuario(tipoUsuario);
    }

    public List<Usuario> findAllPacientes() {
        return usuarioRepository.findAllPacientes();
    }

    public List<Usuario> findAllFuncionarios() {
        return usuarioRepository.findAllFuncionarios();
    }

    public Usuario save(Usuario usuario) {
        if (usuario.getSenha() != null && !usuario.getSenha().startsWith("$2a$")) {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario update(Long id, Usuario usuarioAtualizado) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setNomeCompleto(usuarioAtualizado.getNomeCompleto());
                    usuario.setEmail(usuarioAtualizado.getEmail());
                    usuario.setTelefone(usuarioAtualizado.getTelefone());
                    usuario.setDataNascimento(usuarioAtualizado.getDataNascimento());
                    usuario.setGenero(usuarioAtualizado.getGenero());
                    usuario.setCargo(usuarioAtualizado.getCargo());
                    usuario.setFotoPerfil(usuarioAtualizado.getFotoPerfil());
                    
                    if (usuarioAtualizado.getSenha() != null && !usuarioAtualizado.getSenha().isEmpty()) {
                        usuario.setSenha(passwordEncoder.encode(usuarioAtualizado.getSenha()));
                    }
                    
                    return usuarioRepository.save(usuario);
                })
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + id));
    }

    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public boolean existsByCpf(String cpf) {
        return usuarioRepository.existsByCpf(cpf);
    }

    public boolean validatePassword(String rawPassword, String encodedPassword) {
        // Se a senha no banco não está criptografada, compara diretamente
        if (!encodedPassword.startsWith("$2a$")) {
            return rawPassword.equals(encodedPassword);
        }
        // Se está criptografada, usa o encoder
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}