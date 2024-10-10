package chc.eletrica9.controle;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import chc.eletrica9.entidade.Projeto;
import chc.eletrica9.servico.ProjetoServico;

@Controller
//@RequestMapping("/projeto")
public class ProjetoControle {

	@Autowired
	private ProjetoServico projetoServico;

	@GetMapping("/projeto")
	public String listarProjetos(Model model) {
		model.addAttribute("projetos", projetoServico.buscaTodos());
		return "projetos";
	}

	@PostMapping("/projeto/{id}/delete")
	public String deleteProjeto(@PathVariable UUID id) {
		projetoServico.remove(id);
		return "redirect:/projeto";
	}

	@GetMapping("/projeto/entrada")
	public String entradaProjeto(Model model) {
		model.addAttribute("projeto", new Projeto());
		return "entrada_projeto";
	}

	@PostMapping("/projeto/salvar")
	public String salvarProjeto(@ModelAttribute Projeto projeto) {
		projetoServico.salva(projeto);
		return "redirect:/projeto";
	}

	@GetMapping("/projeto/{id}/editar")
	public String editarProjeto(@PathVariable UUID id, Model model) {
		Projeto projeto = projetoServico.buscaPorId(id);
		model.addAttribute("projeto", projeto);
		return "editar_projeto";
	}

	@PostMapping("/projeto/atualizar")
	public String atualizarProjeto(@ModelAttribute Projeto projeto) {
		projetoServico.update(projeto);
		return "redirect:/projeto";
	}

	@PostMapping("/projeto/{id}/copiar")
	public String copiarProjeto(@PathVariable UUID id) {
		Projeto projetoExistente = projetoServico.buscaPorId(id);
		Projeto novoProjeto = projetoExistente.clone(); // Este método deve ser implementado na classe Projeto
		novoProjeto.setId(null); // Definir o ID como null para que um novo ID seja gerado
		projetoServico.salva(novoProjeto);
		return "redirect:/projeto";
	}

}
