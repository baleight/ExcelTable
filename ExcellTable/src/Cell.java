
/**
 * Creo una classe per definire il tipo della cella
 */
public class Cell {
    int dimCOLUMN=27;
    public String[] arrayCell;
    public Cell( int i) {
        arrayCell = new String[dimCOLUMN];
        for(int j =0; j<dimCOLUMN; j++){
            if(j==0) {
                arrayCell[j] = ""+i; //inizializzo la colonna index
            }else {
                arrayCell[j] = ""; //inizializzo ogni colonna con un valore vuoto di string
            }
        }
    }
    /**
     * Ottengo il valore della cella
     */
    public String getValueAtCell(int col) {
        return arrayCell[col];
    }
    public void setValueAtCell(int col, String value) {
        arrayCell[col] = value;
    }

}
