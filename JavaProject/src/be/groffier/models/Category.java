package be.groffier.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Category implements Serializable{

	private static final long serialVersionUID = 3780568525675869930L;
	private int num;
	private Manager manager;
	private CategoryEnum category;
	private List<Member> members;
	private Calendar calendar;
	
	public Category() {}
	public Category(int id, Manager manager, CategoryEnum category, Calendar calendar) {
		setNum(id);
		
		setManager(manager);
		setCategory(category);
		setMembers(new ArrayList<>());
		setCalendar(calendar);
	}
	public Category(int id, CategoryEnum category) {
	    setNum(id);
	    setManager(null);
	    setCategory(category);
	    setMembers(new ArrayList<>());
	    setCalendar(null);
	}

	public int getNum() { return num; }
	public void setNum(int value) { num = value; }

	public Manager getManager() { return manager; }
	public void setManager(Manager value) { manager = value; }

	public CategoryEnum getCategory() { return category; }
	public void setCategory(CategoryEnum value) { category = value; }

	public List<Member> getMembers() { return members; }
	public void setMembers(List<Member> value) { members = value; }

	public Calendar getCalendar() { return calendar; }
	public void setCalendar(Calendar value) { calendar = value; }

	public boolean addMember(Member m) {
		if (members.contains(m)) {return false;}
		else {members.add(m); return true;}
	}
	
	@Override
	public String toString() {
		return category.toString().replace("_", " ");
	}
}