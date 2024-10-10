package chc.eletrica9.controle;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import chc.eletrica9.entidade.Fonte;
import chc.eletrica9.entidade.Projeto;
import chc.eletrica9.servico.FonteServico;
import chc.eletrica9.servico.ProjetoServico;

@Controller
public class FonteControle {

	@Autowired
	private FonteServico fonteServico;
	@Autowired
	private ProjetoServico projetoServico;

	@GetMapping("/projeto/{projetoId}/fonte")
	public String listarFontes(@PathVariable UUID projetoId, Model model) {
		Projeto projeto = projetoServico.buscaPorId(projetoId);
		model.addAttribute("fontes", projeto.getFontes());
		model.addAttribute("projetoId", projetoId);
		return "fontes";
	}

	@PostMapping("/fonte/{id}/delete")
	public String deleteFonte(@PathVariable UUID id) {
		Fonte fonte = fonteServico.buscaPorId(id);
		UUID projetoId = fonte.getProjeto().getId();
		fonteServico.remove(id);
		return "redirect:/projeto/" + projetoId + "/fonte";
	}

	@PostMapping("/fonte/salvar")
	public String salvarFonte(@ModelAttribute Fonte fonte, @RequestParam UUID projetoId) {
		Projeto projeto = projetoServico.buscaPorId(projetoId);
		fonte.setProjeto(projeto);
		fonteServico.salva(fonte);
		return "redirect:/projeto/" + projetoId + "/fonte";
	}

	@GetMapping("/fonte/{id}/editar")
	public String editarFonte(@PathVariable UUID id, Model model) {
		Fonte fonte = fonteServico.buscaPorId(id);
		model.addAttribute("fonte", fonte);
		return "editar_fonte";
	}

	@PostMapping("/fonte/atualizar")
	public String atualizarFonte(@ModelAttribute Fonte fonte, @RequestParam UUID projetoId) {
		fonteServico.update(fonte);
		return "redirect:/projeto/" + projetoId + "/fonte";
	}

	@GetMapping("/fonte/entrada/{projetoId}")
	public String entradaFonte(@PathVariable UUID projetoId, Model model) {
		model.addAttribute("fonte", new Fonte());
		model.addAttribute("projetoId", projetoId);
		return "entrada_fonte";
	}

	@PostMapping("/fonte/{id}/copiar")
	public String copiarFonte(@PathVariable UUID id) {
		Fonte fonteExistente = fonteServico.buscaPorId(id);
		Fonte novaFonte = fonteExistente.clone();
		novaFonte.setId(null);
		fonteServico.salva(novaFonte);
		UUID projetoId = novaFonte.getProjeto().getId();
		return "redirect:/projeto/" + projetoId + "/fonte";
	}

}
