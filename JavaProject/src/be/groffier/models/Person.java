package be.groffier.models;

import be.groffier.dao.*;

public abstract class Person {
	private static int ID = 0;
	private int id;
	private String name;
	private String firstname;
	private String tel;
	private String password;
	
	public Person(String name, String firstname, String tel, String password) {
		setId(ID++);
		setName(name);
		setFirstname(firstname);
		setTel(tel);
		setPassword(password);
	}
	
	public int getId() { return id; }
	public void setId(int value) { id = value; }
	
	public String getName() { return name; }
	public void setName(String value) { name = value; }
	
	public String getFirstname() { return firstname; }
	public void setFirstname(String value) { firstname = value; }
	
	public String getTel() { return tel; }
	public void setTel(String value) { tel = value; }
	
	public String getPassword() { return password; }
	public void setPassword(String value) { password = value; }
	
	public static Person auth(String username, String password) {
		PersonDAO p = new PersonDAO();
		return null; //TODO
	}
}