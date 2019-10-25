import java.util.List;
import java.util.Arrays;
import org.apache.commons.lang3.ArrayUtils;

public class Analizador {
	
/* 	public static String masCercano(double ratio) {
			if (ratio < 1.5) {                      // aprox 1.0
				return "1";							
			} else if (1 <= ratio && ratio < 3.0) { // aprox 2.0
				return "N";
			} else if (3 <= ratio && ratio < 6.0) { // aprox 4.0
				return "N2";
			} else if (6 <= ratio && ratio < 10.0) { // aprox 8.0
				return "N3";
			} else { 								 // otras
				return "NF";
			}
	} */
	
/* 	public static void main(String arg[]) {
		int n1 = 100000;
		int n2 = 200000;
		Temporizador t = new Temporizador();
		t.iniciar();
		Algoritmo.f(n1);
		t.parar();
		long t1 = t.tiempoPasado();
		t.reiniciar();
		t.iniciar();
		Algoritmo.f(n2);
		t.parar();
		long t2 = t.tiempoPasado();
		double ratio = (double)t2/t1;
		System.out.println(masCercano(ratio));
	} */

	public static final int NUM_EJECUCIONES = 5;
	public static final int NUM_DATOS = 10;
	public static final int NUM_FUNCIONES = Funcion.funciones.values().length;
	public static final int TAMANO_DATOS_INICIAL = 10;
	public static final int MULTIPLICADOR_TAMANO = Math.pow(10,3);


	public static void main(String[] args) {
		long tiempo1 = System.currentTimeMilis(); 
		// double vectorMedias[] = mediaPorColumna(tiemposEjecucion());
		long vectorMinimos[] = Estadistica.minimoPorColumnas(tiemposEjecucion(TAMANO_DATOS_INICIAL, MULTIPLICADOR_TAMANO));
		double minimaDesviacion = Estadistica.minimoVector(dispersionLimitesFunciones(vectorMinimos));
		Funcion.funciones complejidad = determinarComplejidad(minimaDesviacion);
	}

	/*  tiemposEjecucion()
		Ejecuta el ALGORITMO para distintos tamaños de datos de entrada (NUM_DATOS) repetidas veces (NUM_EJECUCIONES).
		Guarda los tiempos de ejecucion para cada tamaño de datos en una matriz de (NUM_EJECUCIONES x NUM_DATOS) celdas.
	*/
	public static long[][] tiemposEjecucion(int tamanoDatosInicial, int multiplicadorTamano){
		long [][] matrizDeTiempos = new long[NUM_EJECUCIONES][NUM_DATOS];
		Temporizador temp = new Temporizador(1);

		for(int fil = 0; fil < NUM_EJECUCIONES; fil++){
			for(int col = 0; col < NUM_DATOS; col++){
				temp.iniciar();
				Algoritmo.f(tamanoDatosInicial*multiplicadorTamano); // Ejecuito el algoritmo y guardo el tiempo de ejecucion
				temp.parar();
				long tiempoEjecucion = temp.tiempoPasado();

				matrizDeTiempos[fil][col] = tiempoEjecucion; // Guardo el tiempo en la correspondiente celda de la matriz

				temp.reiniciar();
			}
		}

		return matrizDeTiempos;
	}

	/*	dispersionLimitesFunciones
		Dado un vector, va a calcular el límite de este para cada una de
		las funciones y devolverá un vector resultante con las desviaciones típicas.
	*/
	public static long[] dispersionLimitesFunciones(long [] vectorTiempos){
		double [] vector = new double[NUM_DATOS];
		double [] vectorDesviacion = new double[NUM_DATOS]
		long n = TAMANO_DATOS_INICIAL;

		for(int func = 0; func < NUM_FUNCIONES; func++){
			n = TAMANO_DATOS_INICIAL;
			Funcion funcion = new Funcion(func);
			for(int pos = 0; pos < NUM_DATOS; pos++){
				vector[pos] = vectorTiempos[pos] / funcion.calcular(n);
				n++;
			}
			vectorDesviacion[func] = Estadistica.desviacionTipica(vector);
		}
		
		return vectorDesviacion;
	}

	public static Funcion.funciones determinarComplejidad(double [] vectorDispersiones){
		Funcion.funciones indexCompl = asList(vectorDispersiones).indexOf(Estadistica.minimoVector(vectorDispersiones));
		return indexCompl;
	}

	// ------------------------------------------------

/* 	public static void main(String[] args) {
		long t1 = System.currentTimeMillis();	

		long matriz[][] = calcularMatrizTiempos();
		mostrarTabla(matriz, NUM_EJECUCIONES, NUM_DATOS);
		System.out.println("---- MEDIA ----\n");
		double [] medias = calcularVectorMediaTiempos(matriz);
		mostrarVector(medias);
		System.out.println("\n---- MINIMO ----\n");
		mostrarVector(calcularVectorMinimoTiempos(matriz));
		System.out.println("\n---- LIMITES ----\n");
		double[][] matrizLim = calcularMatrizLimites(medias);
		mostrarTablaD(matrizLim, NUM_FUNCIONES, NUM_DATOS);
		System.out.println("\n---- DESVIACION TIPICA ----\n");
		mostrarDesviaciones(matrizLim);
		
		long t2 = System.currentTimeMillis();
		long temp = t2-t1;

		System.out.println("---- " + (long) Math.ceil(temp/1000));
	}

	public static long[][] calcularMatrizTiempos(){
		long matrizTiempos[][] = new long[NUM_EJECUCIONES][NUM_DATOS];

		for(int i = 0; i < NUM_EJECUCIONES; i++){
			for(int n = INICIO_DATOS; n < FINAL_DATOS; n++){
				Temporizador t = new Temporizador(1);
				t.iniciar();
				Algoritmo.f(n*((long) Math.pow(10,3))); 
				t.parar();
				long tiempo = t.tiempoPasado();
				matrizTiempos[i][n-INICIO_DATOS] = tiempo;
				t.reiniciar();
			}
		}

		return matrizTiempos;
	}

	public static double[] calcularVectorMediaTiempos(long matriz[][]){
		double [] vector = new double[NUM_DATOS]; 

		for(int i = 0; i < NUM_DATOS; i++){
			int sum = 0;
			double media = 0;
				for(int j = 0; j < NUM_EJECUCIONES; j++){
					sum += matriz[j][i];
				}
			media = (double) sum / NUM_EJECUCIONES;
			vector[i] = media;
			
		}

		return vector;

	}

	public static double[] calcularVectorMinimoTiempos(long matriz[][]){
		double vector[] = new double[NUM_DATOS];
		long aux[] = new long[NUM_EJECUCIONES];

		for(int i = 0; i < NUM_DATOS; i++){
			for(int j = 0; j < NUM_EJECUCIONES; j++){
				aux[j] = matriz[j][i];
			}
			vector[i] = calcularMinimoVector(aux);
		}

		return vector;
	}

	private static double calcularMinimoVector(long vector[]){
		double minimo = vector[0];
		for(long v : vector){
			if(minimo > v){
				minimo = v;
			}
		}
		return minimo;
	}

	public static void mostrarTabla(long [][] tabla, long filas, long columnas){
		System.out.println("---- TABLA ----\n");	

		for(int i = 0; i < filas; i++){
			for(int j = 0; j < columnas; j++){
				System.out.print(tabla[i][j] + "  ");
			}
			System.out.println("\n");
		}
	}

	public static void mostrarTablaD(double [][] tabla, long filas, long columnas){
	//	System.out.println("---- TABLA ----\n");	

		for(int i = 0; i < filas; i++){
			for(int j = 0; j < columnas; j++){
				System.out.printf("%.5f  ", tabla[i][j]);
			}
			System.out.println("\n");
		}
	}

	public static void mostrarVector(double [] vector){
		for(double d : vector){
			System.out.print(d + "  ");
		}
	}

	public static double[][] calcularMatrizLimites(double vector[]){
		double matriz [][] = new double [NUM_FUNCIONES][NUM_DATOS];

		for(int i = 0; i < NUM_FUNCIONES; i++){
			for(int j = 0; j < NUM_DATOS; j++){

				switch(i){
					case 0: 
						matriz[i][j] = vector[j]/funcLineal((j+INICIO_DATOS));
						break;
					case 1:
						matriz[i][j] = vector[j]/funcCuadratica((j+INICIO_DATOS));
						break;
					case 2:
						matriz[i][j] = vector[j]/funcCubica((j+INICIO_DATOS));
						break;
				}
			}
		}

		return matriz;
	}

	public static void mostrarDesviaciones(double[][] datos){
		for(int i = 0; i<NUM_FUNCIONES; i++){
			System.out.println(Estadistica.desviacionTipica(datos[i]));
		}
	}

	

	private static double funcLineal(double tamDatos){
		return tamDatos;
	}
	
	private static double funcCuadratica(double tamDatos){
		return Math.pow(tamDatos,2);
	}

	private static double funcCubica(double tamDatos){
		return Math.pow(tamDatos,3);
	}
	 */

}
