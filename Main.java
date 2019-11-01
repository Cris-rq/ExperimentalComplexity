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

    public static void main(String[] args) {
        File al = new File("Algoritmo.class");
        al.delete();

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
                System.out.println(read.readLine());
                read.close();
            }
            catch(Exception e){}

            //f2.renameTo(f);
        }
    }

    private static void temporizador(int seg){
        long t1 = System.currentTimeMillis();
        while((System.currentTimeMillis() - t1) <= (seg*1000)){}
    }

}