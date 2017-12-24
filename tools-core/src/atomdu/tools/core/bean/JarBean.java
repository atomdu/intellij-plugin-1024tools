package atomdu.tools.core.bean;

import java.io.Serializable;

public class JarBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private String name;
	private String jar;
	public JarBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public JarBean(String name, String jar) {
		super();
		this.name = name;
		this.jar = jar;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getJar() {
		return jar;
	}
	public void setJar(String jar) {
		this.jar = jar;
	}
	@Override
	public String toString() {
		return "JarBean [name=" + name + ", jar=" + jar + "]";
	}
	
}
