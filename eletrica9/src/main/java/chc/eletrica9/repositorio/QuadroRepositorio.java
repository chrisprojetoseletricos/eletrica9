package chc.eletrica9.repositorio;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import chc.eletrica9.entidade.Quadro;

public interface QuadroRepositorio extends JpaRepository<Quadro, UUID> {

}