public class Funcion{

    public enum Funciones {F1, FLOGN, FN, FNLOGN, FN2, FN3, F2N, FNF;}
    private Funciones funcion;

    public Funcion(int funcion){
        this.funcion = Funciones.values()[funcion];
    }

    public double calcular(long numero){
        double calc = 0;

        switch(funcion){
            case F1:
                calc = 1;
                break;
            case FLOGN:
                calc = Math.log(numero)/Math.log(2);
                break;
            case FN:
                calc = numero;
                break;
            case FNLOGN:
                calc = numero*(Math.log(numero)/Math.log(2));
                break;
            case FN2:
                calc = Math.pow(numero,2);
                break;
            case FN3:
                calc = Math.pow(numero, 3);
                break;
            case F2N:
                calc = Math.pow(2, numero);
                break;
            case FNF:
                calc = factorial(numero);
                break;
        }

        return calc;
    }

    public static double factorial(long numero){
        double resultado;
        if(numero == 0){
            resultado = 1;
        }
        else{
            resultado = numero * factorial(numero-1);
        }

        return resultado;
    }

    public static String enumFuncionToString(Funciones funcion){
        String cadena = ""; 
        switch(funcion){
            case F1:
                cadena = "1";
                break;
            case FLOGN:
                cadena = "LOGN";
                break;
            case FN:
                cadena = "N";
                break;
            case FNLOGN:
                cadena = "NLOGN";
                break;
            case FN2:
                cadena = "N2";
                break;
            case FN3:
                cadena = "N3";
                break;
            case F2N:
                cadena = "2N";
                break;
            case FNF:
                cadena = "NF";
                break;
        }

        return cadena;
    }
}