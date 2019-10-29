public class VariableComp{

    public static double[] arr = new double[10];
    private long multiplicador = 1000;

    public double[] inicializarArray(){
        for(int i = 0; i< arr.length; i++){
            arr[i] = 0;
        }
        return arr;
    }

    public synchronized void accederAlArray(int i){
        long tiempo1 = System.currentTimeMillis();
        Algoritmo.f(i*multiplicador);
        long tiempo2 = System.currentTimeMillis();
  
        arr[i] = (double) (tiempo2-tiempo1);
    }

    public static double[] devolverArr(){
        return arr;
    }

}