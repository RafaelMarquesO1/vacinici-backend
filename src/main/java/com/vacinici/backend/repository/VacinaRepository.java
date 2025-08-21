package com.vacinici.backend.repository;

import com.vacinici.backend.entity.Vacina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacinaRepository extends JpaRepository<Vacina, Long> {
    List<Vacina> findByCategoria(String categoria);
    
    @Query("SELECT v FROM Vacina v WHERE v.nome LIKE %:nome%")
    List<Vacina> findByNomeContaining(@Param("nome") String nome);
    
    @Query("SELECT v FROM Vacina v WHERE v.fabricante LIKE %:fabricante%")
    List<Vacina> findByFabricanteContaining(@Param("fabricante") String fabricante);
    
    @Query("SELECT v FROM Vacina v WHERE v.idadeMinima <= :idade AND (v.idadeMaxima IS NULL OR v.idadeMaxima >= :idade)")
    List<Vacina> findByIdadeAplicavel(@Param("idade") Integer idade);
}