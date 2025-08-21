package com.vacinici.backend.controller;

import com.vacinici.backend.entity.Agendamento;
import com.vacinici.backend.entity.Usuario;
import com.vacinici.backend.entity.Vacina;
import com.vacinici.backend.entity.LocalVacinacao;
import com.vacinici.backend.repository.AgendamentoRepository;
import com.vacinici.backend.repository.UsuarioRepository;
import com.vacinici.backend.repository.VacinaRepository;
import com.vacinici.backend.repository.LocalVacinacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/agendamentos")
@CrossOrigin(origins = "*")
public class AgendamentoController {

    @Autowired
    private AgendamentoRepository agendamentoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private VacinaRepository vacinaRepository;
    
    @Autowired
    private LocalVacinacaoRepository localRepository;

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllAgendamentos() {
        List<Agendamento> agendamentos = agendamentoRepository.findAllOrderByDataAgendamentoDesc();
        List<Map<String, Object>> response = agendamentos.stream().map(this::mapToResponse).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<Map<String, Object>>> getAgendamentosByPaciente(@PathVariable Long pacienteId) {
        List<Agendamento> agendamentos = agendamentoRepository.findByPacienteIdOrderByDataAgendamentoDesc(pacienteId);
        List<Map<String, Object>> response = agendamentos.stream().map(this::mapToResponse).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createAgendamento(@RequestBody Agendamento agendamento) {
        try {
            Agendamento saved = agendamentoRepository.save(agendamento);
            Map<String, Object> response = mapToResponse(saved);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Erro ao criar agendamento: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    private Map<String, Object> mapToResponse(Agendamento agendamento) {
        Map<String, Object> response = new HashMap<>();
        response.put("id", agendamento.getId());
        response.put("pacienteId", agendamento.getPacienteId());
        response.put("vacinaId", agendamento.getVacinaId());
        response.put("localId", agendamento.getLocalId());
        response.put("dataAgendamento", agendamento.getDataAgendamento());
        response.put("status", agendamento.getStatus());
        response.put("dataCriacao", agendamento.getDataCriacao());
        
        // Buscar nomes relacionados
        usuarioRepository.findById(agendamento.getPacienteId()).ifPresent(u -> 
            response.put("nomePaciente", u.getNomeCompleto()));
        vacinaRepository.findById(agendamento.getVacinaId()).ifPresent(v -> 
            response.put("nomeVacina", v.getNome()));
        localRepository.findById(agendamento.getLocalId()).ifPresent(l -> 
            response.put("nomeLocal", l.getNome()));
        
        return response;
    }
}