package com.vacinici.backend.controller;

import com.vacinici.backend.entity.*;
import com.vacinici.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/test-data")
@CrossOrigin(origins = "*")
public class TestDataController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private VacinaRepository vacinaRepository;

    @Autowired
    private LocalVacinacaoRepository localRepository;

    @Autowired
    private HistoricoVacinacaoRepository historicoRepository;

    @PostMapping("/populate")
    public ResponseEntity<String> populateTestData() {
        try {
            // Buscar usuários existentes
            List<Usuario> usuarios = usuarioRepository.findAll();
            List<Vacina> vacinas = vacinaRepository.findAll();
            List<LocalVacinacao> locais = localRepository.findAll();

            if (usuarios.isEmpty() || vacinas.isEmpty()) {
                return ResponseEntity.badRequest().body("Necessário ter usuários e vacinas cadastrados primeiro");
            }

            // Encontrar um paciente e um funcionário
            Usuario paciente = usuarios.stream()
                    .filter(u -> "PACIENTE".equals(u.getTipoUsuario()))
                    .findFirst()
                    .orElse(usuarios.get(0));

            Usuario funcionario = usuarios.stream()
                    .filter(u -> "FUNCIONARIO".equals(u.getTipoUsuario()))
                    .findFirst()
                    .orElse(usuarios.get(0));

            LocalVacinacao local = locais.isEmpty() ? null : locais.get(0);

            // Criar históricos de exemplo
            for (int i = 0; i < Math.min(3, vacinas.size()); i++) {
                HistoricoVacinacao historico = new HistoricoVacinacao();
                historico.setPaciente(paciente);
                historico.setFuncionario(funcionario);
                historico.setVacina(vacinas.get(i));
                historico.setLocal(local);
                historico.setDose("1ª dose");
                historico.setDataAplicacao(LocalDate.now().minusDays(30 + i * 10));
                historico.setLote("LOTE00" + (i + 1));
                historico.setValidade(LocalDate.now().plusYears(1));
                historico.setObservacoes("Vacina aplicada com sucesso - teste");

                historicoRepository.save(historico);
            }

            return ResponseEntity.ok("Dados de teste criados com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao criar dados: " + e.getMessage());
        }
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearTestData() {
        try {
            historicoRepository.deleteAll();
            return ResponseEntity.ok("Dados de teste removidos!");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao remover dados: " + e.getMessage());
        }
    }
}