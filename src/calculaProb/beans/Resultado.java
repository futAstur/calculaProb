package calculaProb.beans;

public class Resultado {
	private String equipo;
	private double correctas;
	private double golaveraje;

	public Resultado(String equipo, double correctas, double golaveraje) {
		super();
		this.equipo = equipo;
		this.correctas = correctas;
		this.golaveraje = golaveraje;
	}

	public String getEquipo() {
		return equipo;
	}

	public void setEquipo(String equipo) {
		this.equipo = equipo;
	}

	public double getCorrectas() {
		return correctas;
	}

	public void setCorrectas(double correctas) {
		this.correctas = correctas;
	}

	public double getGolaveraje() {
		return golaveraje;
	}

	public void setGolaveraje(double golaveraje) {
		this.golaveraje = golaveraje;
	}

	@Override
	public String toString() {
		return String.format("-> %s - Lo consigue: %.2f %% - Se decide por golaveraje: %.2f %%", equipo, correctas, golaveraje);
	}
	
	public String toMachineFriendly(char separator) {
		String base = String.format("%s\t%.4f\t%.4f", equipo,correctas,golaveraje);
		return base.replaceAll("\t", String.valueOf(separator));
	}

}
