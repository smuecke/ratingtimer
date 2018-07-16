import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;

public class Chimes {

    public static final int HORN = 0;
    public static final int RING = 1;

    public void chime(final int type) {
        URL sound = (type == HORN) ? Media.HORN : Media.RING;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                AudioInputStream ais = null;
                try {
                    ais = AudioSystem.getAudioInputStream(sound);
                    Clip clip = AudioSystem.getClip();
                    clip.open(ais);
                    clip.start();
                   
                    /* 
                    // clean up resources
                    while (clip.isRunning() || clip.isActive())
                        try { Thread.sleep(200); }
                        catch (InterruptedException e) {}
                    */
                    clip.drain();
                    clip.close();
                    ais.close();
                } catch (Exception e) {
                    System.err.println("Exception while playing sound!");
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }
}
