package calculaProb.estrategias_generacion;

import java.util.ArrayList;
import java.util.List;

import calculaProb.CalculadorPartidos;
import calculaProb.beans.Clasificacion;
import calculaProb.beans.Partido;

public class EstrategiaBacktracking implements EstrategiaGeneracion {

	private CalculadorPartidos cp;
	
	
	
	private void backtracking(Clasificacion c, List<Partido> p) {
		cp.addGenerada();
		if(cp.getGeneradas()%100000==0) {
			long fin = System.currentTimeMillis();
			System.out.println("generadas: "+cp.getGeneradas()+" en "+(fin-cp.getInicial())/1000+" s = "+cp.getGeneradas()/(fin-cp.getInicial())+" gen/s");
		
		}if(p.isEmpty()) {
			cp.addSolucion(c);
		}
		else
		{
			for (int i = 0; i < 3; i++) {
				Partido par = p.get(p.size()-1);
				p.remove(p.size()-1);
				par.setResultado(i);
				c.addPartido(par);
				backtracking(c.clone(), clonarPartidos(p));
				c.restarPartido(par);
				p.add(par);
			}
			
		}
		
		
	}

	private List<Partido> clonarPartidos(List<Partido> p) {
		List<Partido> result = new ArrayList<>();
		for (Partido partido : p) {
			result.add(partido.clone());
		}
		return result;
	}

	@Override
	public void run(CalculadorPartidos cp) {
		this.cp=cp;
		backtracking(cp.getC(), cp.getPartidos());
	}
}
