import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
public class MyRenderer extends DefaultTableCellRenderer {
    Color background, foreground;
    public MyRenderer(Color bg, Color fg) {
        super();
        //Setto il colore di sfondo
        this.background = bg;
        //Setto il colore di testo
        this.foreground = fg;
        setHorizontalAlignment( JLabel.CENTER );
    }
    public Component getTableCellRendererComponent(JTable table, Object
            value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        Component cell = super.getTableCellRendererComponent(table, value,
                isSelected, hasFocus, row, column);
        cell.setBackground(background);
        cell.setForeground(foreground);
        return cell;
    }
}
