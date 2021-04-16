package com.example.continuada02.repositorio;

import com.example.continuada02.dominio.Lutador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LutadorRepository extends JpaRepository<Lutador, Integer> {

//    List<Lutador> findByLutador();

      List<Lutador> findAllByVivoIsTrue();

      List<Lutador> findAllByVida(Double vida);
}
