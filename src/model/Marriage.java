package model;

import lombok.Getter;
import lombok.Setter;

public class Marriage {
			
	@Setter @Getter private String date;
	@Setter @Getter private String place;
	@Setter @Getter private Person husband, wife;
	
}
