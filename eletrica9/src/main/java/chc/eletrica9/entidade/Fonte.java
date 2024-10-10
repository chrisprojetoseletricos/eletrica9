package chc.eletrica9.entidade;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "TB_Fonte")
public class Fonte implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	@Column(name = "Concessionaria")
	private String concessionaria;
	@Column(name = "NomeFonte")
	private String Nome;
	// @Column(name = "Tensão_Fonte", nullable = false, unique = true) private
	// double tensao_fonte;
	@Lob
	@Column(name = "Descrição")
	private String descricao;
	// @Column(name = "TipoRede_Fonte", nullable = false, unique = true) private
	// String tipoRede;

	@JsonProperty(access = Access.WRITE_ONLY)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "projeto_id")
	private Projeto projeto;

	@Builder.Default
	@JsonProperty(access = Access.WRITE_ONLY)
	@OneToMany(mappedBy = "fonte", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Quadro> quadros = new HashSet<>();

	@Override
	public Fonte clone() {
		Fonte cloned = new Fonte();

		cloned.setConcessionaria(concessionaria);
		cloned.setProjeto(this.getProjeto());
		cloned.setDescricao(descricao);
		cloned.setNome(Nome);

		cloned.setQuadros(new HashSet<Quadro>());

		if (this.quadros != null) {
			for (Quadro quadro : this.quadros) {
				Quadro clonedQuadro = quadro.clone();
				clonedQuadro.setFonte(cloned);
				cloned.getQuadros().add(clonedQuadro);
			}
		}

		return cloned;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Fonte fonte = (Fonte) o;
		return id != null && id.equals(fonte.id);
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}
}
