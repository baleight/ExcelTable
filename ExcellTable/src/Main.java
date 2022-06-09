public class Main {
    /**
     * Metodo principale che rende eseguibile un programma,
     * richiamo la funzione
     */
    static Window window;
    public static void main(String[] args) {
        window = new Window();
    }
    public static void aggiornaExcelFrameDaHome() {
        window.updateExcelFrame();
    }
}
