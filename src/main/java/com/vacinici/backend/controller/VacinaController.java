package com.vacinici.backend.controller;

import com.vacinici.backend.entity.Vacina;
import com.vacinici.backend.service.VacinaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vacinas")
@CrossOrigin(origins = "*")
public class VacinaController {

    @Autowired
    private VacinaService vacinaService;

    @GetMapping
    public ResponseEntity<List<Vacina>> getAllVacinas() {
        List<Vacina> vacinas = vacinaService.findAll();
        return ResponseEntity.ok(vacinas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vacina> getVacinaById(@PathVariable Long id) {
        Optional<Vacina> vacina = vacinaService.findById(id);
        return vacina.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Vacina>> getVacinasByCategoria(@PathVariable String categoria) {
        List<Vacina> vacinas = vacinaService.findByCategoria(categoria);
        return ResponseEntity.ok(vacinas);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Vacina>> buscarVacinasPorNome(@RequestParam String nome) {
        List<Vacina> vacinas = vacinaService.findByNomeContaining(nome);
        return ResponseEntity.ok(vacinas);
    }

    @GetMapping("/idade/{idade}")
    public ResponseEntity<List<Vacina>> getVacinasPorIdade(@PathVariable Integer idade) {
        List<Vacina> vacinas = vacinaService.findByIdadeAplicavel(idade);
        return ResponseEntity.ok(vacinas);
    }

    @PostMapping
    public ResponseEntity<Vacina> createVacina(@Valid @RequestBody Vacina vacina) {
        try {
            Vacina novaVacina = vacinaService.save(vacina);
            return ResponseEntity.ok(novaVacina);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vacina> updateVacina(@PathVariable Long id, @Valid @RequestBody Vacina vacina) {
        try {
            Vacina vacinaAtualizada = vacinaService.update(id, vacina);
            return ResponseEntity.ok(vacinaAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVacina(@PathVariable Long id) {
        try {
            vacinaService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}