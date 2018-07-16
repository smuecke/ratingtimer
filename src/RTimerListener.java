public interface RTimerListener {

    void setData(int total, int rem, int n);
    void chime(int type); // check if sounds should be played
    void stopped();
    
    boolean stopAfter();
    

}
