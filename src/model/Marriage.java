package model;

import lombok.Getter;
import lombok.Setter;
import model.tools.ManWoman;

public class Marriage {
			
	@Setter @Getter private String date;
	@Setter @Getter private String place;
	@Setter @Getter private Person husband, wife;
	
	public Marriage() {}
	
	public Marriage(Person spouse1, Person spouse2) {
		setSpouses(spouse1, spouse2);
	}

	public Marriage(Person spouse1, Person spouse2, String date, String place) {
		setSpouses(spouse1, spouse2);
		this.date = date;
		this.place = place;
	}
	
	private void setSpouses(Person spouse1, Person spouse2) {
		ManWoman manWoman = new ManWoman(spouse1, spouse2);
		
		if (manWoman.success()) {
			husband = manWoman.getMan();
			wife = manWoman.getWoman();
		}
	}
	
}
