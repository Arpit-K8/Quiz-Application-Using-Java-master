package quiz.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class Score extends JFrame implements ActionListener {
    String name;
    int score;
    JButton playAgain, exit, viewAnswers;

    Score(String name, int score) {
        this.name = name;
        this.score = score;
        setTitle("Quiz Results - Simple Minds");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Create main panel with gradient background
        JPanel mainPanel = new JPanel() {
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

        // Score image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/score.png"));
        Image i2 = i1.getImage().getScaledInstance(300, 250, Image.SCALE_SMOOTH);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(50, 150, 300, 250);
        mainPanel.add(image);

        // Main heading with shadow effect
        JLabel heading = new JLabel("Thank you, " + name + "!");
        heading.setBounds(450, 50, 300, 50);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 36));
        heading.setForeground(Color.WHITE);

        JLabel shadow = new JLabel("Thank you, " + name + "!");
        shadow.setBounds(452, 52, 300, 50);
        shadow.setFont(new Font("Segoe UI", Font.BOLD, 36));
        shadow.setForeground(new Color(0, 0, 0, 80));

        mainPanel.add(shadow);
        mainPanel.add(heading);

        // Subtitle
        JLabel subtitle = new JLabel("Your Simple Minds Quiz Results");
        subtitle.setBounds(450, 100, 300, 30);
        subtitle.setFont(new Font("Segoe UI", Font.ITALIC, 18));
        subtitle.setForeground(new Color(255, 255, 255, 200));
        mainPanel.add(subtitle);

        // Score panel with progress bar
        JPanel scorePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20);
                g2d.setColor(new Color(255, 255, 255, 240));
                g2d.fill(roundedRectangle);
                g2d.setColor(new Color(255, 255, 255, 100));
                g2d.setStroke(new BasicStroke(2));
                g2d.draw(roundedRectangle);

                // Progress bar
                int progressWidth = (int) ((score / 100.0) * (getWidth() - 40));
                g2d.setColor(new Color(46, 204, 113));
                g2d.fillRect(20, 80, progressWidth, 20);
                g2d.setColor(new Color(70, 130, 180));
                g2d.drawRect(20, 80, getWidth() - 40, 20);

                g2d.dispose();
            }
        };
        scorePanel.setLayout(null);
        scorePanel.setBounds(450, 130, 300, 120);
        scorePanel.setOpaque(false);

        JLabel lblscore = new JLabel("Your Score");
        lblscore.setBounds(20, 20, 260, 30);
        lblscore.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblscore.setForeground(new Color(70, 130, 180));
        lblscore.setHorizontalAlignment(SwingConstants.CENTER);
        scorePanel.add(lblscore);

        JLabel scoreValue = new JLabel(String.valueOf(score));
        scoreValue.setBounds(20, 50, 260, 60);
        scoreValue.setFont(new Font("Segoe UI", Font.BOLD, 48));
        scoreValue.setForeground(new Color(46, 204, 113));
        scoreValue.setHorizontalAlignment(SwingConstants.CENTER);
        scorePanel.add(scoreValue);

        mainPanel.add(scorePanel);

        // Performance message
        String performanceMessage = getPerformanceMessage(score);
        JLabel performanceLabel = new JLabel("<html><div style='text-align: center;'>" + performanceMessage + "</div></html>");
        performanceLabel.setBounds(450, 260, 300, 60);
        performanceLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        performanceLabel.setForeground(getPerformanceColor(score));
        performanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(performanceLabel);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setBounds(450, 340, 300, 100);
        buttonPanel.setOpaque(false);

        playAgain = createStyledButton("Play Again", new Color(46, 204, 113));
        playAgain.setBounds(0, 10, 140, 45);
        playAgain.addActionListener(this);
        buttonPanel.add(playAgain);

        viewAnswers = createStyledButton("View Answers", new Color(52, 152, 219));
        viewAnswers.setBounds(160, 10, 140, 45);
        viewAnswers.addActionListener(this);
        buttonPanel.add(viewAnswers);

        exit = createStyledButton("Exit", new Color(231, 76, 60));
        exit.setBounds(80, 55, 140, 45);
        exit.addActionListener(this);
        buttonPanel.add(exit);

        mainPanel.add(buttonPanel);

        // Add keyboard navigation
        mainPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "playAgain");
        mainPanel.getActionMap().put("playAgain", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playAgain.doClick();
            }
        });

        // Add decorative elements
        addDecorativeElements(mainPanel);

        setSize(1200, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20);
                g2d.setColor(bgColor);
                g2d.fill(roundedRectangle);
                g2d.setColor(new Color(255, 255, 255, 50));
                g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight() / 2, 20, 20));
                g2d.dispose();
                super.paintComponent(g);
            }
        };

        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private String getPerformanceMessage(int score) {
        int correctAnswers = score / 10;
        if (score >= 80) {
            return "Excellent! You got " + correctAnswers + "/10 correct. You're a Java master!";
        } else if (score >= 60) {
            return "Great job! You got " + correctAnswers + "/10 correct. Solid Java skills!";
        } else if (score >= 40) {
            return "Good effort! You got " + correctAnswers + "/10 correct. Keep practicing!";
        } else {
            return "You got " + correctAnswers + "/10 correct. Time to brush up on Java!";
        }
    }

    private Color getPerformanceColor(int score) {
        if (score >= 80) {
            return new Color(46, 204, 113); // Green
        } else if (score >= 60) {
            return new Color(52, 152, 219); // Blue
        } else if (score >= 40) {
            return new Color(243, 156, 18); // Orange
        } else {
            return new Color(231, 76, 60); // Red
        }
    }

    private void addDecorativeElements(JPanel panel) {
        for (int i = 0; i < 5; i++) {
            JPanel circle = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(new Color(255, 255, 255, 30));
                    g2d.fillOval(0, 0, getWidth(), getHeight());
                    g2d.dispose();
                }
            };
            circle.setBounds(1000 + i * 30, 400 + i * 20, 20 + i * 5, 20 + i * 5);
            circle.setOpaque(false);
            panel.add(circle);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == playAgain) {
            setVisible(false);
            new Login();
        } else if (ae.getSource() == exit) {
            int choice = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to exit?",
                "Exit Application",
                JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        } else if (ae.getSource() == viewAnswers) {
            JOptionPane.showMessageDialog(this,
                "This feature is not yet implemented. Contact the developer for answer review functionality!",
                "View Answers",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Score("User", 0));
    }
}