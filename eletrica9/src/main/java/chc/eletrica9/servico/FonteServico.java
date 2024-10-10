package chc.eletrica9.servico;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chc.eletrica9.entidade.Fonte;
import chc.eletrica9.repositorio.FonteRepositorio;

@Service
public class FonteServico {

	@Autowired
	private FonteRepositorio fonteRepositorio;

	public Fonte salva(Fonte obj) {
		return fonteRepositorio.save(obj);
	}

	public void remove(UUID id) {
		fonteRepositorio.deleteById(id);
	}

	public Fonte buscaPorId(UUID id) {
		return fonteRepositorio.findById(id).get();
	}

	public Iterable<Fonte> buscaTodos() {
		return fonteRepositorio.findAll();
	}

	public Fonte update(Fonte obj) {
		Optional<Fonte> newObj = fonteRepositorio.findById(obj.getId());
		if (newObj.isPresent()) {
			updateFonte(newObj.get(), obj);
			return fonteRepositorio.save(newObj.get());
		} else {
			// Tratar o caso em que o objeto Fonte com o ID fornecido não é encontrado
			// Isso pode envolver lançar uma exceção ou retornar null
			return null;
		}
	}

	public void updateFonte(Fonte newObj, Fonte obj) {
		newObj.setNome(obj.getNome());
	}

	@Override
	public Fonte clone() {
		try {
			return (Fonte) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new AssertionError(); // Não pode acontecer
		}
	}
}