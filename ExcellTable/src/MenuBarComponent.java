
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Window;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * MenuBarComponent è il componente menu del frame principale.
 * Presenta i menu File e Help con i rispettivi sottomenu.
 */
public class MenuBarComponent extends JMenuBar
{
    private static final FileConfig fileConfig = new FileConfig();
    private static ChartEx chartEx;
    private static Window windowParent;
    private JMenuItem menuNewFile;
    private JMenuItem menuOpen;
    private JMenuItem menuSave;
    private JMenuItem menuExit;
    private JMenuItem lineaGrafo;
    private JMenuItem barraGrafo;
    private JMenuItem ghLink;

    MenuBarComponent(Window parent)
    {
        windowParent=parent;
        initMenu();
        addActionListenerComponent(menuNewFile, MenuBarComponent::newFileEvent);
        addActionListenerComponent(menuOpen, MenuBarComponent::openEvent);
        addActionListenerComponent(menuSave, MenuBarComponent::saveEvent);
        addActionListenerComponent(menuExit, MenuBarComponent::exitEvent);
        addActionListenerComponent(lineaGrafo, MenuBarComponent::lineaGrafo);
        addActionListenerComponent(barraGrafo, MenuBarComponent::barraGrafo);
        addActionListenerComponent(ghLink, MenuBarComponent::githubLinkOpen);
    }



    /** Inizializzo ogni menu */
    private void initMenu()
    {
        JMenu menuFile = new JMenu("File");
        menuNewFile = new JMenuItem("New file");
        menuOpen = new JMenuItem("Open");
        menuSave = new JMenuItem("Save");
        menuExit = new JMenuItem("Exit");
        menuFile.add(menuNewFile);
        menuFile.add(menuOpen);
        menuFile.add(menuSave);
        menuFile.add(menuExit);
        JMenu menuGrafici = new JMenu("Grafici");
        lineaGrafo = new JMenuItem("Linea");
        barraGrafo = new JMenuItem("Barra");
        menuGrafici.add(lineaGrafo);
        menuGrafici.add(barraGrafo);
        JMenu menuHelp = new JMenu("help");
        ghLink = new JMenuItem("Github repository");
        menuHelp.add(ghLink);
        add(menuFile);
        add(menuGrafici);
        add(menuHelp);
    }
    /** Aggiungo le azioni per ascoltare gli eventi dei componenti */
    private void addActionListenerComponent(JMenuItem item, Runnable method)
    {
        item.addActionListener(actionEvent -> method.run());
    }
    /** Aprò il file */
    private static void openEvent()
    {
        // Select File
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(new FileNameExtensionFilter("File di testo *.txt", "txt"));

        int returnVal = fileChooser.showSaveDialog(windowParent);
        if (returnVal == JFileChooser.CANCEL_OPTION) return ;
        File openedFile = fileChooser.getSelectedFile();
        // ------------------------
        fileConfig.openFilePathForReading(openedFile.getAbsolutePath());
        TableModel.setCelle(fileConfig.readFile());
        TableModel.printMatrix();
        // ------------------------
    }
    /** Salvo il file */
    private static void saveEvent()
    {
        // Select File
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Salva come testo *.txt", "txt"));


        int returnVal = fileChooser.showSaveDialog(windowParent);
        if (returnVal == JFileChooser.CANCEL_OPTION) return ;

        File file;
        if(fileChooser.getSelectedFile().getName().contains(".txt")){
            file = fileChooser.getSelectedFile();
        }else{
            file = new File(fileChooser.getSelectedFile() + ".txt");
        }

        if(file.exists())
        {
            int confirmDialog = JOptionPane.showConfirmDialog(windowParent, "Sovrascrivi il file selezionato?");
            if(confirmDialog != 0) return;
        }
        try
        {
            file.createNewFile();
        }
        catch (IOException e) { e.printStackTrace(); }
        // ------------------------
        // Riempio il file
        System.out.println("Ho salvat il file:" + file.getAbsolutePath());
        System.out.println("Grandezza: " + TableModel.MAX_COLUMN + " % "+ TableModel.MAX_ROW);

        fileConfig.openFilePathForWritting(file.getAbsolutePath());
        fileConfig.writeFile("DIM_ROW: " + TableModel.MAX_ROW);
        int i,j;
        for(i = 0; i < TableModel.MAX_ROW ; i++){
            for(j = 0; j < TableModel.MAX_COLUMN ; j++) {
                if(j==0){
                    fileConfig.writeFile("ROW: " + i);
                }
                else{
                    char cc = 'A';
                    fileConfig.writeFile("\tCOL " + Character.toString(cc + (j-1)) +" : "+ TableModel.cell[i].getValueAtCell(j));
                }
            }
        }
        try {
            fileConfig.closeFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static void newFileEvent()
    {
        System.out.println("Cliccio new file");
        Main.updateExcelFrameDaHome();
    }
    private static void githubLinkOpen()
    {
        try
        {
            Desktop.getDesktop().browse(new URI("https://github.com/baleight/ExcelTable"));
        }
        catch (IOException | URISyntaxException e) { e.printStackTrace(System.err); }
    }
    private static void lineaGrafo() {
        chartEx=new ChartEx();
        ArrayList<String> colDate = ExcelPane.getColumnList(0);
        ArrayList<String> colVal = ExcelPane.getColumnList(1);

        if(colDate!=null&&colVal!=null){
            chartEx.LineaChartEx(colDate,colVal);
        }else{
            chartEx.errorChartEX();
        }

    }

    private static void barraGrafo() {
        chartEx=new ChartEx();
        ArrayList<String> colDate = ExcelPane.getColumnList(0);
        ArrayList<String> colVal = ExcelPane.getColumnList(1);

        if(colDate!=null&&colVal!=null){
            chartEx.BarChartEx(colDate,colVal);
        }else{
            chartEx.errorChartEX();
        }


    }
    private static void exitEvent()
    {
        System.exit(0);
    }
}
