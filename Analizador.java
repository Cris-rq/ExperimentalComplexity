import java.util.List;
import java.util.Arrays;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class Analizador {

	public static final int NUM_EJECUCIONES = 3;
	public static final int NUM_COMPLEJIDADES = 3;
	public static final int NUM_DATOS = 10;
	public static final int NUM_FUNCIONES = Funcion.Funciones.values().length;
	public static final long TAMANO_DATOS_INICIAL = 10;
	public static final long MULTIPLICADOR_TAMANO = 10;

	public static Timer timer = new Timer();
    public static TimerTask exitApp = new TimerTask() {
		public void run() {
			System.exit(0);
		}
	};

	public static void main(String[] args){
		timer.schedule(exitApp, new Date(System.currentTimeMillis()+9500));

		Funcion.Funciones [][] complejidadPorMultArr = new Funcion.Funciones[][];
		for(int mult = 1; true; mult = mult*10){
			Funcion.Funciones auxCompArr = new Funcion.Funciones[0];
			for(int i = 0; i < NUM_COMPLEJIDADES; i++){
				Funcion.Funciones comp = complejidad();

				auxCompArr = Arrays.copyOf(auxCompArr, auxCompArr.length + 1);
				auxCompArr[i] = comp;
			}
		}

		Funcion.Funciones [] complejidadArr = new Funcion.Funciones[NUM_COMPLEJIDADES];

		int [] complejidadIndexArr = new int[complejidadArr.length];
		for(int i = 0; i < complejidadIndexArr.length; i++){
			complejidadIndexArr[i] = complejidadArr[i].ordinal();
		}
		
		int complejidadIndex = Estadistica.moda(complejidadIndexArr);
		
		Funcion.Funciones complejidad = Funcion.Funciones.values()[complejidadIndex];
		System.out.println(Funcion.enumFuncionToString(complejidad));
		
		timer.cancel(); //POSIBLEMENTE SE QUITE
	} 

	private Funcion.Funciones complejidad(){
		long tiempo1 = System.currentTimeMillis();
		Debugger debugger = new Debugger();
		debugger.desactivar();

		long [][] matriz = tiemposEjecucion(TAMANO_DATOS_INICIAL, MULTIPLICADOR_TAMANO);
		debugger.mostrar("MATRIZ:");
		debugger.mostrarMatriz(matriz);
		
		double [] vectorMinimos = Estadistica.minimoPorColumna(matriz, NUM_EJECUCIONES, NUM_DATOS);
		debugger.mostrar("MINIMOS:");
		debugger.mostrarVector(vectorMinimos);

		double [] vectorDesviaciones = dispersionLimitesFunciones(vectorMinimos);
		debugger.mostrar("DESVIACIONES:");
		debugger.mostrarVector(vectorDesviaciones);

		Funcion.Funciones complejidad = determinarComplejidad(vectorDesviaciones);

		long tiempo2 = System.currentTimeMillis(); 
		double temp = tiempo2-tiempo1;

		debugger.mostrar("Tiempo transcurrido: " + temp/1000);
		debugger.mostrar(Funcion.enumFuncionToString(complejidad));

		return complejidad;
	}

	/*  tiemposEjecucion()
		Ejecuta el ALGORITMO para distintos tamaños de datos de entrada (NUM_DATOS) repetidas veces (NUM_EJECUCIONES).
		Guarda los tiempos de ejecucion para cada tamaño de datos en una matriz de (NUM_EJECUCIONES x NUM_DATOS) celdas.
	*/
	private static long[][] tiemposEjecucion(long tamanoDatosInicial, long multiplicadorTamano){
		long [][] matrizDeTiempos = new long[NUM_EJECUCIONES][NUM_DATOS];
		Temporizador temp = new Temporizador(0);
		long tamanoInicial = tamanoDatosInicial;

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
	private static double[] dispersionLimitesFunciones(double [] vectorTiempos){
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

	private static Funcion.Funciones determinarComplejidad(double [] vectorDesviaciones){
		int index  = -1;
		double minimo = Estadistica.minimoVector(vectorDesviaciones);

		for(int i = 0; i < vectorDesviaciones.length; i++){
			if(minimo == vectorDesviaciones[i]){
				index = i;
			}
		};

		return Funcion.Funciones.values()[index];
	}

}