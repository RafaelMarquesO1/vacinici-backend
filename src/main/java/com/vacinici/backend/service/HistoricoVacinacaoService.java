package com.vacinici.backend.service;

import com.vacinici.backend.dto.HistoricoVacinacaoDTO;
import com.vacinici.backend.entity.HistoricoVacinacao;
import com.vacinici.backend.entity.LocalVacinacao;
import com.vacinici.backend.entity.Usuario;
import com.vacinici.backend.entity.Vacina;
import com.vacinici.backend.repository.HistoricoVacinacaoRepository;
import com.vacinici.backend.repository.LocalVacinacaoRepository;
import com.vacinici.backend.repository.UsuarioRepository;
import com.vacinici.backend.repository.VacinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HistoricoVacinacaoService {

    @Autowired
    private HistoricoVacinacaoRepository historicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private VacinaRepository vacinaRepository;

    @Autowired
    private LocalVacinacaoRepository localRepository;

    public List<HistoricoVacinacaoDTO> findAll() {
        return historicoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<HistoricoVacinacaoDTO> findById(Long id) {
        return historicoRepository.findById(id)
                .map(this::convertToDTO);
    }

    public List<HistoricoVacinacaoDTO> findByPacienteId(Long pacienteId) {
        return historicoRepository.findHistoricoCompletoByPaciente(pacienteId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<HistoricoVacinacaoDTO> findByVacinaId(Long vacinaId) {
        return historicoRepository.findByVacinaId(vacinaId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<HistoricoVacinacaoDTO> findByDataAplicacaoBetween(LocalDate dataInicio, LocalDate dataFim) {
        return historicoRepository.findByDataAplicacaoBetween(dataInicio, dataFim).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public HistoricoVacinacaoDTO save(HistoricoVacinacao historico) {
        // Validar se paciente existe
        Usuario paciente = usuarioRepository.findById(historico.getPaciente().getId())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
        
        // Validar se funcionário existe
        Usuario funcionario = usuarioRepository.findById(historico.getFuncionario().getId())
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
        
        // Validar se vacina existe
        Vacina vacina = vacinaRepository.findById(historico.getVacina().getId())
                .orElseThrow(() -> new RuntimeException("Vacina não encontrada"));
        
        // Validar se local existe (se fornecido)
        if (historico.getLocal() != null && historico.getLocal().getId() != null) {
            LocalVacinacao local = localRepository.findById(historico.getLocal().getId())
                    .orElseThrow(() -> new RuntimeException("Local não encontrado"));
            historico.setLocal(local);
        }

        historico.setPaciente(paciente);
        historico.setFuncionario(funcionario);
        historico.setVacina(vacina);

        HistoricoVacinacao savedHistorico = historicoRepository.save(historico);
        return convertToDTO(savedHistorico);
    }

    public HistoricoVacinacaoDTO update(Long id, HistoricoVacinacao historicoAtualizado) {
        return historicoRepository.findById(id)
                .map(historico -> {
                    historico.setDose(historicoAtualizado.getDose());
                    historico.setDataAplicacao(historicoAtualizado.getDataAplicacao());
                    historico.setLote(historicoAtualizado.getLote());
                    historico.setValidade(historicoAtualizado.getValidade());
                    historico.setComprovanteUrl(historicoAtualizado.getComprovanteUrl());
                    historico.setObservacoes(historicoAtualizado.getObservacoes());
                    
                    if (historicoAtualizado.getLocal() != null && historicoAtualizado.getLocal().getId() != null) {
                        LocalVacinacao local = localRepository.findById(historicoAtualizado.getLocal().getId())
                                .orElseThrow(() -> new RuntimeException("Local não encontrado"));
                        historico.setLocal(local);
                    }
                    
                    return convertToDTO(historicoRepository.save(historico));
                })
                .orElseThrow(() -> new RuntimeException("Histórico não encontrado com id: " + id));
    }

    public void deleteById(Long id) {
        historicoRepository.deleteById(id);
    }

    public Long countByVacinaId(Long vacinaId) {
        return historicoRepository.countByVacinaId(vacinaId);
    }

    private HistoricoVacinacaoDTO convertToDTO(HistoricoVacinacao historico) {
        HistoricoVacinacaoDTO dto = new HistoricoVacinacaoDTO();
        dto.setId(historico.getId());
        dto.setPacienteId(historico.getPaciente().getId());
        dto.setNomePaciente(historico.getPaciente().getNomeCompleto());
        dto.setFuncionarioId(historico.getFuncionario().getId());
        dto.setNomeFuncionario(historico.getFuncionario().getNomeCompleto());
        dto.setVacinaId(historico.getVacina().getId());
        dto.setNomeVacina(historico.getVacina().getNome());
        dto.setFabricante(historico.getVacina().getFabricante());
        dto.setDose(historico.getDose());
        dto.setDataAplicacao(historico.getDataAplicacao());
        dto.setLote(historico.getLote());
        dto.setValidade(historico.getValidade());
        
        if (historico.getLocal() != null) {
            dto.setLocalId(historico.getLocal().getId());
            dto.setNomeLocal(historico.getLocal().getNome());
        }
        
        dto.setComprovanteUrl(historico.getComprovanteUrl());
        dto.setObservacoes(historico.getObservacoes());
        
        return dto;
    }
}