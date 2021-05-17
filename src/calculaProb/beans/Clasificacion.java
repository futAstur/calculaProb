package calculaProb.beans;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import calculaProb.comparadores.ComparatorByCoef;
import calculaProb.comparadores.ComparatorByPts;

public class Clasificacion {

	private List<EquipoEnUnaClasificacion> equipos = new ArrayList<>();
	
	public static final Comparator<EquipoEnUnaClasificacion> ORDENAR_POR_PUNTOS = new ComparatorByPts();
	public static final Comparator<EquipoEnUnaClasificacion> ORDENAR_POR_COEFICIENTE = new ComparatorByCoef();
	private Comparator<EquipoEnUnaClasificacion> ordenarPor;

	public Clasificacion(Comparator<EquipoEnUnaClasificacion> ordenarPor) {
		this.ordenarPor = ordenarPor;
	}
	
	public List<EquipoEnUnaClasificacion> getEquipos(Comparator<EquipoEnUnaClasificacion> sortMethod) {
		for (EquipoEnUnaClasificacion eq : equipos) {
			eq.colocarPuntos();
		}
		equipos.sort(sortMethod);

		return equipos;
	}
	
	public List<EquipoEnUnaClasificacion> getEquipos(){
		return equipos;
	}

	public void addEquipo(EquipoEnUnaClasificacion equipo) {
		equipos.add(equipo);
	}

	public void addPartido(Partido p) {
		for (EquipoEnUnaClasificacion eq : equipos) {
			if (eq.getEquipo().equals(p.getLocal())) {
				if (p.getResultado() == Partido.V)
					eq.addVictorias(1);
				else if (p.getResultado() == Partido.E)
					eq.addEmpates(1);
				else
					eq.addDerrotas(1);
			}
			if (eq.getEquipo().equals(p.getVisitante())) {
				if (p.getResultado() == Partido.V)
					eq.addDerrotas(1);
				else if (p.getResultado() == Partido.E)
					eq.addEmpates(1);
				else
					eq.addVictorias(1);

			}
		}
	}

	public void restarPartido(Partido p) {
		for (EquipoEnUnaClasificacion eq : equipos) {
			if (eq.getEquipo().equals(p.getLocal())) {
				if (p.getResultado() == Partido.V)
					eq.addVictorias(-1);
				else if (p.getResultado() == Partido.E)
					eq.addEmpates(-1);
				else
					eq.addDerrotas(-1);
			}
			if (eq.getEquipo().equals(p.getVisitante())) {
				if (p.getResultado() == Partido.V)
					eq.addDerrotas(-1);
				else if (p.getResultado() == Partido.E)
					eq.addEmpates(-1);
				else
					eq.addVictorias(-1);

			}
		}

	}

	public String toString() {
		String result = "";
		List<EquipoEnUnaClasificacion> eqps = getEquipos(ordenarPor);
		for (int i = 0; i < eqps.size(); i++) {
			result += (i + 1) + " -> " + eqps.get(i).getEquipo() + " - " + eqps.get(i).getPuntos() + " pts\n";
		}
		return result;
	}

	public Clasificacion clone() {
		Clasificacion c = new Clasificacion(ordenarPor);
		for (EquipoEnUnaClasificacion equipoEnUnaClasificacion : equipos) {
			c.addEquipo(equipoEnUnaClasificacion.clone());
		}
		return c;
	}

	public Map<String, Integer> mapaPosiciones() {
		Map<String, Integer> map = new HashMap<>();
		List<EquipoEnUnaClasificacion> eqps = getEquipos(Clasificacion.ORDENAR_POR_PUNTOS);
		int posicion = 1;
		int pos_temp = 1;
		int ptsAnt=-1;
		for (int i = 0; i < eqps.size(); i++) {
			if (i == 0) {
				map.put(eqps.get(i).getEquipo(), 1);
				ptsAnt=eqps.get(i).getPuntos();
			} else {
				if(eqps.get(i).getPuntos()==ptsAnt) {
					pos_temp++;
					map.put(eqps.get(i).getEquipo(), posicion);
				}
				else 
				{
					pos_temp++;
					posicion=pos_temp;
					map.put(eqps.get(i).getEquipo(), posicion);
					ptsAnt=eqps.get(i).getPuntos();
				}
			}
			
			
		}
		return map;
	}

}