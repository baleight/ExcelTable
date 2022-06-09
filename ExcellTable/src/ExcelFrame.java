import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;

public class ExcelFrame extends JPanel implements KeyListener {
    private JButton jBtnApply;
    private JButton addOneHundredRow;
    private JLabel crediti;
    private JTextField jtxCampo;
    private static TableModel model = null;
    private static JTable table;
    private JPanel mainPanel;
    private TableColumn col;
    private JLabel rowLabel;
    private JLabel colLabel;
    private JLabel maxRowLabel;
    private JPanel jPanelTop;
    private JPanel jPanelSud;
    private JPanel jPanelCenterTop;
    private DefaultTableCellRenderer centerRenderer;

    public ExcelFrame() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        if(table!=null){
            table.removeAll();
            this.removeAll();
        }
        table=null;
        model = new TableModel();
        table = new JTable(model);
        initComponent();
        setSizeComponent();
        setLocationComponent();
        addActionListenerComponent();
    }
    /**
     * Inizializzo i componenti
     */
    private void initComponent() {
        jBtnApply = new JButton("Apply");
        addOneHundredRow = new JButton("Aggiungi +100");
        crediti = new JLabel("@Christian Baleotto");
        jtxCampo = new JTextField();
        mainPanel = new JPanel(new BorderLayout());
        col = new TableColumn();
        rowLabel = new JLabel();
        colLabel = new JLabel();
        maxRowLabel = new JLabel();
        jPanelTop = new JPanel(new BorderLayout());
        jPanelSud = new JPanel(new BorderLayout());
        jPanelCenterTop = new JPanel(new BorderLayout());
        centerRenderer = new DefaultTableCellRenderer();
    }
    /**
     * Aggiungo le gestioni delle chiamate per ogni componente(bottoni)
     */
    private void addActionListenerComponent() {
        jBtnApply.addActionListener(e -> {
            String campo = jtxCampo.getText();
            if (!campo.equals("")) {
                model.setValueAt(campo, table.getSelectedRow(), table.getSelectedColumn());
                jtxCampo.setText("");
            }
        });
        table.getSelectionModel().addListSelectionListener(e -> {
            jtxCampo.setText("");
            if(table.getSelectedColumn()>0)
                jtxCampo.setText(model.getValueAt(table.getSelectedRow(), table.getSelectedColumn()));
            rowLabel.setText(" Righe: " + table.getSelectedRow());
            char cc = 'A' ;
            colLabel.setText(" Colonne: " + (char)(cc + (table.getSelectedColumn()-1)));
            maxRowLabel.setText(" Max Righe: " + (table.getRowCount()) +" ");
        });
        addOneHundredRow.addActionListener(e -> model.increaseMatrix(100));
    }
    /**
     * Setto i componenti con dimensioni fisse
     */
    private void setSizeComponent() {
        jBtnApply.setSize(100, 30);
        jtxCampo = new JTextField(30);
        jtxCampo.setSize(120, 30);
        colLabel.setSize(80, 30);
        rowLabel.setSize(80, 30);
        table.setRowHeight(25);
        col = table.getColumnModel().getColumn(0);//prendo il modello della colonna 0
        //modifico certi parametri
        col.setCellRenderer(new MyRenderer(Color.lightGray, Color.blue));//e modifico il colore di sfondo
        table.getColumnModel().getColumn(0).setPreferredWidth(200);//modifico la larghezza della cella colonna 0
    }
    /**
     * Alloco nei panelli i componenti
     */
    private void setLocationComponent() {
        jtxCampo.addKeyListener(this);//aggiungo la gestione delle chiamate della tastiera sulla textbox: camp
        //centro tutto
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        table.setDefaultRenderer(String.class, centerRenderer);
        //pulisco il panello principale
        mainPanel.removeAll();
        //Alloco i componenti nel panello superiore centrale
        jPanelCenterTop.setAlignmentX(Component.LEFT_ALIGNMENT);
        jPanelCenterTop.add(rowLabel, BorderLayout.WEST);
        jPanelCenterTop.add(colLabel, BorderLayout.CENTER);
        jPanelCenterTop.add(maxRowLabel, BorderLayout.EAST);
        //Alloco i componenti nel panello superiore e ci inserisco jPanelCenterTop al centro
        jPanelTop.setAlignmentX(Component.LEFT_ALIGNMENT);
        jPanelTop.add(jtxCampo, BorderLayout.WEST);//sinistra
        jPanelTop.add(jPanelCenterTop, BorderLayout.CENTER);
        jPanelTop.add(jBtnApply, BorderLayout.EAST);//destra
        //Creo un pannello scorrevole per la tabella
        JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        //Alloco i componenti nel panello inferiori
        jPanelSud.add(addOneHundredRow,BorderLayout.EAST);
        jPanelSud.add(crediti,BorderLayout.WEST);
        //Alloco tutti i componenti nel panello principale
        mainPanel.add(jPanelTop, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(jPanelSud, BorderLayout.SOUTH);
        //aggiungo il panello principale alla finestra
        add(mainPanel);
    }
    /** Gestire l'evento DIGITATO della chiave dal campo di testo. */
    public void keyTyped(KeyEvent e) {}
    /** Gestire l'evento PREMUTO della chiave dal campo di testo. */
    public void keyPressed(KeyEvent e) {}
    /** Gestire l'evento RILASCIATA della chiave dal campo di testo. */
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 10 || e.getKeyChar() == KeyEvent.VK_ENTER) {
                String value = jtxCampo.getText();
            insertIntoCell(value);
        }
    }
    /**
     * Inserisco dentro la cella il valore in String
     */
    private void insertIntoCell(String value){
        if (!value.equals("")) {
            System.out.println("Aggiungo " + value);

            if(table.getSelectedRow()!=-1 || table.getSelectedColumn()!=-1){
                model.setValueAt(value, table.getSelectedRow(), table.getSelectedColumn());
                jtxCampo.setText("");
            }else{
                System.out.println("Errore non hai cliccato nessuna cella!");
                jtxCampo.setText("Error");

            }

        }
    }


    static ArrayList<String> getColumnList(int z) {
        ArrayList <String> list = new ArrayList<String>();
        if(table.getSelectedRow()!=-1 || table.getSelectedColumn()!=-1){
            int i,j;
            j = table.getSelectedColumn()+z;
            for(i = 0; i < model.MAX_ROW ; i++){
                    String value = model.cell[i].getValueAtCell(j);
                    if(Objects.equals(value, "")){
                        list.add("");
                    }else{
                        list.add(model.cell[i].getValueAtCell(j));
                    }
            }
        }
        return list;
    }

}