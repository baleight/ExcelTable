import javax.swing.table.AbstractTableModel;
import java.io.IOException;
import java.util.regex.Pattern;

public class TableModel extends AbstractTableModel {
    static FunAdditional funAdd = new FunAdditional();
    public static int MAX_COLUMN;
    public static int MAX_ROW;
    private String[] columnNames;
    public static void setCelle(Cell[] celle) {
        TableModel.cell = celle;
    }
    public  static Cell[] cell;
    public FileConfig fileConfig = new FileConfig();

    //costruttore
    public TableModel() {
        System.out.println("Inizializzo il modello della tabella");
        initCell();
    }


    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return MAX_ROW;
    }
    /**Restituisco un valore di default per la colonna superiore usata per le convenzioni del foglio di calcolo: A, B, C, ...*/
    public String getColumnName(int col) {
        return columnNames[col];
    }
    public String getValueAt(int row, int col) {
        return cell[row].getValueAtCell(col);
    }
    /**
     * JTable usa questo metodo per determinare il renderer/editor
     * predefinito per ogni cella. Se non implementassimo questo metodo,
     * l'ultima colonna conterrebbe testo ("true"/"false"),
     * anziché una casella di controllo.
     * Restituisce Object.class indipendentemente da columnIndex.
     */
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
    /**
     * Non è necessario implementare questo metodo a meno che la tabella non sia modificabile.
     */
    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        return col >= 1;
    }
    /**
     * Non è necessario implementare questo metodo a meno che i dati della tabella non possano cambiare
     */
    public void setValueAt(Object value, int row, int col) {
        String values = (String) value;
        if(!values.equals("")){
            if(values.charAt(0)=='='){
                System.out.println("Entro in modalita calcolatore: ");
                values = values.substring(1);
                if(!(values.equals("")))
                    values= String.valueOf(calculator(values));
                else
                    values= String.valueOf("");
            }
            String linea = "Setto ROW:"+ row + " | COL:" + FunAdditional.converterInLetter(col) + " che prima era \""
                    + cell[row].getValueAtCell(col) +"\"";
            System.out.print(linea);
            cell[row].setValueAtCell(col, values);
            System.out.println(" con \"" + cell[row].getValueAtCell(col)+"\"");
            fireTableCellUpdated(row, col);
            scanningMatrix();
            insertIntoHistoryFile(linea);
        }
    }
    /**
     * Inserisco nel file History.txt le operazioni che eseguo come: Setto ROW:5 | COL:A che prima era "" con "32"
     */
    private void insertIntoHistoryFile(String linea) {
        fileConfig.openFileLocallyForWritting("\\History.txt");
        fileConfig.writeFile(linea);
        try {
            fileConfig.closeFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /** Aumento di 100 righe nella matrice */
    public void increaseMatrix(int quantitaRighe) {
        int dimNuovoRow = MAX_ROW+quantitaRighe;
        Cell[] celleTemp = new Cell[dimNuovoRow];//creo una matrice con la nuova grandezza row+100
        for (int i = 0; i<dimNuovoRow; i++){
            celleTemp[i] = new Cell(i);
        }
        copyMatrix_From_MatrixA_to_MatrixB(celleTemp, cell);//riempio temp con i valori di data
        MAX_ROW += quantitaRighe;
        cell = new Cell[MAX_ROW];
        for (int i = 0; i<MAX_ROW; i++){
            cell[i] = new Cell(i);
        }
        copyMatrix_From_MatrixA_to_MatrixB(cell, celleTemp);
    }
    /** Copio tutta la matrice da A alla matrice B */
    private void copyMatrix_From_MatrixA_to_MatrixB(Cell[] matriceA, Cell[] matriceB) {//copio da A a B
        int i;
        int j;
        for(i = 0; i < MAX_ROW ; i++){
            for(j = 0; j < MAX_COLUMN ; j++) {
                matriceA[i].setValueAtCell(j,matriceB[i].getValueAtCell(j));
            }
        }
    }
    /** Salvo tutte le celle nel file di salvataggio */
    public void scanningMatrix() {
        fileConfig.openFileLocallyForWritting("\\Salvataggio.txt");
        fileConfig.writeFile("DIM_ROW: " + MAX_ROW);
        int i,j;
        for(i = 0; i < MAX_ROW ; i++){
            for(j = 0; j < MAX_COLUMN ; j++) {
                if(j==0)
                    fileConfig.writeFile("ROW: " + i);
                else
                    fileConfig.writeFile("\tCOL " + FunAdditional.converterInLetter(j) +" : "+ cell[i].getValueAtCell(j));
            }
        }
        try {
            fileConfig.closeFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /** Inizializzo celle */
    public void initCell() {
        MAX_COLUMN = 27;
        MAX_ROW = 200;
        columnNames = new String[MAX_COLUMN];
        cell = new Cell[MAX_ROW];
        //riempio la tabella
        for (int i = 0; i<MAX_ROW; i++){
            cell[i] = new Cell(i);
        }
        //Inserisco tutte le lettere da A a Z nel array columnNames
        for(int i = 0; i < MAX_COLUMN ; i++){
            if(i != 0)
                columnNames[i]= FunAdditional.converterInLetter(i);
        }
    }
    /**
     * Metodo per stampare la matrice
     */
    public static void printMatrix(){
        System.out.println("DIM_ROW: " + MAX_ROW);
        int i,j;
        for(i = 0; i < MAX_ROW ; i++){
            for(j = 0; j < MAX_COLUMN ; j++) {
                if(j==0){
                    System.out.println("ROW: " + i);
                    System.out.print("ROW: " + i);
                }
                else{
                    System.out.print("\tCOL " + FunAdditional.converterInLetter(j) +" : "+ cell[i].getValueAtCell(j));
                }
            }
            System.out.println();
        }
    }
    /**
     * Calcolo l'epressione di due operandi. Esempio: 123+B3(7)= 130.0 in double
     */
    public double calculator(String valore){
        String first_operand, second_operand;
        String[] arr;
        String operatore;//prendo l'operatore + * - /
        System.out.println("Espressione: "+valore + " LEN: "+valore.length());//A1+23
        int i = 0;
        char car = valore.charAt(i);
        while (!(funAdd.isOperatore(car))){
            car = valore.charAt(i);
            i++;
            if(i==valore.length()){
                System.out.println("Esco da while perche non ha nessun operazione");
                break;
            }
        }
        if(i==valore.length()){
            operatore = String.valueOf("+");//prendo l'operatore + * - /
            first_operand=valore;
            second_operand="0";
        }else {
            operatore = String.valueOf(car);//prendo l'operatore + * - /
            arr = valore.split( Pattern.quote(String.valueOf(car))) ;
            first_operand = arr[0];
            second_operand= arr[1];
        }
        if(funAdd.isUnaLettera(first_operand.charAt(0)) && first_operand.charAt(1)>0){//se ce A1 B3
            System.out.print("1_Operando è una lettera: ");
            first_operand = getStringColumnNames(first_operand);
        }
        if(funAdd.isUnaLettera(second_operand.charAt(0))){
            System.out.print("2_Operando è una lettera: ");
            second_operand = getStringColumnNames(second_operand);
        }
        System.out.println("1_Operando: "+first_operand);
        System.out.println("Operatore: " + operatore);
        System.out.println("2_Operando: "+second_operand);

        if(second_operand.equals(""))
            second_operand="0";
        if(first_operand.equals(""))
            first_operand="0";
        // store the value in 1st
        double result;
        if (operatore.equals("+"))
            result = (Double.parseDouble(first_operand) + Double.parseDouble(second_operand));
        else if (operatore.equals("-"))
            result = (Double.parseDouble(first_operand) - Double.parseDouble(second_operand));
        else if (operatore.equals("/")||operatore.equals(":"))
            result = (Double.parseDouble(first_operand) / Double.parseDouble(second_operand));
        else
            result = (Double.parseDouble(first_operand) * Double.parseDouble(second_operand));
        return result;
    }
    /**
     * Metodo per convertire i valori dell intersezione tra ColumnNames e le righe (A2,C3,G5 etc...)
     * nei valori corrispondenti
     */
    private String getStringColumnNames(String operand) {
        int col;
        int row;
        col = FunAdditional.converterInNumber(operand.charAt(0))+1;
        System.out.print("COL "+col+" ");
        row = FunAdditional.converterLetterInNumber(operand.charAt(1));
        System.out.print("ROW "+row+" ");
        operand=(cell[row].getValueAtCell(col));
        System.out.print("Valore nella celle: "+operand);
        return operand;
    }
}

