package Wrappers;

import com.google.gson.annotations.SerializedName;

public class WrapperUsuario {
	
	@SerializedName("nick")
	private String nick;
	@SerializedName("password")
	private String password;
	@SerializedName("mail")
	private String mail;
	@SerializedName("nombre")
	private String nombre;
	
	public WrapperUsuario() {
	}
	
	public WrapperUsuario(String nick, String password, String mail, String nombre) {
	
		this.nick = nick;
		this.password = password;
		this.mail = mail;
		this.nombre = nombre;
	}
	
	
	public String imprimirBookData(){
		
		return (this.nick+" "+this.password+" "+this.mail+" "+this.nombre);
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	
	

}
