import java.io.*;
import java.io.File;
import java.nio.file.Paths;

public class FileConfig {
    public String path = printCurrentWorkingDirectory();
    File file;
    FileWriter fileWriter;
    FileReader fileReader;
    BufferedWriter bufferedWriter;
    BufferedReader bufferedReader;


    public FileConfig() {    }
    public Cell[] readFile() {
        int MAXROW;
        String line;
        try {
            line = bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        MAXROW = Integer.parseInt(line.substring(9));//Prelevo la dimensione della matrice dal carattere 9 in poi

        Cell[] celleTemp = new Cell[MAXROW];
        try {
            for (int i = 0; i<MAXROW; i++){
                celleTemp[i] = new Cell(i);
            }
            System.out.println("Righe presenti: " + MAXROW);
            System.out.println("Il contenuto del file è il seguente:");
            for(int i=0; i<MAXROW; i++) {
                for (int j = 0; j < 27; j++) {
                    line = bufferedReader.readLine();
                    if (j == 0) {
                        celleTemp[i].setValueAtCell(j, line.substring(5));//Prelevo il numero della riga dal carattere 5 in poi
                    } else {
                        celleTemp[i].setValueAtCell(j, line.substring(9));//Prelevo la cella[i][j] della matrice dal carattere 9 in poi
                    }
                }
            }
            fileReader.close();
            bufferedReader.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return celleTemp;
    }
    /** Apro il file locale in scrittura */
    public void openFileLocallyForWritting(String nome_file){
        String absulute = path+nome_file;
        file = new File(absulute);
        try {
            fileWriter = new FileWriter(file);
            bufferedWriter = new BufferedWriter(fileWriter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void writeFile(String linea) {
        try {
            bufferedWriter.append(linea);
            bufferedWriter.newLine();

        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
    /** Chiudo il file */
    public void  closeFile() throws IOException {
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    private static String printCurrentWorkingDirectory() {
        return Paths.get("")
                .toAbsolutePath()
                .toString();
    }
    /** Apro il file da path in scrittura */
    public void openFilePathForWritting(String path){
        file = new File(path);
        try {
            fileWriter = new FileWriter(file);
            bufferedWriter = new BufferedWriter(fileWriter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /** Apro il file in path da lettura */
    public void openFilePathForReading(String path){
        file = new File(path);
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
