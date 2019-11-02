import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.io.InputStreamReader;

public class Main{

    public static final String [] idealStr = {"2N", "1", "N2", "NLOGN", "N", "N2", "1", "N3", "N2", "N", "NLOGN", "N", "LOGN", "2N", "1", "LOGN", "N", "NF", "N"};

    public static void main(String[] args) {
        File al = new File("Algoritmo.class");
        al.delete();
        String [] arr = new String [19];

        for(int i = 1; i <= 19; i++){
            String algoritmoNStr = "Algoritmos/Algoritmo" + i + ".class";
            String algoritmoStr = "Algoritmo.class";

            File f = new File(algoritmoNStr);
            File f2 = new File(algoritmoStr);
            String wkDir = System.getProperty("user.dir");

            try{
                Files.copy(f.toPath(),f2.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
            catch(IOException e){
                e.printStackTrace();
            }
            System.out.print("Algoritmo " + i + ": ");
            ProcessBuilder build = new ProcessBuilder("java", "Analizador");
            build.directory(new File(wkDir));

            try{
                Process analizadorProcess = build.start();
                BufferedReader read = new BufferedReader(
                                      new InputStreamReader(analizadorProcess.getInputStream())
                                      );
                analizadorProcess.waitFor();
                arr[i-1] = read.readLine();
                System.out.println(arr[i-1]);
                read.close();
            }
            catch(Exception e){}
        }

        double cont = 0;
        for(int i = 0; i <= 18; i++){
            if(arr[i].equals(idealStr[i])) cont++;
        }
        double ratio = (cont*100)/19;
        double nota = cont - (19-cont)*(1/7);
        nota = (nota*100)/19;

        System.out.println("RATIO DE ACIERTO: " + ratio);
        System.out.println("NOTA*: " + nota);



    }
}