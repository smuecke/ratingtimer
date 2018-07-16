import javax.swing.ImageIcon;
import java.net.URL;

public class Media {

    public static final ImageIcon ICON_START = new ImageIcon(absURL("res/arrow16.png"));
    public static final ImageIcon ICON_STOP  = new ImageIcon(absURL("res/stop4.png"));
    public static final ImageIcon ICON_SKIP  = new ImageIcon(absURL("res/play-1.png"));
    public static final ImageIcon ICON_CLOCK = new ImageIcon(absURL("res/timer20.png"));
    public static final ImageIcon ICON_TABLE = new ImageIcon(absURL("res/calendar68.png"));
    public static final ImageIcon ICON_NOTES = new ImageIcon(absURL("res/edit18.png"));
    public static final ImageIcon ICON_NOTES_ALERT = new ImageIcon(absURL("res/edit18-alert.png"));

    public static final URL HORN = absURL("res/sound2.wav");
    public static final URL RING = absURL("res/sound1.wav");

    public static final String TT_SKIP = "Skip remaining time";
    public static final String TT_STOPAFTER = "Stop automatically after the time is up";
    public static final String TT_TYPE = "Select task type";
    public static final String TT_TIME = "Set task time";
    public static final String TT_CLOCK = "Remaining time for this task";
    public static final String TT_TOTAL = "Total elapsed time";
    public static final String TT_TASKS = "Total number of tasks";

    public static URL absURL(String relPath) {
        ClassLoader cl = Media.class.getClassLoader();
        return cl.getResource(relPath);
    }

    public static void main(String[] args) {
        System.out.println(absURL("res/horn.wav"));
    }

}
