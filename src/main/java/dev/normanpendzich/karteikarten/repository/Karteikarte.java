package dev.normanpendzich.karteikarten.repository;

public class Karteikarte {
	private Integer id;
	private String frage, antwort, kategorie, user, geaendert;
	private Boolean flag;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFrage() {
		return frage;
	}
	public void setFrage(String frage) {
		this.frage = frage;
	}
	public String getAntwort() {
		return antwort;
	}
	public void setAntwort(String antwort) {
		this.antwort = antwort;
	}
	public String getKategorie() {
		return kategorie;
	}
	public void setKategorie(String kategorie) {
		this.kategorie = kategorie;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getGeaendert() {
		return geaendert;
	}
	public void setGeaendert(String geaendert) {
		this.geaendert = geaendert;
	}
	public Boolean getFlag() {
		return flag;
	}
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	
	
	
}
