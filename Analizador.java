
public class Analizador {
	
	/* 
	 * NOTA IMPORTANTE
	 * 
	 * Esta clase se proporciona solamente para ilustrar el formato de
	 * salida que deberia tener la solucion a este ejericio.
	 * Esta clase debe modificarse completamente para cumplir 
	 * mínimamente los requisitos de esta practica.
	 * Notese que ni siquiera esta completa......
	 */ 
	
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

	public static void main(String[] args) {
		long t1 = System.currentTimeMillis();	

		long matriz[][] = calcularMatrizTiempos();
		mostrarTabla(matriz);

		long t2 = System.currentTimeMillis();
		long temp = t2-t1;

		System.out.println("---- " + (long) Math.ceil(temp/1000));
	}

	public static long[][] calcularMatrizTiempos(){
		long matrizTiempos[][] = new long[NUM_EJECUCIONES][NUM_DATOS];

		for(int i = 0; i < NUM_EJECUCIONES; i++){
			for(int n = NUM_DATOS; n < 2*NUM_DATOS; n++){ //n = tam datos
				Temporizador t = new Temporizador(1);
				t.iniciar();
				Algoritmo.f(n*((long) Math.pow(10,3))); //
				t.parar();
				long tiempo = t.tiempoPasado();
				matrizTiempos[i][n-NUM_DATOS] = tiempo;
				t.reiniciar();
			}
		}

		return matrizTiempos;
	}

	public static long[] calcularVectorMediaTiempos(long matriz[][]){
		long [] vector = new long [NUM_EJECUCIONES]; 
		
		// QUIERO JUGAR AL LOL :3 <3

		for(int i = 0; i < NUM_DATOS; i++){
			int sum = 0;
			double media = 0;
				for(int j = 0; j < NUM_EJECUCIONES; j++){
					sum += matriz[j][i];
				}
			media = sum / NUM_EJECUCIONES;
			vector[i] = media;
			
		}
	}

	public static long[] calcularVectorMinimoTiempos(long matriz[][]){
		long vector[] = new long[NUM_DATOS];
		long aux[] = new long[NUM_EJECUCIONES];

		for(int i = 0; i < NUM_DATOS; i++){
			for(int j = 0; j < NUM_EJECUCIONES; j++){
				aux[j] = matriz[j][i];
			}
			vector[i] = calcularMinimoVector(aux);
		}

		return vector;
	}

	private static long caclularMinimoVector(long vector[]){
		long minimo = vector[0];
		for(long n : vector){
			if(minimo < v){
				minimo = v;
			}
		}
		return minimo;
	}

	public static void mostrarTabla(long tabla[][]){
		System.out.println("---- TABLA ----");	

		for(int i = 0; i < NUM_EJECUCIONES; i++){
			for(int j = NUM_DATOS; j < 2*NUM_DATOS; j++){
				System.out.print(tabla[i][j-NUM_DATOS] + "  ");
			}
			System.out.println("\n");
		}
	}
}
