import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import javax.swing.*;

public class RTWindow2 extends JFrame implements ActionListener, RTimerListener {
    
    public static final int INIT_TASK_TIME = 9;
    public static final int INIT_TARGET_TIME = 60;
    
    private boolean timerRunning, notesAlert;
    
    private JButton skipRemaining;
    private JCheckBox stopAfter;
    private JComboBox taskType;
    private JLabel clock, total, tasks;
    private JMenuItem miStart, miStop, miClear;
    private JCheckBoxMenuItem miAOT, miSounds;
    private JTabbedPane rootPanel;
    private JTextArea notes;
    private JProgressBar targetProgress;
    private TimeSpinner taskTime, targetTime;
    
    private TimeTable table;
    private TimeTableModel tableModel;
    
    private Chimes chimes;
    private RTimer rtimer;
    
    private int value_total, value_tasks, value_saved;

    public RTWindow2() {
        super("Rating Timer");
        this.setAlwaysOnTop(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        initUI();
        initData();
        setupUI();

        this.setContentPane(rootPanel);
        this.pack();
        this.setVisible(true);
    }

    private void initUI(){
        rootPanel = new JTabbedPane();

        miStart = new JMenuItem("Start", Media.ICON_START);
        miStart.addActionListener(this);
        miStop = new JMenuItem("Stop", Media.ICON_STOP);
        miStop.addActionListener(this);
        miAOT = new JCheckBoxMenuItem("Always on top", true);
        miAOT.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                RTWindow2.this.setAlwaysOnTop(miAOT.isSelected());
            }
        });
        miClear = new JMenuItem("Clear data");
        miClear.addActionListener(this);
        miSounds = new JCheckBoxMenuItem("Play sounds", true);
        
        clock = new JLabel("09:00", SwingConstants.CENTER);
        clock.setFont(new Font(clock.getFont().getName(), clock.getFont().getStyle(), 40));
        clock.setToolTipText(Media.TT_CLOCK);
        total = new JLabel("00:00", SwingConstants.CENTER);
        total.setToolTipText(Media.TT_TOTAL);
        tasks = new JLabel("0", SwingConstants.CENTER);
        tasks.setToolTipText(Media.TT_TASKS);
        skipRemaining = new JButton(Media.ICON_SKIP);
        skipRemaining.addActionListener(this);
        skipRemaining.setToolTipText(Media.TT_SKIP);
        stopAfter = new JCheckBox("Stop after");
        stopAfter.setToolTipText(Media.TT_STOPAFTER);
        taskType = new JComboBox(TaskType.names());
        taskType.setToolTipText(Media.TT_TYPE);
        taskTime = new TimeSpinner();
        taskTime.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                RTWindow2.this.clock.setText(((Time) taskTime.getValue()).toString());
            }
        });
        taskTime.setToolTipText(Media.TT_TIME);
        targetProgress = new JProgressBar();
        targetProgress.setStringPainted(true);
        targetTime = new TimeSpinner();
        targetTime.addChangeListener(new ChangeListener () {
            @Override
            public void stateChanged(ChangeEvent e) {
                RTWindow2.this.targetProgress.setMaximum(((Time) targetTime.getValue()).toSeconds());
            }
        });
        
        table = new TimeTable();
    }

    private void initData() {
        timerRunning = false;
        notesAlert = false;
        
        value_total = 0;
        value_tasks = 0;
        value_saved = 0;
        taskTime.setValue(INIT_TASK_TIME);
        targetTime.setValue(INIT_TARGET_TIME);
        targetProgress.setMinimum(0);
        targetProgress.setMaximum(INIT_TARGET_TIME*60);
        targetProgress.setValue(0);
        targetProgress.setString(Time.timeFormat(0));
        
        tableModel = new TimeTableModel();
        table.setModel(this.tableModel);

        chimes = new Chimes();
    }
    
    private void setupUI() {
        // setup menu bar
        JMenuBar menubar = new JMenuBar();
        JMenu mControl = new JMenu("Control");
        JMenu mData = new JMenu("Data");
        
        JMenuItem miQuit = new JMenuItem("Quit");
        miQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        mControl.add(miStart);
        mControl.add(miStop);
        mControl.addSeparator();
        mControl.add(miAOT);
        mControl.add(miSounds);
        mControl.addSeparator();
        mControl.add(miQuit);

        mData.add(miClear);

        menubar.add(mControl);
        menubar.add(mData);
        this.setJMenuBar(menubar);
      
        // setup rest of the panel
        GridBagConstraints c = new GridBagConstraints();
        
        JPanel clockPanel = new JPanel(new GridBagLayout());
        
        // layout of clock panel
        c.fill=GridBagConstraints.HORIZONTAL;
        c.insets=new Insets(2,2,2,2);
        c.gridwidth=2;
        c.gridheight=2;
        clockPanel.add(clock, c);
        c.gridwidth=1;
        c.gridheight=1;
        c.gridy=2;
        clockPanel.add(total, c);
        c.gridx=1;
        clockPanel.add(tasks, c);
        c.gridx=2;
        clockPanel.add(skipRemaining, c);
        c.gridx=3;
        clockPanel.add(stopAfter, c);
        c.gridwidth=2;
        c.gridx=2;
        c.gridy=0;
        clockPanel.add(taskTime, c);
        c.gridy=1;
        clockPanel.add(taskType, c);
        c.gridwidth = 4;
        c.gridx = 0;
        c.gridy = 3;
        clockPanel.add(targetProgress, c);
        
        rootPanel.addTab("", clockPanel);
        rootPanel.setIconAt(0, Media.ICON_CLOCK);
        
        // table panel
        JPanel tablePanel = new JPanel(new GridBagLayout());
        
        JScrollPane tableSP = new JScrollPane(table);
        tableSP.setPreferredSize(clockPanel.getPreferredSize());
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        tablePanel.add(tableSP, c);
        
        c.gridy = 1;
        tablePanel.add(targetTime, c);
        
        rootPanel.addTab("", tablePanel);
        rootPanel.setIconAt(1, Media.ICON_TABLE);
        
        // notes panel
        notes = new JTextArea();
        notes.setLineWrap(true);
        notes.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void removeUpdate(DocumentEvent e) {
                if (RTWindow2.this.notes.getDocument().getLength() <= 0) {
                    RTWindow2.this.notesAlert = false;
                    RTWindow2.this.rootPanel.setIconAt(2, Media.ICON_NOTES);
                }
            }
            
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (! RTWindow2.this.notesAlert) {
                    RTWindow2.this.notesAlert = true;
                    RTWindow2.this.rootPanel.setIconAt(2, Media.ICON_NOTES_ALERT);
                }
            }
            
            @Override
            public void changedUpdate(DocumentEvent e) {}
        });
        
        rootPanel.addTab("", new JScrollPane(notes));
        rootPanel.setIconAt(2, Media.ICON_NOTES);
    }

    public void setData(int total, int rem, int n) {
        this.value_total = total;
        this.value_tasks = n;
    
        this.total.setText(Time.timeFormat(total));
        this.clock.setText(Time.timeFormat(rem));
        this.tasks.setText("" + n);
        
        this.targetProgress.setValue(this.value_saved + total);
        this.targetProgress.setString(Time.timeFormat(this.value_saved + total));
    }
    
    public void chime(final int type) {
        if (miSounds.isSelected())
            chimes.chime(type);
    }
    
    public void stopped() {
        if (value_tasks > 0) {
            int toAdd = Math.min(value_total, value_tasks * ((Time) taskTime.getValue()).toSeconds());
            table.addTime(currentTaskType(), toAdd, value_tasks);
            value_saved += toAdd;
        }
        setData(0, ((Time) taskTime.getValue()).toSeconds(), 0);
        
        // set clock color
        clock.setForeground(Color.BLACK);
        
        if (stopAfter.isSelected())
            stopAfter.setSelected(false);
        timerRunning=false;
    }
    
    public boolean stopAfter() {
        return stopAfter.isSelected();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object s = e.getSource();
        if (s == miStart && !timerRunning) {
            rtimer = new RTimer(this, ((Time) taskTime.getValue()).toSeconds());
            rtimer.start();
            
            // set clock color
            clock.setForeground(Color.BLUE);
            
            timerRunning=true;
        } else if (s == miStop && timerRunning) {
            rtimer.interrupt();
            this.stopped();
        } else if (s == miClear) {
            table.clear();
            notes.setText("");
            value_saved = 0;
            setData(0, ((Time) taskTime.getValue()).toSeconds(), 0);
            targetProgress.setValue(0);
        } else if (s == skipRemaining) {
            rtimer.nextTask = true;
        }
    }
    
    public int currentTaskType() {
        int[] tt = { 1, 4, 0, 2, 3, 5, 6 };
        return tt[taskType.getSelectedIndex()];
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RTWindow2();
            }
        });
    }
}
