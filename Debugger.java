import java.util.List;

public class Debugger{

    private Boolean estaActivado;

    public Debugger(){
        estaActivado = true;
    }

    public void activar(){
        estaActivado = true;
    }

    public void desactivar(){
        estaActivado = false;
    }

    public void mostrarComplejidad(Funcion.Funciones func){
        if(estaActivado) System.out.println("Complejidad: " + func.name());
    }

    public void mostrar(String texto){
        if(estaActivado) System.out.println(texto);
    }

    public void saltoLinea(){
        if(estaActivado) System.out.println("");
    }

    public void mostrarVector(double [] vec){
        if(estaActivado){
            for(double n : vec){
                mostrar(String.valueOf(n));
            }
        }
    }

    public void mostrarMatriz(long [][] matriz){
        if(estaActivado){
            for(long [] v : matriz){
                for(long n : v){
                    System.out.print(n + "  ");
                }
                saltoLinea();
            }
        }
    }

}