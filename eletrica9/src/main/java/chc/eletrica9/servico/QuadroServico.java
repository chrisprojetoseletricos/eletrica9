package chc.eletrica9.servico;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chc.eletrica9.entidade.Quadro;
import chc.eletrica9.repositorio.QuadroRepositorio;

@Service
public class QuadroServico {

	@Autowired
	private QuadroRepositorio quadroRepositorio;

	public Quadro salva(Quadro obj) {
		return quadroRepositorio.save(obj);
	}

	public void remove(UUID id) {
		quadroRepositorio.deleteById(id);
	}

	public Quadro buscaPorId(UUID id) {
		return quadroRepositorio.findById(id).get();
	}

	public Iterable<Quadro> buscaTodos() {
		return quadroRepositorio.findAll();
	}

	public Quadro update(Quadro obj) {
		Optional<Quadro> newObj = quadroRepositorio.findById(obj.getId());
		if (newObj.isPresent()) {
			updateQuadro(newObj.get(), obj);
			return quadroRepositorio.save(newObj.get());
		} else {
			// Tratar o caso em que o objeto Fonte com o ID fornecido não é encontrado
			// Isso pode envolver lançar uma exceção ou retornar null
			return null;
		}
	}

	public void updateQuadro(Quadro newObj, Quadro obj) {
		newObj.setNome(obj.getNome());
	}

	@Override
	public Quadro clone() {
		try {
			return (Quadro) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new AssertionError(); // Não pode acontecer
		}
	}
}