package treeGraphs;

import java.util.ArrayList;

import model.Person;

public class ClickMap {

	class ClickArea {
		int left, top, right, bottom;
		Person osoba;

		public ClickArea() {}

		public ClickArea(Person osoba, int left, int top, int right, int bottom) {
			this.osoba = osoba;
			if (left < right)
			{
				this.left = left;
				this.right = right;
			} else
			{
				this.left = right;
				this.right = left;
			}
			
			if (top < bottom)
			{
				this.top = top;
				this.bottom = bottom;
			} else
			{
				this.top = bottom;
				this.bottom = top;
			}
		}
		
	}
	
	private ArrayList<ClickArea> areas = new ArrayList<ClickArea>();
	
	public int size() {
		return areas.size();
	}
	
	public ClickArea getArea(int index){
		
		if (index < 0 || index >= areas.size())
			throw new IllegalArgumentException("Index out of range");

		return areas.get(index);
	}
	
	public void addArea(Person person, int top, int left, int bottom, int right)
	{
		areas.add(new ClickArea(person, top, left, bottom, right));
	}
	
	public Person whoIsThere(int x, int y)
	{
		for (ClickArea o: areas)
			if ((o.left <= x) && (o.right >= x) &&
				(o.top <= y) && (o.bottom >= y))
				return o.osoba;
		return null;
	}

	public void clear() {
		areas.clear();
	}
	
	public String areaList()
	{
		StringBuffer sb = new StringBuffer();
		
		for (ClickArea area: areas)
		{
			sb.append(area.osoba.nameSurname() + " -> " + area.left + "," + area.top + " | " + area.right + "," + area.bottom + "\n");
		}
		
		return sb.toString();
	}
}
