package com.vacinici.backend.repository;

import com.vacinici.backend.entity.HorarioDisponivel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HorarioDisponivelRepository extends JpaRepository<HorarioDisponivel, Long> {
    List<HorarioDisponivel> findByLocalIdAndDisponivelTrue(Long localId);
}
