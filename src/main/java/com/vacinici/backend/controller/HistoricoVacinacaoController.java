package com.vacinici.backend.controller;

import com.vacinici.backend.dto.HistoricoVacinacaoDTO;
import com.vacinici.backend.entity.HistoricoVacinacao;
import com.vacinici.backend.entity.LocalVacinacao;
import com.vacinici.backend.entity.Usuario;
import com.vacinici.backend.entity.Vacina;
import com.vacinici.backend.service.HistoricoVacinacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/historico")
@CrossOrigin(origins = "*")
public class HistoricoVacinacaoController {

    @Autowired
    private HistoricoVacinacaoService historicoService;

    @GetMapping
    public ResponseEntity<List<HistoricoVacinacaoDTO>> getAllHistorico() {
        List<HistoricoVacinacaoDTO> historico = historicoService.findAll();
        return ResponseEntity.ok(historico);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoricoVacinacaoDTO> getHistoricoById(@PathVariable Long id) {
        Optional<HistoricoVacinacaoDTO> historico = historicoService.findById(id);
        return historico.map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<HistoricoVacinacaoDTO>> getHistoricoByPaciente(@PathVariable Long pacienteId) {
        List<HistoricoVacinacaoDTO> historico = historicoService.findByPacienteId(pacienteId);
        return ResponseEntity.ok(historico);
    }

    @GetMapping("/vacina/{vacinaId}")
    public ResponseEntity<List<HistoricoVacinacaoDTO>> getHistoricoByVacina(@PathVariable Long vacinaId) {
        List<HistoricoVacinacaoDTO> historico = historicoService.findByVacinaId(vacinaId);
        return ResponseEntity.ok(historico);
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<HistoricoVacinacaoDTO>> getHistoricoByPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        List<HistoricoVacinacaoDTO> historico = historicoService.findByDataAplicacaoBetween(dataInicio, dataFim);
        return ResponseEntity.ok(historico);
    }

    @PostMapping
    public ResponseEntity<HistoricoVacinacaoDTO> createHistorico(@Valid @RequestBody Map<String, Object> historicoData) {
        try {
            HistoricoVacinacao historico = new HistoricoVacinacao();
            
            // Configurar paciente
            Usuario paciente = new Usuario();
            paciente.setId(Long.valueOf(historicoData.get("pacienteId").toString()));
            historico.setPaciente(paciente);
            
            // Configurar funcionário
            Usuario funcionario = new Usuario();
            funcionario.setId(Long.valueOf(historicoData.get("funcionarioId").toString()));
            historico.setFuncionario(funcionario);
            
            // Configurar vacina
            Vacina vacina = new Vacina();
            vacina.setId(Long.valueOf(historicoData.get("vacinaId").toString()));
            historico.setVacina(vacina);
            
            // Configurar dados básicos
            historico.setDose(historicoData.get("dose").toString());
            historico.setDataAplicacao(LocalDate.parse(historicoData.get("dataAplicacao").toString()));
            historico.setLote(historicoData.get("lote").toString());
            
            // Configurar dados opcionais
            if (historicoData.get("validade") != null) {
                historico.setValidade(LocalDate.parse(historicoData.get("validade").toString()));
            }
            
            if (historicoData.get("localId") != null) {
                LocalVacinacao local = new LocalVacinacao();
                local.setId(Long.valueOf(historicoData.get("localId").toString()));
                historico.setLocal(local);
            }
            
            if (historicoData.get("comprovanteUrl") != null) {
                historico.setComprovanteUrl(historicoData.get("comprovanteUrl").toString());
            }
            
            if (historicoData.get("observacoes") != null) {
                historico.setObservacoes(historicoData.get("observacoes").toString());
            }
            
            HistoricoVacinacaoDTO novoHistorico = historicoService.save(historico);
            return ResponseEntity.ok(novoHistorico);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistoricoVacinacaoDTO> updateHistorico(@PathVariable Long id, @Valid @RequestBody HistoricoVacinacao historico) {
        try {
            HistoricoVacinacaoDTO historicoAtualizado = historicoService.update(id, historico);
            return ResponseEntity.ok(historicoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistorico(@PathVariable Long id) {
        try {
            historicoService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/estatisticas/vacina/{vacinaId}")
    public ResponseEntity<Long> getEstatisticasVacina(@PathVariable Long vacinaId) {
        Long count = historicoService.countByVacinaId(vacinaId);
        return ResponseEntity.ok(count);
    }
}