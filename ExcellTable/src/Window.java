import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    ExcelFrame frame;
    MenuBarComponent  menuBarComponent;
    Dimension screenSize;
    public Window(){
        setSizeFrame();
        menuBarComponent = new MenuBarComponent(this);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);//exit
        setResizable(true);
        setJMenuBar(menuBarComponent);
        frame = new ExcelFrame();
        frame.setBackground(Color.WHITE);
        add(frame, BorderLayout.CENTER);
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
        //setLocationByPlatform(true);
        setLocation((width  - width/2) / 2, (height - height/2) / 2);
        setPreferredSize(new Dimension(width/2, height/2));
        setTitle("Excel - Programmazione Oggetti");
    }
    /**
     * Rimuovo il contenuto del panello e lo ri-aggiungo all'interno del Frame
     */
    public void updateExcelFrame(){
        this.remove(frame);
        frame = new ExcelFrame();
        add(frame, BorderLayout.CENTER);
        pack();
    }

}
