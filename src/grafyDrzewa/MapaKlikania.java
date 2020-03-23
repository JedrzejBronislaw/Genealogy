package grafyDrzewa;

import java.util.ArrayList;

import dane.Osoba;

public class MapaKlikania {

	class Obszar {
		int x1,y1,x2,y2;
		Osoba osoba;

		public Obszar() {
			// TODO Auto-generated constructor stub
		}

		public Obszar(Osoba osoba, int x1, int y1, int x2, int y2) {
			this.osoba = osoba;
			if (x1 < x2)
			{
				this.x1 = x1; this.x2 = x2;
			} else
			{
				this.x1 = x2; this.x2 = x1;
			}
			
			if (y1 < y2)
			{
				this.y1 = y1; this.y2 = y2;
			} else
			{
				this.y1 = y2; this.y2 = y1;
			}
		}
		
	}
	
	private ArrayList<Obszar> obszary = new ArrayList<Obszar>();
	
	public int liczbaObszarow() {return obszary.size();}
	public Obszar getObszar(int indeks){
		
		if (indeks < 0 || indeks >= obszary.size())
			throw new IllegalArgumentException("Indeks poza zakresem");

		return obszary.get(indeks);
	}
	
	public void dodajObszar(Osoba osoba, int x1, int y1, int x2, int y2)
	{
		obszary.add(new Obszar(osoba, x1,y1,x2,y2));
	}
	
	public Osoba ktoTuJest(int x, int y)
	{
		for (Obszar o: obszary)
			if ((o.x1 <= x) && (o.x2 >= x) &&
				(o.y1 <= y) && (o.y2 >= y))
				return o.osoba;
		return null;
	}

	public void wyczysc() {
		obszary.clear();
	}
	
	public String listaObszarow()
	{
		StringBuffer sb = new StringBuffer();
		
		for (Obszar o: obszary)
		{
			sb.append(o.osoba.imieNazwisko() + " -> " + o.x1 + "," + o.y1 + " | " + o.x2 + "," + o.y2 + "\n");
		}
		
		return sb.toString();
	}
}
