package chc.eletrica9.servico;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chc.eletrica9.entidade.Fonte;
import chc.eletrica9.entidade.Projeto;
import chc.eletrica9.repositorio.ProjetoRepositorio;

@Service
public class ProjetoServico {

	@Autowired
	private ProjetoRepositorio projetoRepositorio;

	public Projeto salva(Projeto obj) {
		return projetoRepositorio.save(obj);
	}

	public void remove(UUID id) {
		projetoRepositorio.deleteById(id);
	}

	public Projeto buscaPorId(UUID id) {
		return projetoRepositorio.findById(id).get();
	}

	public Iterable<Projeto> buscaTodos() {
		return projetoRepositorio.findAll();
	}

	public Set<Fonte> findFontes(UUID id) {
		Projeto projeto = projetoRepositorio.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Projeto não encontrado"));
		return projeto.getFontes();
	}

	public Projeto update(Projeto obj) {
		Optional<Projeto> newObj = projetoRepositorio.findById(obj.getId());
		if (newObj.isPresent()) {
			updateProjeto(newObj.get(), obj);
			return projetoRepositorio.save(newObj.get());
		} else {
			// Tratar o caso em que o objeto Fonte com o ID fornecido não é encontrado
			// Isso pode envolver lançar uma exceção ou retornar null
			return null;
		}
	}

	public void updateProjeto(Projeto newObj, Projeto obj) {
		newObj.setNome(obj.getNome());
	}

	@Override
	public Projeto clone() {
		try {
			return (Projeto) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new AssertionError(); // Não pode acontecer
		}
	}
}