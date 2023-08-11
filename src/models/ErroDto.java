package models;

import java.util.List;

public class ErroDto {
	
	private String erro;
	private List<String> validacoes;
	
	public ErroDto() {
		
	}

	public ErroDto(String erro, List<String> validacoes) {
		super();
		this.erro = erro;
		this.validacoes = validacoes;
	}

	public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		this.erro = erro;
	}

	public List<String> getValidacoes() {
		return validacoes;
	}

	public void setValidacoes(List<String> validacoes) {
		this.validacoes = validacoes;
	}

	@Override
	public String toString() {
		return "ErroDto [erro=" + erro + ", validacoes=" + validacoes + "]";
	}
	
}
