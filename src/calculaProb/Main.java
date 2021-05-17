package calculaProb;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import calculaProb.beans.Clasificacion;
import calculaProb.beans.EquipoEnUnaClasificacion;
import calculaProb.beans.Partido;
import calculaProb.beans.Resultado;
import calculaProb.estrategias_generacion.EstrategiaAleatorio;

public class Main {

	public static void main(String[] args) {
		System.out.println(getPrologo());
		System.out.println("CALCULANDO...");
		
		Comparator<EquipoEnUnaClasificacion> ordenarPor = Clasificacion.ORDENAR_POR_PUNTOS;
		
		// Carga la Clasificación Actual desde el archivo clas.txt
		Clasificacion c = cargarClasificacion("files/clas.txt", ordenarPor, ";");
		// Generar partidos desde el archivo grupos.txt
		//generarPartidosDesdeGrupo("files/grupos.txt", "files/partidos.txt");
		// Carga los partidos del archivo partidos.txt
		List<Partido> p = cargarPartidos("files/partidos.txt");

		// Posiciones para las que calcular la probabilidad
		int posicionInicial = 1;
		int posicionFinal = 4;
		List<Integer> list = new ArrayList<>();
		for (int i = posicionInicial; i <= posicionFinal; i++) {
			list.add(i);
		}
		// Llamada al calculador de partidos
		CalculadorPartidos b = new CalculadorPartidos(c, p, list, 2, new EstrategiaAleatorio(1000000, c), false,ordenarPor);

		System.out.println("Clasificaciones generadas = " + b.getSoluciones().size());

		List<Resultado> resultados = c.getEquipos().stream().map(eqp -> b.getOpcionesDe(eqp.getEquipo(), list))
				.collect(Collectors.toList());
		resultados.sort((o1, o2) -> Double.compare(o2.getCorrectas(), o1.getCorrectas()));

		System.out.println("-------------RESULTADO-------------");
		
		resultados.stream().forEach(r -> System.out.println(r.toString()));
		
		System.out.println("-----------------------------------");
		resultados.stream().forEach(r -> System.out.println(r.toMachineFriendly('\t')));

	}

	private static String getPrologo() {
		StringBuilder sb = new StringBuilder();

		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(new FileInputStream("files/prolog.txt"), "UTF-8"));
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	private static void generarPartidosDesdeGrupo(String filenameIn, String filenameOut) {
		List<String> partidos = new ArrayList<>();
		List<String> equiposA = new ArrayList<String>();
		List<String> equiposB = new ArrayList<String>();
		boolean a = true;

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filenameIn), "UTF-8"));
			String line = null;
			while ((line = br.readLine()) != null) {

				if ("-".equals(line)) {
					a = false;
					continue;
				}

				if (a) {
					equiposA.add(line);
				} else {
					equiposB.add(line);
				}

			}

			for (String eq1 : equiposA) {
				for (String eq2 : equiposB) {
					partidos.add(eq1 + ";" + eq2);
					partidos.add(eq2 + ";" + eq1);
				}
			}

			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			BufferedWriter br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filenameOut), "UTF-8"));

			for (String partido : partidos) {
				br.append(partido);
				br.newLine();
			}

			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Clasificacion cargarClasificacion(String filename,Comparator<EquipoEnUnaClasificacion> ordenarPor, String separador) {
		Clasificacion c = new Clasificacion(ordenarPor);
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] fields = line.split(separador);
				EquipoEnUnaClasificacion eqc = new EquipoEnUnaClasificacion(fields[0], Integer.parseInt(fields[1]),
						Integer.parseInt(fields[2]), Integer.parseInt(fields[3]));
				c.addEquipo(eqc);
			}

			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;

	}

	public static List<Partido> cargarPartidos(String filename) {
		List<Partido> ps = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] fields = line.split(";");
				Partido p = new Partido(fields[0], fields[1]);
				ps.add(p);
			}

			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ps;

	}
}
