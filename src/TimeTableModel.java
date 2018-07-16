import javax.swing.table.AbstractTableModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by smuecke on 29.03.15.
 */
public class TimeTableModel extends AbstractTableModel {

    private int[][] values = new int[8][2];

    private String[] columnNames = {
            "Type", "Time", "Tasks"
    };
    private String[] rowNames = {
            "DB", "EXP", "IRR", "RR",
            "SXS", "TTR", "URL", "<html><b>Total</b></html>"
    };

    public TimeTableModel() {
        super();

        for (int i = 0; i < 8; i++) {
            values[i][0] = 0;
            values[i][1] = 0;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public int getRowCount() {
        return rowNames.length;
    }

    @Override
    public int getColumnCount() { return columnNames.length; }

    @Override
    public Object getValueAt(int row, int col) {
        switch (col) {
            case 0 : return rowNames[row];
            case 1 : return String.format(row == 7 ? "<html><b>%.2f min</b></html>" : "%.2f min", values[row][col - 1] / 60f);
            case 2 : return row == 7 ? boldString("" + values[row][1]) : "" + values[row][1];
            default : return null;
        }
    }

    public void addTime(int taskType, int seconds, int tasks) {
        // specific values
        values[taskType][0] += seconds;
        values[taskType][1] += tasks;
        // sums
        values[7][0] += seconds;
        values[7][1] += tasks;
    }

    public void clear() {
        for (int i = 0; i < 8; i++) {
            values[i][0] = 0;
            values[i][1] = 0;
        }
    }

    private boolean inRange(int n, int u, int v) {
        return n >= u && n <= v;
    }

    private String boldString(String s) {
        return "<html><b>" + s + "</b></html>";
    }
}
