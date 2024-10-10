/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chc.eletrica9.entidade;

import java.io.Serializable;
import java.util.Date;
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
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(of = "id")

@Entity
@Table(name = "TB_Projeto")
public class Projeto implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	@Column(name = "Nome")
	private String nome;
	@Column(name = "Autor")
	private String autor;
	@Column(name = "Data")
	@Temporal(TemporalType.DATE)
	private Date dataProjeto;
	@Lob
	private String descricao;

	@Builder.Default
	@JsonProperty(access = Access.WRITE_ONLY)
	@OneToMany(mappedBy = "projeto", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Fonte> fontes = new HashSet<>();

	@Override
	public Projeto clone() {

		Projeto cloned = new Projeto();
		cloned.setId(null); // Adicione esta linha
		cloned.setNome(nome);
		cloned.setAutor(autor);
		cloned.setDataProjeto(dataProjeto);
		cloned.setDescricao(descricao);
		cloned.setFontes(new HashSet<>()); // Cria uma nova coleção vazia de Fonte

		for (Fonte fonte : this.fontes) {
			Fonte clonedFonte = fonte.clone(); // Este método deve ser implementado na classe Fonte
			clonedFonte.setProjeto(cloned); // Vincula a fonte clonada ao novo projeto
			cloned.getFontes().add(clonedFonte);
		}

		return cloned;

	}
}
