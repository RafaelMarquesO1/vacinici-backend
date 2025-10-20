package com.vacinici.backend.repository;

import com.vacinici.backend.entity.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    
    @Query("SELECT a FROM Agendamento a WHERE a.pacienteId = :pacienteId ORDER BY a.dataAgendamento DESC")
    List<Agendamento> findByPacienteIdOrderByDataAgendamentoDesc(@Param("pacienteId") Long pacienteId);
    
    @Query("SELECT a FROM Agendamento a ORDER BY a.dataAgendamento DESC")
    List<Agendamento> findAllOrderByDataAgendamentoDesc();
    
    boolean existsByLocalIdAndDataAgendamento(Long localId, LocalDateTime dataAgendamento);
}