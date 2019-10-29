public class VariableComp{

    public static double[] arr = new double[10];
    private double multiplicador = 10;

    public double[] inicializarArray(){
        for(int i = 0; i< arr.length; i++){
            arr[i] = 0;
        }
        return arr;
    }

    public synchronized void accederAlArray(int i){
        arr[i] = i*multiplicador;
    }

    public static double[] devolverArr(){
        return arr;
    }

}