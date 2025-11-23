package quiz.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Random;
import java.util.ArrayList;

public class Quiz extends JFrame implements ActionListener {
    String questions[][] = new String[10][5];
    String answers[][] = new String[10][2];
    String useranswers[][] = new String[10][1];
    JLabel qno, question, timerLabel, scoreLabel, progressLabel;
    JRadioButton opt1, opt2, opt3, opt4;
    ButtonGroup groupoptions;
    JButton next, submit, lifeline, pause;
    JPanel mainPanel, questionPanel, optionsPanel, buttonPanel, headerPanel;
    JProgressBar progressBar;
    Timer timer;
    int[] questionOrder;

    public static int timerCount = 15;
    public static int ans_given = 0;
    public static int count = 0;
    public static int score = 0;
    public static int lifelineUsed = 0;
    public static boolean isPaused = false;
    String name;

    Quiz(String name) {
        this.name = name;
        setTitle("Quiz - Simple Minds");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setBounds(50, 0, 1440, 900);

        // Initialize arrays
        for (int i = 0; i < 10; i++) {
            useranswers[i] = new String[1];
            useranswers[i][0] = "";
        }

        // Randomize question order
        questionOrder = new int[10];
        for (int i = 0; i < 10; i++) questionOrder[i] = i;
        shuffleArray(questionOrder);

        initializeComponents();
        initializeQuestions();
        setupTimer();

        // Start the quiz
        start(count);
        timer.start(); // Start the timer for the first question
        setVisible(true);
    }

    private void initializeComponents() {
        // Create main panel with gradient background
        mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(25, 25, 112),
                    getWidth(), getHeight(), new Color(70, 130, 180)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        };
        mainPanel.setLayout(null);
        setContentPane(mainPanel);

        createHeaderPanel();
        createQuestionPanel();
        createOptionsPanel();
        createButtonPanel();
        createStatusPanel();
    }

    private void createHeaderPanel() {
        headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20);
                g2d.setColor(new Color(0, 0, 0, 120));
                g2d.fill(roundedRectangle);
                g2d.dispose();
            }
        };
        headerPanel.setLayout(null);
        headerPanel.setBounds(50, 30, 1340, 120);
        headerPanel.setOpaque(false);

        JLabel headerText = new JLabel("Simple Minds Quiz", SwingConstants.CENTER);
        headerText.setBounds(0, 20, 1340, 60);
        headerText.setFont(new Font("Segoe UI", Font.BOLD, 48));
        headerText.setForeground(Color.WHITE);

        JLabel welcomeText = new JLabel("Welcome " + name + "!", SwingConstants.CENTER);
        welcomeText.setBounds(0, 75, 1340, 30);
        welcomeText.setFont(new Font("Segoe UI", Font.ITALIC, 18));
        welcomeText.setForeground(new Color(220, 220, 220));

        headerPanel.add(headerText);
        headerPanel.add(welcomeText);
        mainPanel.add(headerPanel);
    }

    private void createStatusPanel() {
        // Timer panel with better visibility
        JPanel timerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15);
                g2d.setColor(new Color(255, 255, 255, 240));
                g2d.fill(roundedRectangle);
                g2d.setColor(new Color(220, 53, 69, 200));
                g2d.setStroke(new BasicStroke(2));
                g2d.draw(roundedRectangle);
                g2d.dispose();
            }
        };
        timerPanel.setLayout(null);
        timerPanel.setBounds(50, 170, 300, 80);
        timerPanel.setOpaque(false);

        timerLabel = new JLabel("Time: 15s", SwingConstants.CENTER);
        timerLabel.setBounds(0, 15, 300, 50);
        timerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        timerLabel.setForeground(new Color(220, 53, 69));

        timerPanel.add(timerLabel);
        mainPanel.add(timerPanel);

        // Score panel
        JPanel scorePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15);
                g2d.setColor(new Color(255, 255, 255, 240));
                g2d.fill(roundedRectangle);
                g2d.setColor(new Color(40, 167, 69, 200));
                g2d.setStroke(new BasicStroke(2));
                g2d.draw(roundedRectangle);
                g2d.dispose();
            }
        };
        scorePanel.setLayout(null);
        scorePanel.setBounds(370, 170, 200, 80);
        scorePanel.setOpaque(false);

        scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
        scoreLabel.setBounds(0, 15, 200, 50);
        scoreLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        scoreLabel.setForeground(new Color(40, 167, 69));

        scorePanel.add(scoreLabel);
        mainPanel.add(scorePanel);

        // Progress panel
        JPanel progressPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15);
                g2d.setColor(new Color(255, 255, 255, 240));
                g2d.fill(roundedRectangle);
                g2d.setColor(new Color(70, 130, 180, 200));
                g2d.setStroke(new BasicStroke(2));
                g2d.draw(roundedRectangle);
                g2d.dispose();
            }
        };
        progressPanel.setLayout(null);
        progressPanel.setBounds(590, 170, 800, 80);
        progressPanel.setOpaque(false);

        progressLabel = new JLabel("Question 1 of 10", SwingConstants.LEFT);
        progressLabel.setBounds(20, 10, 200, 25);
        progressLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        progressLabel.setForeground(new Color(70, 130, 180));

        progressBar = new JProgressBar(0, 10);
        progressBar.setBounds(20, 40, 760, 25);
        progressBar.setValue(1);
        progressBar.setStringPainted(true);
        progressBar.setString("10%");
        progressBar.setBackground(new Color(240, 240, 240));
        progressBar.setForeground(new Color(70, 130, 180));
        progressBar.setFont(new Font("Segoe UI", Font.BOLD, 12));

        progressPanel.add(progressLabel);
        progressPanel.add(progressBar);
        mainPanel.add(progressPanel);
    }

    private void createQuestionPanel() {
        questionPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20);
                g2d.setColor(new Color(255, 255, 255, 250));
                g2d.fill(roundedRectangle);
                g2d.setColor(new Color(70, 130, 180, 150));
                g2d.setStroke(new BasicStroke(3));
                g2d.draw(roundedRectangle);
                g2d.dispose();
            }
        };
        questionPanel.setLayout(null);
        questionPanel.setBounds(50, 270, 1100, 120);
        questionPanel.setOpaque(false);

        qno = new JLabel();
        qno.setBounds(30, 20, 120, 40);
        qno.setFont(new Font("Segoe UI", Font.BOLD, 28));
        qno.setForeground(new Color(70, 130, 180));
        questionPanel.add(qno);

        question = new JLabel();
        question.setBounds(160, 20, 920, 80);
        question.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        question.setForeground(new Color(50, 50, 50));
        question.setVerticalAlignment(SwingConstants.TOP);
        questionPanel.add(question);

        mainPanel.add(questionPanel);
    }

    private void createOptionsPanel() {
        optionsPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20);
                g2d.setColor(new Color(255, 255, 255, 250));
                g2d.fill(roundedRectangle);
                g2d.setColor(new Color(70, 130, 180, 150));
                g2d.setStroke(new BasicStroke(3));
                g2d.draw(roundedRectangle);
                g2d.dispose();
            }
        };
        optionsPanel.setLayout(null);
        optionsPanel.setBounds(50, 410, 1100, 250);
        optionsPanel.setOpaque(false);

        opt1 = createStyledRadioButton();
        opt1.setBounds(30, 30, 1040, 40);
        optionsPanel.add(opt1);

        opt2 = createStyledRadioButton();
        opt2.setBounds(30, 80, 1040, 40);
        optionsPanel.add(opt2);

        opt3 = createStyledRadioButton();
        opt3.setBounds(30, 130, 1040, 40);
        optionsPanel.add(opt3);

        opt4 = createStyledRadioButton();
        opt4.setBounds(30, 180, 1040, 40);
        optionsPanel.add(opt4);

        groupoptions = new ButtonGroup();
        groupoptions.add(opt1);
        groupoptions.add(opt2);
        groupoptions.add(opt3);
        groupoptions.add(opt4);

        mainPanel.add(optionsPanel);
    }

    private void createButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setBounds(1170, 410, 220, 250);
        buttonPanel.setOpaque(false);

        next = createStyledButton("Next", new Color(52, 152, 219));
        next.setBounds(0, 0, 200, 50);
        next.setFont(new Font("Segoe UI", Font.BOLD, 18));
        next.addActionListener(this);
        buttonPanel.add(next);

        pause = createStyledButton("Pause", new Color(255, 193, 7));
        pause.setBounds(0, 60, 200, 50);
        pause.setFont(new Font("Segoe UI", Font.BOLD, 16));
        pause.addActionListener(this);
        buttonPanel.add(pause);

        lifeline = createStyledButton("50-50 Lifeline", new Color(155, 89, 182));
        lifeline.setBounds(0, 120, 200, 50);
        lifeline.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lifeline.addActionListener(this);
        buttonPanel.add(lifeline);

        submit = createStyledButton("Submit", new Color(46, 204, 113));
        submit.setBounds(0, 180, 200, 50);
        submit.setFont(new Font("Segoe UI", Font.BOLD, 18));
        submit.addActionListener(this);
        submit.setEnabled(false);
        buttonPanel.add(submit);

        mainPanel.add(buttonPanel);
    }

    private JRadioButton createStyledRadioButton() {
        JRadioButton radio = new JRadioButton() {
            @Override
            protected void paintComponent(Graphics g) {
                if (isSelected()) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15);
                    g2d.setColor(new Color(70, 130, 180, 100));
                    g2d.fill(roundedRectangle);
                    g2d.setColor(new Color(70, 130, 180));
                    g2d.setStroke(new BasicStroke(2.5f));
                    g2d.draw(roundedRectangle);
                    g2d.dispose();
                } else {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15);
                    g2d.setColor(new Color(248, 249, 250));
                    g2d.fill(roundedRectangle);
                    g2d.setColor(new Color(206, 212, 218));
                    g2d.setStroke(new BasicStroke(1.5f));
                    g2d.draw(roundedRectangle);
                    g2d.dispose();
                }
                super.paintComponent(g);
            }
        };

        radio.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        radio.setForeground(new Color(50, 50, 50));
        radio.setBackground(new Color(255, 255, 255, 0));
        radio.setOpaque(false);
        radio.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        radio.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        radio.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!radio.isSelected()) {
                    radio.setBackground(new Color(70, 130, 180, 50));
                    radio.repaint();
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                if (!radio.isSelected()) {
                    radio.setBackground(new Color(255, 255, 255, 0));
                    radio.repaint();
                }
            }
        });
        
        return radio;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text) {
            private Color currentColor = bgColor;
            
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 25, 25);
                g2d.setColor(currentColor);
                g2d.fill(roundedRectangle);
                
                // Add gradient effect
                GradientPaint gradient = new GradientPaint(0, 0, new Color(255, 255, 255, 30), 0, getHeight()/2, new Color(255, 255, 255, 0));
                g2d.setPaint(gradient);
                g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight()/2, 25, 25));
                
                g2d.dispose();
                super.paintComponent(g);
            }
            
            public void setCurrentColor(Color color) {
                this.currentColor = color;
                repaint();
            }
        };

        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                if (button.isEnabled()) {
                    ((JButton)evt.getSource()).setBackground(bgColor.brighter());
                    button.repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                if (button.isEnabled()) {
                    ((JButton)evt.getSource()).setBackground(bgColor);
                    button.repaint();
                }
            }
        });

        return button;
    }

    private void setupTimer() {
        timer = new Timer(1000, e -> {
            if (!isPaused) {
                timerCount--;
                updateTimerDisplay();
                
                if (timerCount <= 0) {
                    handleTimeout();
                }
            }
        });
    }

    private void updateTimerDisplay() {
        timerLabel.setText("Time: " + timerCount + "s");
        if (timerCount <= 5) {
            timerLabel.setForeground(Color.RED);
            // Add blinking effect for last 5 seconds
            if (timerCount % 2 == 0) {
                timerLabel.setVisible(false);
            } else {
                timerLabel.setVisible(true);
            }
        } else if (timerCount <= 10) {
            timerLabel.setForeground(new Color(255, 165, 0)); // Orange
            timerLabel.setVisible(true);
        } else {
            timerLabel.setForeground(new Color(220, 53, 69));
            timerLabel.setVisible(true);
        }
    }

    private void updateProgress() {
        progressLabel.setText("Question " + (count + 1) + " of 10");
        progressBar.setValue(count + 1);
        progressBar.setString(((count + 1) * 10) + "%");
    }

    private void initializeQuestions() {
        questions[0][0] = "Which is used to find and fix bugs in the Java programs?";
        questions[0][1] = "JVM";
        questions[0][2] = "JDB";
        questions[0][3] = "JDK";
        questions[0][4] = "JRE";

        questions[1][0] = "What is the return type of the hashCode() method in the Object class?";
        questions[1][1] = "int";
        questions[1][2] = "Object";
        questions[1][3] = "long";
        questions[1][4] = "void";

        questions[2][0] = "Which package contains the Random class?";
        questions[2][1] = "java.util package";
        questions[2][2] = "java.lang package";
        questions[2][3] = "java.awt package";
        questions[2][4] = "java.io package";

        questions[3][0] = "An interface with no fields or methods is known as?";
        questions[3][1] = "Runnable Interface";
        questions[3][2] = "Abstract Interface";
        questions[3][3] = "Marker Interface";
        questions[3][4] = "CharSequence Interface";

        questions[4][0] = "In which memory a String is stored, when we create a string using new operator?";
        questions[4][1] = "Stack";
        questions[4][2] = "String memory";
        questions[4][3] = "Random storage space";
        questions[4][4] = "Heap memory";

        questions[5][0] = "Which of the following is a marker interface?";
        questions[5][1] = "Runnable interface";
        questions[5][2] = "Remote interface";
        questions[5][3] = "Readable interface";
        questions[5][4] = "Result interface";

        questions[6][0] = "Which keyword is used for accessing the features of a package?";
        questions[6][1] = "import";
        questions[6][2] = "package";
        questions[6][3] = "extends";
        questions[6][4] = "export";

        questions[7][0] = "In java, jar stands for?";
        questions[7][1] = "Java Archive Runner";
        questions[7][2] = "Java Archive";
        questions[7][3] = "Java Application Resource";
        questions[7][4] = "Java Application Runner";

        questions[8][0] = "Which of the following is a mutable class in java?";
        questions[8][1] = "java.lang.StringBuilder";
        questions[8][2] = "java.lang.Short";
        questions[8][3] = "java.lang.Byte";
        questions[8][4] = "java.lang.String";

        questions[9][0] = "Which of the following option leads to the portability and security of Java?";
        questions[9][1] = "Bytecode is executed by JVM";
        questions[9][2] = "The applet makes the Java code secure and portable";
        questions[9][3] = "Use of exception handling";
        questions[9][4] = "Dynamic binding between objects";

        // Initialize answers array properly
        for (int i = 0; i < 10; i++) {
            answers[i] = new String[2];
        }
        
        answers[0][1] = "JDB";
        answers[1][1] = "int";
        answers[2][1] = "java.util package";
        answers[3][1] = "Marker Interface";
        answers[4][1] = "Heap memory";
        answers[5][1] = "Remote interface";
        answers[6][1] = "import";
        answers[7][1] = "Java Archive";
        answers[8][1] = "java.lang.StringBuilder";
        answers[9][1] = "Bytecode is executed by JVM";
    }

    private void shuffleArray(int[] array) {
        Random rand = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    private void handleTimeout() {
        timer.stop();
        timerLabel.setVisible(true);
        timerLabel.setText("Time's up!");
        timerLabel.setForeground(Color.RED);
        
        opt1.setEnabled(false);
        opt2.setEnabled(false);
        opt3.setEnabled(false);
        opt4.setEnabled(false);

        // Auto-advance after timeout
        Timer delayTimer = new Timer(2000, e -> {
            opt1.setEnabled(true);
            opt2.setEnabled(true);
            opt3.setEnabled(true);
            opt4.setEnabled(true);
            
            if (count == 9) {
                if (groupoptions.getSelection() == null) {
                    useranswers[count][0] = "";
                } else {
                    useranswers[count][0] = groupoptions.getSelection().getActionCommand();
                }
                calculateFinalScore();
                showResults();
            } else {
                if (groupoptions.getSelection() == null) {
                    useranswers[count][0] = "";
                } else {
                    useranswers[count][0] = groupoptions.getSelection().getActionCommand();
                }
                count++;
                start(count);
                timerCount = 15;
                timer.start();
            }
            ((Timer)e.getSource()).stop();
        });
        delayTimer.setRepeats(false);
        delayTimer.start();
    }

    private void calculateFinalScore() {
        score = 0;
        for (int i = 0; i < useranswers.length; i++) {
            if (useranswers[i][0] != null && useranswers[i][0].equals(answers[questionOrder[i]][1])) {
                score += 10;
            }
        }
    }

    private void showResults() {
        setVisible(false);
        JFrame resultsFrame = new JFrame("Quiz Results - Simple Minds");
        resultsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        resultsFrame.setBounds(50, 0, 1440, 900);
        resultsFrame.setResizable(false);

        JPanel resultsPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(25, 25, 112),
                    getWidth(), getHeight(), new Color(70, 130, 180)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        };
        resultsPanel.setLayout(null);
        resultsFrame.setContentPane(resultsPanel);

        // Header
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20);
                g2d.setColor(new Color(0, 0, 0, 120));
                g2d.fill(roundedRectangle);
                g2d.dispose();
            }
        };
        headerPanel.setLayout(null);
        headerPanel.setBounds(50, 30, 1340, 120);
        headerPanel.setOpaque(false);

        JLabel headerText = new JLabel("Quiz Results", SwingConstants.CENTER);
        headerText.setBounds(0, 20, 1340, 60);
        headerText.setFont(new Font("Segoe UI", Font.BOLD, 48));
        headerText.setForeground(Color.WHITE);

        JLabel subHeaderText = new JLabel("Thank you for playing, " + name + "!", SwingConstants.CENTER);
        subHeaderText.setBounds(0, 75, 1340, 30);
        subHeaderText.setFont(new Font("Segoe UI", Font.ITALIC, 18));
        subHeaderText.setForeground(new Color(220, 220, 220));

        headerPanel.add(headerText);
        headerPanel.add(subHeaderText);
        resultsPanel.add(headerPanel);

        // Results Card
        JPanel cardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20);
                g2d.setColor(new Color(255, 255, 255, 250));
                g2d.fill(roundedRectangle);
                g2d.setColor(new Color(70, 130, 180, 150));
                g2d.setStroke(new BasicStroke(3));
                g2d.draw(roundedRectangle);
                g2d.dispose();
            }
        };
        cardPanel.setLayout(null);
        cardPanel.setBounds(200, 170, 1040, 600);
        cardPanel.setOpaque(false);

        // Score Display
        JLabel scoreTitle = new JLabel("Your Score", SwingConstants.CENTER);
        scoreTitle.setBounds(0, 30, 1040, 40);
        scoreTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        scoreTitle.setForeground(new Color(70, 130, 180));
        cardPanel.add(scoreTitle);

        JLabel scoreValue = new JLabel(score + "/100", SwingConstants.CENTER);
        scoreValue.setBounds(0, 80, 1040, 80);
        scoreValue.setFont(new Font("Segoe UI", Font.BOLD, 64));
        scoreValue.setForeground(new Color(40, 167, 69));
        cardPanel.add(scoreValue);

        // Percentage
        JLabel percentageLabel = new JLabel("Percentage: " + score + "%", SwingConstants.CENTER);
        percentageLabel.setBounds(0, 170, 1040, 40);
        percentageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        percentageLabel.setForeground(new Color(50, 50, 50));
        cardPanel.add(percentageLabel);

        // Questions Answered
        JLabel questionsLabel = new JLabel("Questions Answered: " + (count + 1) + "/10", SwingConstants.CENTER);
        questionsLabel.setBounds(0, 220, 1040, 40);
        questionsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        questionsLabel.setForeground(new Color(50, 50, 50));
        cardPanel.add(questionsLabel);

        // Motivational Message
        String message;
        if (score >= 80) {
            message = "Excellent! Outstanding performance!";
        } else if (score >= 60) {
            message = "Good job! You did well!";
        } else if (score >= 40) {
            message = "Not bad, but there's room for improvement!";
        } else {
            message = "Keep studying and try again!";
        }
		JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>" + message + "</div></html>", SwingConstants.CENTER);
		messageLabel.setBounds(0, 270, 1040, 60);
        messageLabel.setFont(new Font("Segoe UI", Font.ITALIC, 20));
        messageLabel.setForeground(new Color(70, 130, 180));
        cardPanel.add(messageLabel);

        // Exit Button
        JButton exitButton = createStyledButton("Exit", new Color(220, 53, 69));
        exitButton.setBounds(420, 500, 200, 50);
        exitButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        exitButton.addActionListener(e -> System.exit(0));
        cardPanel.add(exitButton);

        resultsPanel.add(cardPanel);
        resultsFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == next) {
            ans_given = 1;
            if (groupoptions.getSelection() == null) {
                useranswers[count][0] = "";
            } else {
                useranswers[count][0] = groupoptions.getSelection().getActionCommand();
                
                // Immediate feedback
                if (useranswers[count][0].equals(answers[questionOrder[count]][1])) {
                    JOptionPane.showMessageDialog(this, "Correct! +10 points", "Correct Answer", JOptionPane.INFORMATION_MESSAGE);
                    score += 10;
                    scoreLabel.setText("Score: " + score);
                } else {
                    JOptionPane.showMessageDialog(this, "Incorrect! The correct answer was: " + answers[questionOrder[count]][1], 
                        "Wrong Answer", JOptionPane.WARNING_MESSAGE);
                }
            }

            if (count == 8) {
                next.setEnabled(false);
                submit.setEnabled(true);
            }

            count++;
            start(count);
            timerCount = 15;
            timer.restart();
            
        } else if (ae.getSource() == pause) {
            if (!isPaused) {
                isPaused = true;
                timer.stop();
                pause.setText("Resume");
                // Disable all options during pause
                opt1.setEnabled(false);
                opt2.setEnabled(false);
                opt3.setEnabled(false);
                opt4.setEnabled(false);
                next.setEnabled(false);
                lifeline.setEnabled(false);
            } else {
                isPaused = false;
                timer.start();
                pause.setText("Pause");
                // Re-enable options
                opt1.setEnabled(true);
                opt2.setEnabled(true);
                opt3.setEnabled(true);
                opt4.setEnabled(true);
                next.setEnabled(true);
                if (lifelineUsed == 0) lifeline.setEnabled(true);
            }
            
        } else if (ae.getSource() == lifeline) {
            if (lifelineUsed == 0) {
                ArrayList<Integer> wrongOptions = new ArrayList<>();
                for (int i = 1; i <= 4; i++) {
                    if (!questions[questionOrder[count]][i].equals(answers[questionOrder[count]][1])) {
                        wrongOptions.add(i);
                    }
                }
                
                // Remove 2 wrong options randomly
                Random rand = new Random();
                int option1 = wrongOptions.get(rand.nextInt(wrongOptions.size()));
                wrongOptions.remove(Integer.valueOf(option1));
                int option2 = wrongOptions.get(rand.nextInt(wrongOptions.size()));

                if (option1 == 1 || option2 == 1) {
                    opt1.setEnabled(false);
                    opt1.setForeground(Color.GRAY);
                }
                if (option1 == 2 || option2 == 2) {
                    opt2.setEnabled(false);
                    opt2.setForeground(Color.GRAY);
                }
                if (option1 == 3 || option2 == 3) {
                    opt3.setEnabled(false);
                    opt3.setForeground(Color.GRAY);
                }
                if (option1 == 4 || option2 == 4) {
                    opt4.setEnabled(false);
                    opt4.setForeground(Color.GRAY);
                }

                lifelineUsed = 1;
                lifeline.setEnabled(false);
                lifeline.setText("Used");
                
                JOptionPane.showMessageDialog(this, "50-50 Lifeline used! Two incorrect options have been removed.", 
                    "Lifeline Used", JOptionPane.INFORMATION_MESSAGE);
            }
            
        } else if (ae.getSource() == submit) {
            ans_given = 1;
            if (groupoptions.getSelection() == null) {
                useranswers[count][0] = "";
            } else {
                useranswers[count][0] = groupoptions.getSelection().getActionCommand();
                
                // Immediate feedback for last question
                if (useranswers[count][0].equals(answers[questionOrder[count]][1])) {
                    JOptionPane.showMessageDialog(this, "Correct! +10 points", "Correct Answer", JOptionPane.INFORMATION_MESSAGE);
                    score += 10;
                } else {
                    JOptionPane.showMessageDialog(this, "Incorrect! The correct answer was: " + answers[questionOrder[count]][1], 
                        "Wrong Answer", JOptionPane.WARNING_MESSAGE);
                }
            }

            // Calculate final score
            calculateFinalScore();
            showResults();
        }
    }

    public void start(int count) {
        if (count < 10) {
            qno.setText("Q" + (count + 1) + ":");
            question.setText("<html><div style='width: 900px; line-height: 1.4;'>" + questions[questionOrder[count]][0] + "</div></html>");

            opt1.setText(questions[questionOrder[count]][1]);
            opt1.setActionCommand(questions[questionOrder[count]][1]);
            opt1.setEnabled(true);
            opt1.setForeground(new Color(50, 50, 50));

            opt2.setText(questions[questionOrder[count]][2]);
            opt2.setActionCommand(questions[questionOrder[count]][2]);
            opt2.setEnabled(true);
            opt2.setForeground(new Color(50, 50, 50));

            opt3.setText(questions[questionOrder[count]][3]);
            opt3.setActionCommand(questions[questionOrder[count]][3]);
            opt3.setEnabled(true);
            opt3.setForeground(new Color(50, 50, 50));

            opt4.setText(questions[questionOrder[count]][4]);
            opt4.setActionCommand(questions[questionOrder[count]][4]);
            opt4.setEnabled(true);
            opt4.setForeground(new Color(50, 50, 50));

            groupoptions.clearSelection();
            updateProgress();
            
            // Re-enable lifeline if not used
            if (lifelineUsed == 0) {
                lifeline.setEnabled(true);
            }
            
            // Enable/disable buttons based on question number
            if (count == 9) {
                next.setEnabled(false);
                submit.setEnabled(true);
            } else {
                next.setEnabled(true);
                submit.setEnabled(false);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Set the system Look and Feel
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException e) {
                System.err.println("Look and Feel class not found: " + e.getMessage());
                // Fallback to default Look and Feel (Metal)
                try {
                    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                } catch (Exception ex) {
                    System.err.println("Failed to set fallback Look and Feel: " + ex.getMessage());
                }
            } catch (UnsupportedLookAndFeelException e) {
                System.err.println("Look and Feel not supported: " + e.getMessage());
                // Fallback to default Look and Feel
                try {
                    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                } catch (Exception ex) {
                    System.err.println("Failed to set fallback Look and Feel: " + ex.getMessage());
                }
            } catch (Exception e) {
                System.err.println("Unexpected error setting Look and Feel: " + e.getMessage());
            }

            // Proceed with creating the quiz application
            String name = JOptionPane.showInputDialog(null, "Enter your name:", 
                "Welcome to Simple Minds Quiz", JOptionPane.QUESTION_MESSAGE);
            
            if (name == null || name.trim().isEmpty()) {
                name = "Player";
            }
            
            new Quiz(name.trim());
        });
    
    }
}