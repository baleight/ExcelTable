public class FunAdditional {

    public static int converterLetterInNumber(char charAt) {
        int cc = '0';
        return (charAt-cc);
    }
    public boolean isUnaLettera(char car) {
        if(car>= 65 && car<=90){
            return true;
        }
        return false;
    }
    public boolean isOperatore(char car) {
        return car == '+' || car == '-' || car == '*' || car == '/' || car == ':';
    }
    public boolean isNumber(char car) {
        if(car>= 42 && car<=57){
            return true;
        }
        return false;
    }
    static int converterInNumber(char col) {
        int cc = 'A';
        return (col-cc);
    }

    /** Converto il numero della colonna in lettera */
    static String converterInLetter(int col) {
        char cc = 'A';
        return Character.toString(cc + (col-1));
    }

}
