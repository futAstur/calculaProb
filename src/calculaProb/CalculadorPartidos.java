package calculaProb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import calculaProb.beans.Clasificacion;
import calculaProb.beans.EquipoEnUnaClasificacion;
import calculaProb.beans.Partido;
import calculaProb.beans.Resultado;
import calculaProb.estrategias_generacion.EstrategiaGeneracion;

public class CalculadorPartidos {

	private Clasificacion c;
	private List<Partido> partidos;
	private int generadas = 0;
	private long inicial = 0;
	private List<Clasificacion> soluciones = new ArrayList<>();
	private Comparator<EquipoEnUnaClasificacion> ordenarPor;

	/**
	 * Imprime por pantalla las probabilidades
	 * 
	 * @param c                   - La Clasificación Inicial de la liga
	 * @param p                   - La lista de partidos a disputar
	 * @param posiciones          - Las posiciones para las que calcular la
	 *                            probabilidad. EJ: Posibilidad de quedar campeón =
	 *                            {1}, Posibilidad de quedar entre los dos primeros
	 *                            = {1,2}
	 * @param jornadas            - Numero de jornadas a disputar (sólo se usa si se
	 *                            desean seleccionar partidos)
	 * @param estrategia          - La estrategia de generación de posibilidades
	 * @param seleccionarPartidos - True, si se desean eliminar partidos no
	 *                            necesarios. Sólo funciona si se ordena por puntos.
	 * @param ordenarPor          - Criterio de ordenación de clasificaciones
	 */
	public CalculadorPartidos(Clasificacion c, List<Partido> p, List<Integer> posiciones, int jornadas,
			EstrategiaGeneracion estrategia, boolean seleccionarPartidos,
			Comparator<EquipoEnUnaClasificacion> ordenarPor) {
		this.c = c;
		this.partidos = p;
		this.ordenarPor = ordenarPor;

		if (seleccionarPartidos)
			seleccionarPartidos(posiciones, jornadas);

		inicial = System.currentTimeMillis();

		estrategia.run(this);

		long fin = System.currentTimeMillis();
		System.out.println("Tiempo = " + (fin - inicial) / (double) 1000);

	}

	public Clasificacion getC() {
		return c;
	}

	public void setC(Clasificacion c) {
		this.c = c;
	}

	public List<Partido> getPartidos() {
		return partidos;
	}

	public void setPartidos(List<Partido> partidos) {
		this.partidos = partidos;
	}

	public int getGeneradas() {
		return generadas;
	}

	public void setGeneradas(int generadas) {
		this.generadas = generadas;
	}

	public long getInicial() {
		return inicial;
	}

	public void setInicial(long inicial) {
		this.inicial = inicial;
	}

	public List<Clasificacion> getSoluciones() {
		return soluciones;
	}

	public void setSoluciones(List<Clasificacion> soluciones) {
		this.soluciones = soluciones;
	}

	private void seleccionarPartidos(List<Integer> posiciones, int jornadas) {
		Collections.sort(posiciones);
		int puntosRestantes = jornadas * 3;
		int posSuperior = posiciones.get(0);
		int posInferior = posiciones.get(posiciones.size() - 1);
		List<EquipoEnUnaClasificacion> equipos = c.getEquipos(ordenarPor);
		int ptsSuperior = equipos.get(posSuperior - 1).getPuntos();
		int ptsInferior = equipos.get(posInferior - 1).getPuntos();

		List<String> enJuego = new ArrayList<>();
		for (EquipoEnUnaClasificacion equipo : equipos) {
			if (equipo.getPuntos() <= ptsSuperior + puntosRestantes
					&& equipo.getPuntos() >= ptsInferior - puntosRestantes) {
				enJuego.add(equipo.getEquipo());
				System.out.println("Equipo: " + equipo.getEquipo());
			}
		}

		List<Partido> definitivos = new ArrayList<>();

		for (Partido partido : partidos) {
			if (enJuego.contains(partido.getLocal()) || enJuego.contains(partido.getVisitante())) {
				definitivos.add(partido);
				System.out.println("Partido: " + partido.toString());
			}
		}
		partidos = definitivos;

		System.out.println("Equipos relevantes = " + enJuego.size() + " Partidos relevantes = " + partidos.size());
	}

	public Resultado getOpcionesDe(String equipo, int posicion) {
		List<Integer> posiciones = Arrays.asList(new Integer[] { posicion });
		return getOpcionesDe(equipo, posiciones);
	}

	public Resultado getOpcionesDe(String equipo, List<Integer> posicionesObjetivo) {
		int correctas = 0; // Clasificaciones en las que logra el objetivo
		int golaverage = 0; // Clasificaciones en las que se mira el golaveraje para lograr el objetivo

		for (Clasificacion clasificacion_final : soluciones) {
			Map<String, Integer> mapaEquipoPosicion = clasificacion_final.mapaPosiciones();
			int posicionEquipo = mapaEquipoPosicion.get(equipo);
			boolean equipoLograPosicionObjetivo = posicionesObjetivo.contains(posicionEquipo);

			if (equipoLograPosicionObjetivo
					&& seDecidePorGolaveraje(mapaEquipoPosicion, posicionEquipo, posicionesObjetivo)) {
				golaverage++;
			} else if (equipoLograPosicionObjetivo) {
				correctas++;
			}
		}

		return new Resultado(equipo, calcularPorcentajeSobreClasificacionesGeneradas(correctas),
				calcularPorcentajeSobreClasificacionesGeneradas(golaverage));

	}

	private double calcularPorcentajeSobreClasificacionesGeneradas(int valor) {
		return valor / (double) soluciones.size() * 100;
	}

	private boolean seDecidePorGolaveraje(Map<String, Integer> mapa, Integer posicion, List<Integer> posiciones) {
		int valores = 0;
		for (String key : mapa.keySet()) {
			int valor = mapa.get(key);
			if (valor == posicion) {
				valores++;
			}
		}

		int ultima_valida = posiciones.get(posiciones.size() - 1);
		return (posicion + valores - 1) > ultima_valida;
	}

	public void addGenerada() {
		generadas++;

	}

	public void addSolucion(Clasificacion c2) {
		soluciones.add(c2);
	}

}
