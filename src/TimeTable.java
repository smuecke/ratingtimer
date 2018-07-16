import javax.swing.*;
import java.sql.Time;

/**
 * Created by smuecke on 29.03.15.
 */
public class TimeTable extends JTable {

    public TimeTable() {
        super();
    }

    public void addTime(int taskType, int seconds, int tasks) {
        TimeTableModel ttm = (TimeTableModel) this.getModel();
        ttm.addTime(taskType, seconds, tasks);
        updateUI();
    }

    public void clear() {
        TimeTableModel ttm = (TimeTableModel) this.getModel();
        ttm.clear();
        updateUI();
    }

}
