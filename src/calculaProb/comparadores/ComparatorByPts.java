package calculaProb.comparadores;
import java.util.Comparator;

import calculaProb.beans.EquipoEnUnaClasificacion;

public class ComparatorByPts implements Comparator<EquipoEnUnaClasificacion> {

	@Override
	public int compare(EquipoEnUnaClasificacion arg0, EquipoEnUnaClasificacion arg1) {
		return arg1.getPuntos()-arg0.getPuntos();
	}

}
