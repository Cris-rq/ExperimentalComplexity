import java.util.*;
public class Hebra extends Thread{
    private VariableComp var;
    private int id;

    public Hebra (VariableComp var, int id){
        this.var = var;
        this.id = id;
    }
    public void run(){
        var.accederAlArray(id);
    }
}