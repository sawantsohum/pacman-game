package com.example.demo;

public class newtopic {

	private String id;
	private String character;
	private String name;
	private String pacman;
	
	public newtopic() {
		
	}
	
	public newtopic(String id, String character, String name, String pacman) {
		super();
		this.id = id;
		this.character = character;
		this.name = name;
		this.pacman = pacman;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getCharacter() {
		return character;
	}
	public void setCharacter(String character) {
		this.character = character;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPacman() {
		return pacman;
	}
	public void setPacman(String pacman) {
		this.pacman = pacman;
	}
}
