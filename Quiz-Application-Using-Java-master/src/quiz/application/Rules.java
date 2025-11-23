package quiz.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class Rules extends JFrame implements ActionListener {
    String name;
    JButton start, back;
    JPanel mainPanel;

    Rules(String name) {
        this.name = name;
        setTitle("Quiz Rules - Simple Minds");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

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

        // Welcome heading with shadow effect
        JLabel heading = new JLabel("Welcome, " + name + "!");
        heading.setBounds(450, 50, 300, 50);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 36));
        heading.setForeground(Color.WHITE);

        JLabel shadow = new JLabel("Welcome, " + name + "!");
        shadow.setBounds(452, 52, 300, 50);
        shadow.setFont(new Font("Segoe UI", Font.BOLD, 36));
        shadow.setForeground(new Color(0, 0, 0, 80));

        mainPanel.add(shadow);
        mainPanel.add(heading);

        // Subtitle
        JLabel subtitle = new JLabel("Please read the rules carefully before starting");
        subtitle.setBounds(450, 100, 350, 30);
        subtitle.setFont(new Font("Segoe UI", Font.ITALIC, 18));
        subtitle.setForeground(new Color(255, 255, 255, 200));
        mainPanel.add(subtitle);

        // Rules panel with modern styling
        JPanel rulesPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20);
                g2d.setColor(new Color(255, 255, 255, 230));
                g2d.fill(roundedRectangle);
                g2d.setColor(new Color(255, 255, 255, 100));
                g2d.setStroke(new BasicStroke(2));
                g2d.draw(roundedRectangle);
                g2d.dispose();
            }
        };
        rulesPanel.setLayout(null);
        rulesPanel.setBounds(300, 150, 600, 250);
        rulesPanel.setOpaque(false);

        // Rules content
        JLabel rules = new JLabel();
        rules.setBounds(20, 20, 560, 210);
        rules.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        rules.setForeground(new Color(50, 50, 50));
        rules.setText(
            "<html><div style='text-align: left; line-height: 1.6;'>" +
                "<b style='color: #2E86AB; font-size: 18px;'>Quiz Rules:</b><br><br>" +
                "1. The quiz consists of 10 multiple-choice questions.<br>" +
                "2. You have 15 seconds to answer each question.<br>" +
                "3. Each correct answer awards 10 points.<br>" +
                "4. A 50-50 lifeline is available once during the quiz.<br>" +
                "5. Questions are randomized for each session.<br>" +
                "6. Click 'Next' to proceed or 'Submit' on the final question.<br>" +
                "7. Stay calm and give your best effort. Good luck!" +
            "</div></html>"
        );
        rulesPanel.add(rules);
        mainPanel.add(rulesPanel);

        // Buttons
        back = createStyledButton("Back", new Color(231, 76, 60));
        back.setBounds(400, 420, 150, 45);
        back.addActionListener(this);
        mainPanel.add(back);

        start = createStyledButton("Start Quiz", new Color(46, 204, 113));
        start.setBounds(600, 420, 150, 45);
        start.addActionListener(this);
        mainPanel.add(start);

        // Add keyboard navigation
        mainPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "startQuiz");
        mainPanel.getActionMap().put("startQuiz", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start.doClick();
            }
        });

        mainPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "goBack");
        mainPanel.getActionMap().put("goBack", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                back.doClick();
            }
        });

        // Add decorative elements
        addDecorativeElements();

		setSize(1200, 650);
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

    private void addDecorativeElements() {
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
            mainPanel.add(circle);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == start) {
            setVisible(false);
            new Quiz(name);
        } else if (ae.getSource() == back) {
            setVisible(false);
            new Login();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Rules("User"));
    }
}