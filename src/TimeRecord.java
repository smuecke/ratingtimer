import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * Created by smuecke on 28.11.15.
 */
public class TimeRecord {

    private int currentNTasks, currentTotalTime;
    private HashMap<TaskType, Record> records;

    public TimeRecord() {
        currentNTasks = 0;
        currentTotalTime = 0;
        records = new HashMap<TaskType, Record>(7);
        for (TaskType t : TaskType.values()) {
            records.put(t, new Record());
        }
    }

    private class Record {
        private int nTasks, totalTime;
        private LocalDateTime completed;

        public Record() {
            this.completed = null;
            this.totalTime = 0;
            this.nTasks = 0;
        }

        public Record(LocalDateTime completed, int totalTime, int nTasks) {
            this.completed = completed;
            this.totalTime = totalTime;
            this.nTasks = nTasks;
        }

        public void addCompletedTask(int taskTime) {
            this.totalTime += taskTime;
            this.nTasks++;
            this.completed = LocalDateTime.now();
        }
    }
}
