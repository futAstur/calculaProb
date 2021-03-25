package calculaProb.beans;

public class Partido {

	String local;
	int resultado;
	String visitante;
	
	public static final int V = 0;
	public static final int E =1;
	public static final int D =2;
	
	public Partido (String local,String visitante) {
		this.local=local;
		this.visitante=visitante;
	}
	
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	public int getResultado() {
		return resultado;
	}
	public Partido setResultado(int resultado) {
		this.resultado = resultado;
		return this;
	}
	public String getVisitante() {
		return visitante;
	}
	public void setVisitante(String visitante) {
		this.visitante = visitante;
	}
	public Partido clone() {
		return new Partido(local, visitante);
	}
	public String toString() {
		return local+" - "+visitante;
	}
	
	
	
}
