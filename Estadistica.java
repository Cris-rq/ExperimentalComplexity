public class Estadistica{

    /* Clase que recoge distintas funciones de matemática estadística que nos
       servirán en ciertas partes del código principal */

    public static double media(double [] datos){
        double media = 0;
        for(double n : datos){
            media = media + n;
        }
        media = media / datos.length;
        return media;
    }

    public static double[] mediaPorColumna(long [][] matriz, int filasMatriz, int columnasMatriz){
        double [] vector = new double[filasMatriz];
        double [] vectorMedias = new double[columnasMatriz];

		for(int col = 0; col < columnasMatriz; col++){
            for(int fil = 0; fil < filasMatriz; fil++){
                vector[fil] = (double) matriz[fil][col];
            }
            vectorMedias[col] = media(vector);
        }

        return vectorMedias;
	}

    public static double varianza(double [] datos){
        double var = 0;
        double media = media(datos);

        for(int i = 0; i < datos.length; i++){
            double rango = Math.pow(datos[i] - media,2);
            var = var + rango;
        }

        var = var / datos.length;
        return var;
    }

    public static int moda(int a[]) {
        int maxValue = a[0], maxCount = 0;
    
        for (int i = 0; i < a.length; ++i) {
            int count = 0;
            for (int j = 0; j < a.length; ++j) {
               
               
                if (a[j] == a[i]) ++count;
            }
            if (count > maxCount) {
                maxCount = count;
                maxValue = a[i];
            }
        }
    
        return maxValue;
    }

    public static double desviacionTipica(double [] datos){
        double desv = Math.sqrt(varianza(datos));
        return desv;
    }

    public static double coeficienteDeVariacion(double[] datos){
        double coeficiente = desviacionTipica(datos) / media(datos);
        return coeficiente;
    }

    public static double[] minimoPorColumna(long [][] matriz, int filasMatriz, int columnasMatriz){
        double [] vector = new double[filasMatriz];
        double [] minimos = new double[columnasMatriz];

        for(int col = 0; col < columnasMatriz; col++){
            for(int fil = 0; fil < filasMatriz; fil++){
                vector[fil] = matriz[fil][col];
            }
            minimos[col] = minimoVector(vector);
        }

        return minimos;
    }

    public static double minimoVector(double [] vector){
        double min = vector[0];
        
        for(double num : vector){
            if(num < min){
                min = num;
            }
        }

        return min;
    }
    
    
}