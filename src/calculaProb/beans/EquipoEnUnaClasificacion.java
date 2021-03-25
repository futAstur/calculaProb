package calculaProb.beans;

public class EquipoEnUnaClasificacion {

	private String equipo;
	private int victorias;
	private int empates;
	private int derrotas;
	private int puntos;
	
	public EquipoEnUnaClasificacion(String eq, int v, int e,int d) {
		equipo=eq;
		victorias=v;
		empates=e;
		derrotas=d;
	}
	
	public String getEquipo() {
		return equipo;
	}
	public void setEquipo(String equipo) {
		this.equipo = equipo;
	}
	public int getVictorias() {
		return victorias;
	}
	public void addVictorias(int victorias) {
		this.victorias += victorias;
	}
	public int getEmpates() {
		return empates;
	}
	public void addEmpates(int empates) {
		this.empates += empates;
	}
	public int getDerrotas() {
		return derrotas;
	}
	public void addDerrotas(int derrotas) {
		this.derrotas += derrotas;
	}
	public int getPuntos() {
		return puntos;
	}
	public void addPuntos(int puntos) {
		this.puntos += puntos;
	}

	public void colocarPuntos() {
		this.puntos=victorias*3+empates;
		
	}
	
	public EquipoEnUnaClasificacion clone() {
		return new EquipoEnUnaClasificacion(equipo, victorias, empates, derrotas);
	}

	public int getPartidos() {
		return victorias + empates + derrotas;
	}
	
	
	
	
}
