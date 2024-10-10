package chc.eletrica9.repositorio;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import chc.eletrica9.entidade.Fonte;

public interface FonteRepositorio extends JpaRepository<Fonte, UUID> {

}