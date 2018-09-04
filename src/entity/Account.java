package entity;

import java.io.Serializable;

public class Account implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String nome;
	private String cognome;
	private String email;
	private String password;
    private String EMAIL_VERIFICATION_HASH;
    private String STATUS;
	
	public Account() {
		
	}
	
	public Account(String nome, String cognome, String email, String password) {
		this.nome=nome;
		this.cognome=cognome;
		this.email=email;
		this.password=password;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEMAIL_VERIFICATION_HASH() {
		return EMAIL_VERIFICATION_HASH;
	}

	public void setEMAIL_VERIFICATION_HASH(String eMAIL_VERIFICATION_HASH) {
		EMAIL_VERIFICATION_HASH = eMAIL_VERIFICATION_HASH;
	}


	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}


}
