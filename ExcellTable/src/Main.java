
    /** ExcelTable - Programmazione Oggetti per il 13/06/2022
     * @author Christian Baleotto
     * @version 0.7 - 11/06/2022
     */

public class Main {
    /**
     * Metodo principale che rende eseguibile un programma,
     * richiamo la funzione
     */
    static Window window;
    public static void main(String[] args) {
        window = new Window();
    }
    /** Aggiorno la finestra(JFrame) sostituendo i pannelli con nuovi panelli aggiornati */
    public static void updateExcelFrameDaHome() {
        window.updateExcelFrame();
    }
}
