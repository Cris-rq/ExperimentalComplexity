import java.util.List;
import java.util.Arrays;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class Analizador {

	public static final int NUM_EJECUCIONES = 3;
	public static final int NUM_DATOS = 10;
	public static final int NUM_FUNCIONES = Funcion.Funciones.values().length;
	public static final int TAMANO_DATOS_INICIAL = 10;
	public static final int MULTIPLICADOR_TAMANO = 100;

	public static Timer timer = new Timer();
    public static TimerTask exitApp = new TimerTask() {
		public void run() {
			System.out.print("2N");
			System.exit(0);
		}
	};

 	public static void main(String[] args){	
		timer.schedule(exitApp, new Date(System.currentTimeMillis()+10000));
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

		
		System.out.println(Funcion.enumFuncionToString(complejidad));
		timer.cancel();
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
}