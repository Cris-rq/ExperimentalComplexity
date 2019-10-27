import java.util.List;
import java.util.Arrays;

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
	public static final int NUM_FUNCIONES = Funcion.Funciones.values().length;
	public static final int TAMANO_DATOS_INICIAL = 10;
	public static final int MULTIPLICADOR_TAMANO = 100000;


	public static void main(String[] args) {
		long tiempo1 = System.currentTimeMillis(); 

		Debugger.mostrar("MATRIZ:");
		long [][] matriz = tiemposEjecucion(TAMANO_DATOS_INICIAL, MULTIPLICADOR_TAMANO);
		Debugger.mostrarMatriz(matriz);
		Debugger.mostrar("MINIMOS:");

		double [] vectorMinimos = Estadistica.minimoPorColumna(matriz, NUM_EJECUCIONES, NUM_DATOS);

		Debugger.mostrarVector(vectorMinimos);
		Debugger.mostrar("DESVIACIONES:");

		double [] vectorDesviaciones = dispersionLimitesFunciones(vectorMinimos);

		Debugger.mostrarVector(vectorDesviaciones);

		Funcion.Funciones complejidad = determinarComplejidad(vectorDesviaciones);
		
		long tiempo2 = System.currentTimeMillis(); 
		double temp = tiempo2-tiempo1;

		Debugger.mostrar("Tiempo transcurrido: " + temp/1000);

		/* SALIDA */
		System.out.println(Funcion.enumFuncionToString(complejidad));
	}

	/*  tiemposEjecucion()
		Ejecuta el ALGORITMO para distintos tamaños de datos de entrada (NUM_DATOS) repetidas veces (NUM_EJECUCIONES).
		Guarda los tiempos de ejecucion para cada tamaño de datos en una matriz de (NUM_EJECUCIONES x NUM_DATOS) celdas.
	*/
	public static long[][] tiemposEjecucion(int tamanoDatosInicial, int multiplicadorTamano){
		long [][] matrizDeTiempos = new long[NUM_EJECUCIONES][NUM_DATOS];
		Temporizador temp = new Temporizador(0);
		int tamanoInicial = tamanoDatosInicial;

		for(int fil = 0; fil < NUM_EJECUCIONES; fil++){
			tamanoInicial = tamanoDatosInicial;
			for(int col = 0; col < NUM_DATOS; col++){
				temp.iniciar();
				Algoritmo.f(tamanoInicial*multiplicadorTamano); // Ejecuito el algoritmo y guardo el tiempo de ejecucion
				temp.parar();
				long tiempoEjecucion = temp.tiempoPasado();

				matrizDeTiempos[fil][col] = tiempoEjecucion; // Guardo el tiempo en la correspondiente celda de la matriz
				
				temp.reiniciar();
				tamanoInicial++;
			}
			
		}

		return matrizDeTiempos;
	}

	/*	dispersionLimitesFunciones
		Dado un vector, va a calcular el límite de este para cada una de
		las funciones y devolverá un vector resultante con el coeficiente de variación.
	*/
	public static double[] dispersionLimitesFunciones(double [] vectorTiempos){
		double [] vector = new double[NUM_DATOS];
		double [] vectorCoeficiente = new double[NUM_FUNCIONES];
		long n = TAMANO_DATOS_INICIAL;

		for(int func = 0; func < NUM_FUNCIONES; func++){
			n = TAMANO_DATOS_INICIAL;
			Funcion funcion = new Funcion(func);
			for(int pos = 0; pos < NUM_DATOS; pos++){
				vector[pos] = vectorTiempos[pos] / funcion.calcular(n);
				n++;
			}
			vectorCoeficiente[func] = Estadistica.coeficienteDeVariacion(vector);
		}
		
		return vectorCoeficiente;
	}

	public static Funcion.Funciones determinarComplejidad(double [] vectorDesviaciones){
		int index  = -1;
		double minimo = Estadistica.minimoVector(vectorDesviaciones);

		for(int i = 0; i < vectorDesviaciones.length; i++){
			if(minimo == vectorDesviaciones[i]){
				index = i;
			}
		};

		return Funcion.Funciones.values()[index];
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
	 */

}