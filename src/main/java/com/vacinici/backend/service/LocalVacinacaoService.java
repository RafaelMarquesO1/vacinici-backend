package com.vacinici.backend.service;

import com.vacinici.backend.entity.LocalVacinacao;
import com.vacinici.backend.repository.LocalVacinacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocalVacinacaoService {

    @Autowired
    private LocalVacinacaoRepository localRepository;

    public List<LocalVacinacao> findAll() {
        return localRepository.findAll();
    }

    public Optional<LocalVacinacao> findById(Long id) {
        return localRepository.findById(id);
    }

    public List<LocalVacinacao> findByCidade(String cidade) {
        return localRepository.findByCidade(cidade);
    }

    public List<LocalVacinacao> findByEstado(String estado) {
        return localRepository.findByEstado(estado);
    }

    public List<LocalVacinacao> findByTipo(String tipo) {
        return localRepository.findByTipo(tipo);
    }

    public LocalVacinacao save(LocalVacinacao local) {
        return localRepository.save(local);
    }

    public LocalVacinacao update(Long id, LocalVacinacao localAtualizado) {
        return localRepository.findById(id)
                .map(local -> {
                    local.setNome(localAtualizado.getNome());
                    local.setEndereco(localAtualizado.getEndereco());
                    local.setCidade(localAtualizado.getCidade());
                    local.setEstado(localAtualizado.getEstado());
                    local.setCep(localAtualizado.getCep());
                    local.setTelefone(localAtualizado.getTelefone());
                    local.setHorarioFuncionamento(localAtualizado.getHorarioFuncionamento());
                    local.setLatitude(localAtualizado.getLatitude());
                    local.setLongitude(localAtualizado.getLongitude());
                    local.setTipo(localAtualizado.getTipo());
                    return localRepository.save(local);
                })
                .orElseThrow(() -> new RuntimeException("Local não encontrado com id: " + id));
    }

    public void deleteById(Long id) {
        localRepository.deleteById(id);
    }
}