package com.vacinici.backend.repository;

import com.vacinici.backend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByCpf(String cpf);
    List<Usuario> findByTipoUsuario(String tipoUsuario);
    
    @Query("SELECT u FROM Usuario u WHERE u.tipoUsuario = 'Paciente'")
    List<Usuario> findAllPacientes();
    
    @Query("SELECT u FROM Usuario u WHERE u.tipoUsuario = 'Funcionario'")
    List<Usuario> findAllFuncionarios();
    
    @Query("SELECT u FROM Usuario u WHERE u.nomeCompleto LIKE %:nome%")
    List<Usuario> findByNomeContaining(@Param("nome") String nome);
    
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
}