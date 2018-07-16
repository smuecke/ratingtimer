/**
 * Created by smuecke on 28.11.15.
 */
public enum TaskType {
    EXP, SXS, RR, DB, IRR, TTR, URL;
    
    public static String[] names() {
        TaskType[] tts = values();
        String[] names = new String[tts.length];
        for (int i = 0; i < tts.length; i++)
            names[i] = tts[i].name();
        return names;
    }
}
