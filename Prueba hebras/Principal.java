public class Principal{
    public static void main(String[] args) {
        try{
            VariableComp var = new VariableComp();
            Hebra[] hebra = new Hebra[10];
            
            double[] vector = var.inicializarArray();
            
            for(int i = 0; i<hebra.length;i++){
                hebra[i] = new Hebra(var,i);
            }

            for(int i = 0; i < hebra.length;i++){
                hebra[i].start();
               // hebra[i].join();
            }
            vector = VariableComp.devolverArr();
            for(double n : vector){
                System.out.print(n + "  ");
            }
        } catch(Exception e){

        }
    }
    

}