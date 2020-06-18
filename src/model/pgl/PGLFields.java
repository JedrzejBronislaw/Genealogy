package model.pgl;

import java.text.SimpleDateFormat;

public class PGLFields {

	public static final SimpleDateFormat dataFormat = new SimpleDateFormat("hh:mm:ss yyyy-MM-dd");
	public static final String lineSeparator = "$";

	public static final String mainSectionName = "MAIN";

	public static final String lastOpen         = "ost_otw";
	public static final String lastModification = "wersja";
	public static final String numberOfPersons  = "ile";
	public static final String commonSurname    = "nazw";

	public static String commonSurname(int number) {
		return commonSurname + number;
	}

	public static final String firstName     = "imie";
	public static final String lastName      = "nazwisko";
	public static final String alias         = "ps";
	public static final String sex           = "plec";
	public static final String birthDate     = "datur";
	public static final String birthPlace    = "miejur";
	public static final String deathDate     = "datsm";
	public static final String deathPlace    = "miejsm";
	public static final String lifeStatus    = "zyje";
	public static final String baptismParish = "parafia";
	public static final String burialPlace   = "mpoch";

	public static final String contact  = "kontakt";
	public static final String comments = "uwagi";

	public static final String father    = "ojciec";
	public static final String mother    = "matka";
	public static final String children  = "dzieci";
	public static final String marriages = "malzenstwa";

	public static final String child        = "dziecko";
	public static final String spouse       = "malzonek";
	public static final String weddingDate  = "malzdata";
	public static final String weddingPlace = "malzmjsc";


	public static String spouse(int number) {
		return spouse + number;
	}

	public static String child(int number) {
		return child + number;
	}

	public static String weddingDate(int number) {
		return weddingDate + number;
	}

	public static String weddingPlace(int number) {
		return weddingPlace + number;
	}
}
