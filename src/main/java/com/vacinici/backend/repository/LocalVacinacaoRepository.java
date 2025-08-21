package com.vacinici.backend.repository;

import com.vacinici.backend.entity.LocalVacinacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocalVacinacaoRepository extends JpaRepository<LocalVacinacao, Long> {
    List<LocalVacinacao> findByCidade(String cidade);
    List<LocalVacinacao> findByEstado(String estado);
    List<LocalVacinacao> findByTipo(String tipo);
    
    @Query("SELECT l FROM LocalVacinacao l WHERE l.nome LIKE %:nome%")
    List<LocalVacinacao> findByNomeContaining(@Param("nome") String nome);
    
    @Query("SELECT l FROM LocalVacinacao l WHERE l.cidade = :cidade AND l.estado = :estado")
    List<LocalVacinacao> findByCidadeAndEstado(@Param("cidade") String cidade, @Param("estado") String estado);
}