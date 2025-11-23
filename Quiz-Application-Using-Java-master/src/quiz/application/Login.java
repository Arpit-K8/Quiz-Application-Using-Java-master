package quiz.application;

import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class Login extends JFrame implements ActionListener {
    JButton rules, back;
    JTextField tfname;
    JPanel mainPanel;

    Login() {
        setTitle("Simple Minds - Quiz Application");
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

        // Add background image with overlay
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/login.png"));
        Image img = i1.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(img);
        JLabel image = new JLabel(scaledIcon);
        image.setBounds(0, 0, 600, 500);

        JPanel overlay = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(0, 0, 0, 100));
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        };
        overlay.setBounds(0, 0, 600, 500);
        overlay.setOpaque(false);

        mainPanel.add(image);
        mainPanel.add(overlay);

        // Animated heading
        JLabel heading = new JLabel("Simple Minds");
        heading.setBounds(750, 60, 400, 60);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 48));
        heading.setForeground(Color.WHITE);
        heading.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        JLabel shadow = new JLabel("Simple Minds");
        shadow.setBounds(752, 62, 400, 60);
        shadow.setFont(new Font("Segoe UI", Font.BOLD, 48));
        shadow.setForeground(new Color(0, 0, 0, 80));

        mainPanel.add(shadow);
        mainPanel.add(heading);

        // Subtitle
        JLabel subtitle = new JLabel("Test Your Knowledge");
        subtitle.setBounds(750, 120, 400, 30);
        subtitle.setFont(new Font("Segoe UI", Font.ITALIC, 18));
        subtitle.setForeground(new Color(255, 255, 255, 200));
        mainPanel.add(subtitle);

        // Name input section
        JLabel name = new JLabel("Enter your name");
        name.setBounds(810, 200, 300, 25);
        name.setFont(new Font("Segoe UI", Font.BOLD, 20));
        name.setForeground(Color.WHITE);
        mainPanel.add(name);

        tfname = new JTextField();
        tfname.setBounds(735, 240, 300, 40);
        tfname.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        tfname.setForeground(new Color(50, 50, 50));
        tfname.setBackground(Color.WHITE);
        tfname.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        tfname.setCaretColor(new Color(70, 130, 180));
        tfname.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    rules.doClick();
                }
            }
        });
        mainPanel.add(tfname);

        // Styled buttons
        rules = createStyledButton("Start Quiz", new Color(46, 204, 113));
        rules.setBounds(735, 300, 140, 45);
        rules.addActionListener(this);
        mainPanel.add(rules);

        back = createStyledButton("Exit", new Color(231, 76, 60));
        back.setBounds(895, 300, 140, 45);
        back.addActionListener(this);
        mainPanel.add(back);

        addDecorativeElements();

        setSize(1200, 500);
        setLocationRelativeTo(null);
        setVisible(true);

        tfname.requestFocusInWindow();
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
            circle.setBounds(650 + i * 30, 400 + i * 20, 20 + i * 5, 20 + i * 5);
            circle.setOpaque(false);
            mainPanel.add(circle);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == rules) {
            String name = tfname.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Please enter your name to continue!",
                    "Name Required",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!name.matches("[a-zA-Z ]+")) {
                JOptionPane.showMessageDialog(this,
                    "Name should contain only letters and spaces!",
                    "Invalid Name",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            setVisible(false);
            new Rules(name);
        } else if (ae.getSource() == back) {
            int choice = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to exit?",
                "Exit Application",
                JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login());
    }
}