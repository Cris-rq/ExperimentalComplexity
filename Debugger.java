import java.util.List;

public class Debugger{

    public static void mostrarComplejidad(Funcion.Funciones func){
        System.out.println("Complejidad: " + func.name());
    }

    public static void mostrar(String texto){
        System.out.println(texto);
    }

    public static void saltoLinea(){
        System.out.println("");
    }

    public static void mostrarVector(double [] vec){
        for(double n : vec){
            Debugger.mostrar(String.valueOf(n));
        }
    }

    public static void mostrarMatriz(long [][] matriz){
        for(long [] v : matriz){
            for(long n : v){
                System.out.print(n + "  ");
            }
            Debugger.saltoLinea();
        }
    }

}