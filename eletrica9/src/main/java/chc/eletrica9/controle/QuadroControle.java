package chc.eletrica9.controle;

import java.util.Set;
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
import chc.eletrica9.entidade.Quadro;
import chc.eletrica9.servico.FonteServico;
import chc.eletrica9.servico.ProjetoServico;
import chc.eletrica9.servico.QuadroServico;

@Controller
public class QuadroControle {

	@Autowired
	private FonteServico fonteServico;
	@Autowired
	private QuadroServico quadroServico;
	@Autowired
	private ProjetoServico projetoServico;

	@GetMapping("/fonte/{fonteId}/quadro")
	public String listarFontes(@PathVariable UUID fonteId, Model model) {
		Fonte fonte = fonteServico.buscaPorId(fonteId);
		model.addAttribute("quadros", fonte.getQuadros());
		model.addAttribute("fonteId", fonteId);
		model.addAttribute("projetoId", fonte.getProjeto().getId()); // Adicione esta linha
		return "quadros";
	}

	@GetMapping("/projeto/{projetoId}/fontes")
	public String listarFontesDoProjeto(@PathVariable UUID projetoId, Model model) {
		Projeto projeto = projetoServico.buscaPorId(projetoId);
		Iterable<Fonte> fontes = projeto.getFontes();
		model.addAttribute("fontes", fontes);
		return "fontes";
	}

	@PostMapping("/quadro/salvar")
	public String salvarQuadro(@ModelAttribute Quadro quadro, @RequestParam UUID fonteId) {
		Fonte fonte = fonteServico.buscaPorId(fonteId);
		quadro.setFonte(fonte);
		quadroServico.salva(quadro);
		return "redirect:/fonte/" + fonteId + "/quadro";
	}

	@PostMapping("/subquadro/salvar")
	public String salvarSubQuadro(@ModelAttribute Quadro quadro, @RequestParam UUID fonteId,
			@RequestParam UUID quadroId) {
		Quadro subquadro = quadroServico.buscaPorId(quadroId); // quadro.setFonte(fonte);
		quadro.setQuadroPai(subquadro);
		quadroServico.salva(quadro);
		return "redirect:/quadro/" + quadroId + "/subquadro";
	}

	@GetMapping("/quadro/entrada/{fonteId}")
	public String entradaQuadro(@PathVariable UUID fonteId, Model model) {
		model.addAttribute("quadro", new Quadro());
		model.addAttribute("fonteId", fonteId);
		return "entrada_quadro";
	}

	@PostMapping("/quadro/atualizar")
	public String atualizarQuadro(@ModelAttribute Quadro quadro, @RequestParam UUID fonteId) {
		quadroServico.update(quadro);
		return "redirect:/fonte/" + fonteId + "/quadro";
	}

	@GetMapping("/quadro/{id}/editar")
	public String editarQuadro(@PathVariable UUID id, Model model) {
		Quadro quadro = quadroServico.buscaPorId(id);
		model.addAttribute("quadro", quadro);
		return "editar_quadro";
	}

	@PostMapping("/quadro/{id}/delete")
	public String deleteQuadro(@PathVariable UUID id) {
		Quadro quadro = quadroServico.buscaPorId(id);
		UUID fonteId = quadro.getFonte().getId();
		quadroServico.remove(id);
		return "redirect:/fonte/" + fonteId + "/quadro";
	}

	@PostMapping("/quadro/{id}/copiar")
	public String copiarQuadro(@PathVariable UUID id) {
		Quadro quadroExistente = quadroServico.buscaPorId(id);
		Quadro novoQuadro = quadroExistente.clone(); // Este método deve ser implementado na classe Quadro
		novoQuadro.setId(null);
		quadroServico.salva(novoQuadro);
		UUID fonteId = novoQuadro.getFonte().getId();
		return "redirect:/fonte/" + fonteId + "/quadro";
	}

/*	@GetMapping("/subQuadros.html")
	public String subQuadros(@RequestParam UUID quadroId, Model model) {
		Quadro quadro = quadroServico.buscaPorId(quadroId);
		Set<Quadro> subQuadros = quadro.getQuadrosFilhos(); // Busca os subquadros
		model.addAttribute("quadros", subQuadros); // Adiciona os subquadros ao modelo
		model.addAttribute("quadroId", quadroId);
		model.addAttribute("projetoId", quadro.getFonte().getProjeto().getId());
		model.addAttribute("fonteId", quadro.getFonte().getId());
		return "subQuadros";
	}*/

	@GetMapping("/quadro/entrada/subquadro/{quadroId}")
	public String entradaSubQuadro(@PathVariable UUID quadroId, Model model) {
		Quadro quadroPai = quadroServico.buscaPorId(quadroId);
		Quadro novoSubQuadro = new Quadro();
		novoSubQuadro.setQuadroPai(quadroPai);
		model.addAttribute("quadro", novoSubQuadro);
		model.addAttribute("fonteId", quadroPai.getFonte().getId());
		return "entrada_subquadro";
	}

	@GetMapping("/subQuadros.html")
	public String subQuadros(@RequestParam UUID quadroId, Model model) {
		Quadro quadro = quadroServico.buscaPorId(quadroId);
		Set<Quadro> subQuadros = quadro.getQuadrosFilhos(); // Busca os subquadros
		model.addAttribute("quadros", subQuadros); // Adiciona os subquadros ao modelo
		model.addAttribute("quadroId", quadroId);
		model.addAttribute("projetoId", quadro.getFonte().getProjeto().getId());
		model.addAttribute("fonteId", quadro.getFonte().getId());
		return "subQuadros";
	}

}
