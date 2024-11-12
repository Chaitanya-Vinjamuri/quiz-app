import javax.swing.*;
import java.awt.*;

public class QuizApp {
    private JFrame frame;
    private JPanel panel;
    private DatabaseManager dbManager;
    private UserInputPanel userInputPanel;
    private QuizPanel quizPanel;
    private String userId, userName;

    public QuizApp() {
        dbManager = new DatabaseManager();
        frame = new JFrame("Welcome to the Quiz");
        frame.setSize(850, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        panel = new JPanel(new CardLayout());
        userInputPanel = new UserInputPanel();
        quizPanel = new QuizPanel(dbManager.loadQuizData(), this);  // Pass QuizApp reference to QuizPanel

        panel.add(userInputPanel, "InputScreen");
        panel.add(quizPanel, "QuizScreen");

        frame.add(panel);

        // Add ActionListener for Enter button
        userInputPanel.getEnterButton().addActionListener(e -> handleUserInput());

        frame.setVisible(true);
    }

    // Method to handle user input when "Enter" button is pressed
    private void handleUserInput() {
        userId = userInputPanel.getUserId();
        userName = userInputPanel.getUserName();

        if (userId.isEmpty() || userName.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter both ID and Name", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            CardLayout cl = (CardLayout) panel.getLayout();
            cl.show(panel, "QuizScreen");
            quizPanel.setUserDetails(userId, userName);
        }
    }

    public void onQuizCompleted(int score, int totalQuestions) {
        dbManager.saveQuizResult(userId, userName, score, totalQuestions);
        JOptionPane.showMessageDialog(frame, "Quiz completed! Results saved to database.", "Quiz Completed", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        new QuizApp();
    }
}
