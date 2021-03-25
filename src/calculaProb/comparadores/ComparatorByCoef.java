package calculaProb.comparadores;

import java.util.Comparator;

import calculaProb.beans.EquipoEnUnaClasificacion;

public class ComparatorByCoef implements Comparator<EquipoEnUnaClasificacion> {

	@Override
	public int compare(EquipoEnUnaClasificacion o1, EquipoEnUnaClasificacion o2) {
		double coef1 = o1.getPuntos()/(double)o1.getPartidos();
		double coef2 = o2.getPuntos()/(double)o2.getPartidos();
		return Double.compare(coef2, coef1);
	}

}
