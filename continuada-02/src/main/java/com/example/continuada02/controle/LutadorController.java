package com.example.continuada02.controle;

import com.example.continuada02.dominio.Golpe;
import com.example.continuada02.dominio.Lutador;
import com.example.continuada02.repositorio.LutadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lutadores")
public class LutadorController {

    @Autowired
    private LutadorRepository repository;

    @PostMapping
    public ResponseEntity addLutadores(@RequestBody @Valid Lutador lutador) {
        if(lutador.getNome().length() < 3 || lutador.getNome().length() > 12){
            return ResponseEntity.status(400).body(lutador);
        }else {
            if (lutador.getForcaGolpe() <= 0) {
                return ResponseEntity.status(400).body(lutador);
            } else {
                repository.save(lutador);
                return ResponseEntity.status(201).build();
            }
        }
    }

    @GetMapping()
    public ResponseEntity pegarLutadores(){
        return ResponseEntity.status(200).body(repository.findAll());
    }

    @GetMapping("/contagem-vivos")
    public ResponseEntity retornarLutadoresVivos(){
        return ResponseEntity.status(200).body(repository.findAllByVivoIsTrue());
    }

    @PostMapping(" /lutadores/{id}/concentrar")
    public ResponseEntity concentrarLutadores(@PathVariable Integer id){
        Optional<Lutador> lutador = repository.findById(id);

        if(lutador.isPresent()){
            if(lutador.get().getConcentracoesRealizadas() < 4){
                lutador.get().setConcentracoesRealizadas(lutador.get().getConcentracoesRealizadas()+1);
                lutador.get().setVida(lutador.get().getVida() * 1.15);
                repository.save(lutador.get());
                return ResponseEntity.status(200).build();
            }else{
                return ResponseEntity.status(400).body("Lutador já se concentrou 3 vezes!");
            }
        }else{
            return ResponseEntity.status(400).body("Esse lutador não existe!");
        }
    }

    @PostMapping("/golpe")
    public ResponseEntity adicionarGolpe(@RequestBody Golpe golpe){
        Optional<Lutador> lutadorBate = repository.findById(golpe.getIdLutadorBate());
        Optional<Lutador> lutadorApanha = repository.findById(golpe.getIdLutadorApanha());

        if(lutadorApanha.get().getVida() < lutadorBate.get().getForcaGolpe()){
            lutadorApanha.get().setVida(0.0);
        }else{
            lutadorApanha.get().setVida(lutadorApanha.get().getVida() - lutadorBate.get().getForcaGolpe());
        }
        repository.save(lutadorApanha.get());

        List<Lutador> lutadores = new ArrayList<>();

        lutadores.add(lutadorBate.get());
        lutadores.add(lutadorApanha.get());

        return ResponseEntity.status(201).body(lutadores);
    }

    @GetMapping("/mortos")
    public ResponseEntity retornarMortos(){
        return ResponseEntity.status(200).body(repository.findAllByVida(0.0));
    }
}
