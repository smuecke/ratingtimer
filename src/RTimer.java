/**
 * Created by smuecke on 19.03.15.
 */
public class RTimer extends Thread {

    private int taskTime;
    private int taskTimeElapsed;
    private int totalElapsed;
    private int tasks;

    private RTimerListener rtw;

    public volatile boolean nextTask;

    public RTimer(RTimerListener rtw, int initTaskTime) {
        super();

        this.rtw = rtw;
        taskTime = initTaskTime;
        taskTimeElapsed = 0;
        totalElapsed = 0;
        tasks = 0;
        nextTask = false;
    }

    public void run() {
        long nextSec = System.currentTimeMillis() + 1000;
        boolean running = true;

        while (running) {
            // has one second passed?
            if (System.currentTimeMillis() >= nextSec) {
                nextSec += 1000;
                // "next task" clicked or taskTime elapsed?
                if (nextTask || taskTimeElapsed >= taskTime) {
                    taskTimeElapsed = 1;
                    tasks++;
                    nextTask = false;
                    rtw.chime(0);
                    // handle checkbox
                    if (rtw.stopAfter()) {
                        rtw.setData(totalElapsed + 1, taskTime - taskTimeElapsed, tasks);
                        rtw.stopped();
                        this.interrupt();
                        return;
                    }
                } else {
                    if (taskTime - taskTimeElapsed == 30)
                        rtw.chime(1);
                    taskTimeElapsed++;
                }
                totalElapsed++;

                // give changes to RTWindow
                rtw.setData(totalElapsed, taskTime - taskTimeElapsed, tasks);
            }

            try {
                Thread.sleep(6);
            } catch (InterruptedException e) {
                running = false;
            }
        }
    }
}
