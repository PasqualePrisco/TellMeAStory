package entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Album implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String nome;
	private String path;
	private ArrayList<Vignetta> vignette;
	private int tipo;
	
	public Album() {
		
	}
	
	public Album(String nome, String path, int tipo, ArrayList<Vignetta> vignette) {
		this.setNome(nome);
		this.setPath(path);
		this.tipo=tipo;
		this.setVignette(vignette);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public ArrayList<Vignetta> getVignette() {
		return vignette;
	}

	public void setVignette(ArrayList<Vignetta> vignette) {
		this.vignette = vignette;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	
	
}
