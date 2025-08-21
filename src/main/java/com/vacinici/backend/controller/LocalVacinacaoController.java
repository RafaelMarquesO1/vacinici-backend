package com.vacinici.backend.controller;

import com.vacinici.backend.entity.LocalVacinacao;
import com.vacinici.backend.service.LocalVacinacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/locais")
@CrossOrigin(origins = "*")
public class LocalVacinacaoController {

    @Autowired
    private LocalVacinacaoService localService;

    @GetMapping
    public ResponseEntity<List<LocalVacinacao>> getAllLocais() {
        List<LocalVacinacao> locais = localService.findAll();
        return ResponseEntity.ok(locais);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocalVacinacao> getLocalById(@PathVariable Long id) {
        Optional<LocalVacinacao> local = localService.findById(id);
        return local.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cidade/{cidade}")
    public ResponseEntity<List<LocalVacinacao>> getLocaisByCidade(@PathVariable String cidade) {
        List<LocalVacinacao> locais = localService.findByCidade(cidade);
        return ResponseEntity.ok(locais);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<LocalVacinacao>> getLocaisByEstado(@PathVariable String estado) {
        List<LocalVacinacao> locais = localService.findByEstado(estado);
        return ResponseEntity.ok(locais);
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<LocalVacinacao>> getLocaisByTipo(@PathVariable String tipo) {
        List<LocalVacinacao> locais = localService.findByTipo(tipo);
        return ResponseEntity.ok(locais);
    }

    @PostMapping
    public ResponseEntity<LocalVacinacao> createLocal(@Valid @RequestBody LocalVacinacao local) {
        try {
            LocalVacinacao novoLocal = localService.save(local);
            return ResponseEntity.ok(novoLocal);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocalVacinacao> updateLocal(@PathVariable Long id, @Valid @RequestBody LocalVacinacao local) {
        try {
            LocalVacinacao localAtualizado = localService.update(id, local);
            return ResponseEntity.ok(localAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocal(@PathVariable Long id) {
        try {
            localService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}