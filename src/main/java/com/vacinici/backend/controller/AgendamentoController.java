package com.vacinici.backend.controller;

import com.vacinici.backend.entity.Agendamento;
import com.vacinici.backend.entity.Usuario;
import com.vacinici.backend.entity.Vacina;
import com.vacinici.backend.entity.LocalVacinacao;
import com.vacinici.backend.repository.AgendamentoRepository;
import com.vacinici.backend.repository.UsuarioRepository;
import com.vacinici.backend.repository.VacinaRepository;
import com.vacinici.backend.repository.LocalVacinacaoRepository;
import com.vacinici.backend.entity.HistoricoVacinacao;
import com.vacinici.backend.repository.HistoricoVacinacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    
    @Autowired
    private HistoricoVacinacaoRepository historicoRepository;

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

    @PutMapping("/{id}/status")
    public ResponseEntity<Map<String, Object>> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> statusData) {
        try {
            Optional<Agendamento> agendamentoOpt = agendamentoRepository.findById(id);
            if (agendamentoOpt.isEmpty()) {
                Map<String, Object> error = new HashMap<>();
                error.put("success", false);
                error.put("message", "Agendamento não encontrado");
                return ResponseEntity.notFound().build();
            }
            
            Agendamento agendamento = agendamentoOpt.get();
            agendamento.setStatus(statusData.get("status"));
            
            // Se o status for "Cancelado", salvar o motivo
            if ("Cancelado".equals(statusData.get("status")) && statusData.containsKey("motivoCancelamento")) {
                agendamento.setMotivoCancelamento(statusData.get("motivoCancelamento"));
            }
            
            Agendamento saved = agendamentoRepository.save(agendamento);
            
            Map<String, Object> response = mapToResponse(saved);
            response.put("success", true);
            response.put("message", "Status atualizado com sucesso");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Erro ao atualizar status: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("/{id}/aplicar")
    public ResponseEntity<Map<String, Object>> aplicarVacina(@PathVariable Long id, @RequestBody Map<String, Object> dadosAplicacao) {
        try {
            Optional<Agendamento> agendamentoOpt = agendamentoRepository.findById(id);
            if (agendamentoOpt.isEmpty()) {
                Map<String, Object> error = new HashMap<>();
                error.put("success", false);
                error.put("message", "Agendamento não encontrado");
                return ResponseEntity.notFound().build();
            }
            
            Agendamento agendamento = agendamentoOpt.get();
            
            // Criar histórico de vacinação
            HistoricoVacinacao historico = new HistoricoVacinacao();
            
            Usuario paciente = usuarioRepository.findById(agendamento.getPacienteId()).orElseThrow();
            Usuario funcionario = usuarioRepository.findById(Long.valueOf(dadosAplicacao.get("funcionarioId").toString())).orElseThrow();
            Vacina vacina = vacinaRepository.findById(agendamento.getVacinaId()).orElseThrow();
            LocalVacinacao local = localRepository.findById(agendamento.getLocalId()).orElse(null);
            
            historico.setPaciente(paciente);
            historico.setFuncionario(funcionario);
            historico.setVacina(vacina);
            historico.setLocal(local);
            historico.setDose(dadosAplicacao.get("dose").toString());
            historico.setDataAplicacao(LocalDate.now());
            historico.setLote(dadosAplicacao.get("lote").toString());
            
            if (dadosAplicacao.containsKey("validade")) {
                historico.setValidade(LocalDate.parse(dadosAplicacao.get("validade").toString()));
            }
            if (dadosAplicacao.containsKey("observacoes")) {
                historico.setObservacoes(dadosAplicacao.get("observacoes").toString());
            }
            
            historicoRepository.save(historico);
            
            // Atualizar status do agendamento
            agendamento.setStatus("Aplicado");
            agendamentoRepository.save(agendamento);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Vacina aplicada com sucesso");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Erro ao aplicar vacina: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteAgendamento(@PathVariable Long id) {
        try {
            if (!agendamentoRepository.existsById(id)) {
                Map<String, Object> error = new HashMap<>();
                error.put("success", false);
                error.put("message", "Agendamento não encontrado");
                return ResponseEntity.notFound().build();
            }
            
            agendamentoRepository.deleteById(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Agendamento cancelado com sucesso");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Erro ao cancelar agendamento: " + e.getMessage());
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
        response.put("motivoCancelamento", agendamento.getMotivoCancelamento());
        
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