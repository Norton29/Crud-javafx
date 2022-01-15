package model.entities;

import java.io.Serializable;

public class Departments implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	
	public Departments() {
		
	}
	
	public Departments(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Departments [id=" + id + ", name=" + name + "]";
	}
	
	
	
}
