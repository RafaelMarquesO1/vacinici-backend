package com.vacinici.backend.service;

import com.vacinici.backend.entity.Vacina;
import com.vacinici.backend.repository.VacinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VacinaService {

    @Autowired
    private VacinaRepository vacinaRepository;

    public List<Vacina> findAll() {
        return vacinaRepository.findAll();
    }

    public Optional<Vacina> findById(Long id) {
        return vacinaRepository.findById(id);
    }

    public List<Vacina> findByCategoria(String categoria) {
        return vacinaRepository.findByCategoria(categoria);
    }

    public List<Vacina> findByNomeContaining(String nome) {
        return vacinaRepository.findByNomeContaining(nome);
    }

    public List<Vacina> findByIdadeAplicavel(Integer idade) {
        return vacinaRepository.findByIdadeAplicavel(idade);
    }

    public Vacina save(Vacina vacina) {
        return vacinaRepository.save(vacina);
    }

    public Vacina update(Long id, Vacina vacinaAtualizada) {
        return vacinaRepository.findById(id)
                .map(vacina -> {
                    vacina.setNome(vacinaAtualizada.getNome());
                    vacina.setFabricante(vacinaAtualizada.getFabricante());
                    vacina.setDescricao(vacinaAtualizada.getDescricao());
                    vacina.setDosesRecomendadas(vacinaAtualizada.getDosesRecomendadas());
                    vacina.setIntervaloDoses(vacinaAtualizada.getIntervaloDoses());
                    vacina.setIdadeMinima(vacinaAtualizada.getIdadeMinima());
                    vacina.setIdadeMaxima(vacinaAtualizada.getIdadeMaxima());
                    vacina.setCategoria(vacinaAtualizada.getCategoria());
                    vacina.setImagemUrl(vacinaAtualizada.getImagemUrl());
                    return vacinaRepository.save(vacina);
                })
                .orElseThrow(() -> new RuntimeException("Vacina não encontrada com id: " + id));
    }

    public void deleteById(Long id) {
        vacinaRepository.deleteById(id);
    }
}