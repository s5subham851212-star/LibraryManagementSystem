import java.awt.*;
import javax.swing.*;

public class Dashboard extends JFrame {

    public Dashboard() {

        setTitle("Library Management System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel title = new JLabel(
                "Library Management System",
                SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 24));

        JButton booksButton =
                new JButton("Manage Books");

        booksButton.addActionListener(
                e -> new BookWindow()
        );

        JButton usersButton =
                new JButton("Users");

        usersButton.addActionListener(
                e -> new UserWindow()
        );

        JButton issueButton =
                new JButton("Issue Book");

        issueButton.addActionListener(
                e -> new IssueBook()
        );

        JButton returnButton =
                new JButton("Return Book");

        returnButton.addActionListener(
                e -> new ReturnBook()
        );

        JButton logoutButton =
                new JButton("Logout");

        logoutButton.addActionListener(e -> {

            dispose();

            new Login();
        });

        JPanel panel =
                new JPanel(
                        new GridLayout(5, 1, 10, 10)
                );

        panel.add(booksButton);
        panel.add(usersButton);
        panel.add(issueButton);
        panel.add(returnButton);
        panel.add(logoutButton);

        add(title, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }
}