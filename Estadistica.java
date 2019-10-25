public class Estadistica{

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

    public static double desviacionTipica(double [] datos){
        double desv = Math.sqrt(varianza(datos));
        return desv;
    }

    public static long[] minimoPorColumna(long [][] matriz, int filasMatriz, int columnasMatriz){
        long [] vector = new long[filasMatriz];
        long [] minimos = new long[columnasMatriz];

        for(int col = 0; col < columnasMatriz; col++){
            for(int fil = 0; fil < filasMatriz; fil++){
                vector[fil] = matriz[fil][col];
            }
            minimos[col] = minimoVector(vector);
        }

        return minimos;
    }

    public static long minimoVector(long [] vector){
        long min = vector[0];
        
        for(long num : vector){
            if(num < min){
                min = num;
            }
        }

        return min;
    }
    
    
}