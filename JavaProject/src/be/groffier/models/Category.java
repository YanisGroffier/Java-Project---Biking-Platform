package be.groffier.models;

import java.util.ArrayList;
import java.util.List;

public class Category {
	private static int ID = 0;
	private int num;
	
	private Manager manager;
	private CategoryEnum category;
	private List<Member> members;
	private Calendar calendar;
	
	public Category(Manager manager, CategoryEnum category, Calendar calendar) {
		setNum(ID++);
		
		setManager(manager);
		setCategory(category);
		setMembers(new ArrayList<>());
		setCalendar(calendar);
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
}