import javax.swing.*;
import java.awt.*;

/**
 * Classe che estende JFrame e costituisce la finestra principale
 */
public class Window extends JFrame {
    ExcelPane mainPane;
    MenuBarComponent  menuBarComponent;
    Dimension screenSize;
    public Window(){
        setSizeFrame();
        menuBarComponent = new MenuBarComponent(this);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);//exit
        setResizable(true);
        setJMenuBar(menuBarComponent);//Sistemo la barra del menu
        mainPane = new ExcelPane();//Creo il pannello principale
        mainPane.setBackground(Color.WHITE);
        add(mainPane, BorderLayout.CENTER);//Sistemo in centro il pannello principale nella finestra
        pack();
    }
    /**
     * Setto la dimensione della finestra basandomi sulla risoluzione dello schermo
     * ((Altezza dello schermo)/2 e (Larghezza dello schhermo)/2)
     */
    private void setSizeFrame() {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        //Sistemo la finestra in mezzo
        setLocation((width  - width/2) / 2, (height - height/2) / 2);
        //Inizializzo le dimensioni
        setPreferredSize(new Dimension(width/2, height/2));
        setTitle("Excel - Programmazione Oggetti");
    }
    /**
     * Rimuovo il pannello principale e lo ri-aggiungo all'interno del Frame
     */
    public void updateExcelFrame(){
        this.remove(mainPane);
        mainPane = new ExcelPane();
        add(mainPane, BorderLayout.CENTER);
        pack();
    }

}
