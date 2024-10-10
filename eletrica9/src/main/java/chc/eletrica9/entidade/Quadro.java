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
@Table(name = "TB_Quadro")
public class Quadro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	// @OneToMany(mappedBy = "quadro", targetEntity = Circuito.class, fetch =
	// FetchType.LAZY, cascade = CascadeType.ALL)
	// private Set<Circuito> circuitos = new HashSet<>();
	// @Column(name = "UsaDR", nullable = false, unique = true)
	// private boolean usoDeDR;
	// @Column(name = "Local")
	// private String localizacao;
	@Column(name = "NomeQuadro")
	private String nome;
	@Column(name = "Pot100PercDem")
	private double pot100PercDem;
	// @Enumerated(EnumType.STRING)
	// private int tempAmbiente;
	// @Column(name = "TipoRede_Quadro", nullable = false, unique = true)
	// private String tipoRede;
	// @Column(name = "Tensão_Quadro", nullable = false, unique = true)
	// private double tensao_quadro;

	@JsonProperty(access = Access.WRITE_ONLY)
	@JoinColumn(name = "fonte_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Fonte fonte;

	@JsonProperty(access = Access.WRITE_ONLY)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "quadro_pai_id")
	private Quadro quadroPai;

	@OneToMany(mappedBy = "quadroPai", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Quadro> quadrosFilhos;

	/*
	 * @OneToMany(mappedBy = "quadro", cascade = CascadeType.ALL, orphanRemoval =
	 * true) private Set<Circuito> circuitos;
	 */

	@Override
	public Quadro clone() {
		Quadro clone = new Quadro();
		clone.setNome(nome);
		if (this.fonte != null) {
			clone.setFonte(this.fonte);
		}
		clone.setQuadrosFilhos(new HashSet<Quadro>());
		for (Quadro quadroFilho : this.getQuadrosFilhos()) {
			Quadro clonedChild = new Quadro();
			clonedChild.setNome(quadroFilho.getNome());
			clonedChild.setFonte(quadroFilho.getFonte());
			clonedChild.setQuadroPai(clone); // Set clone as the parent of clonedChild
			clone.getQuadrosFilhos().add(clonedChild);
		}
		return clone;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Quadro quadro = (Quadro) o;
		return id != null && id.equals(quadro.id);
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}
}
