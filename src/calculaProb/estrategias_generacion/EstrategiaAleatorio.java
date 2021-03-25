package calculaProb.estrategias_generacion;

import java.util.Random;

import calculaProb.CalculadorPartidos;
import calculaProb.beans.Clasificacion;
import calculaProb.beans.Partido;

public class EstrategiaAleatorio implements EstrategiaGeneracion {

	private int iteraciones;
	private Clasificacion inicial;
	
	

	public EstrategiaAleatorio(int iteraciones, Clasificacion inicial) {
		super();
		this.iteraciones = iteraciones;
		this.inicial = inicial;
	}



	@Override
	public void run(CalculadorPartidos cp) {
		Random r = new Random();

		for (int i = 0; i < iteraciones; i++) {
			Clasificacion clonada = inicial.clone();
			for (int p = 0; p < cp.getPartidos().size(); p++) {
				Partido part = cp.getPartidos().get(p).setResultado(r.nextInt(3));
				clonada.addPartido(part);
			}
			cp.addSolucion(clonada);
		}

	}

}
