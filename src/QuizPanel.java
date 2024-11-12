import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class QuizPanel extends JPanel {
    private String userId, userName;
    private JLabel questionLabel;
    private JRadioButton[] options;
    private ButtonGroup bg;
    private JButton prevButton, nextButton, submitButton;
    private QuizData quizData;
    private HashMap<Integer, String> userAnswers = new HashMap<>();
    private int currentQuestionIndex = 0;
    private QuizApp quizApp;  // Reference to QuizApp

    public QuizPanel(QuizData quizData, QuizApp quizApp) {
        this.quizData = quizData;
        this.quizApp = quizApp;  // Initialize QuizApp reference
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 248, 255));

        questionLabel = new JLabel("", JLabel.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(questionLabel, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        options = new JRadioButton[4];
        bg = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            bg.add(options[i]);
            optionsPanel.add(options[i]);
        }
        add(optionsPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        prevButton = new JButton("Previous");
        nextButton = new JButton("Next");
        submitButton = new JButton("Submit");
        submitButton.setEnabled(false);

        prevButton.addActionListener(e -> showPreviousQuestion());
        nextButton.addActionListener(e -> showNextQuestion());
        submitButton.addActionListener(e -> submitQuiz());

        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(submitButton);
        add(buttonPanel, BorderLayout.SOUTH);

        loadQuestion();
    }

    public void setUserDetails(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    private void loadQuestion() {
        questionLabel.setText("<html><center>" + quizData.getQuestions().get(currentQuestionIndex) + "</center></html>");
        String[] currentOptions = quizData.getOptionsData().get(currentQuestionIndex);
        for (int i = 0; i < options.length; i++) {
            options[i].setText(currentOptions[i]);
            options[i].setSelected(false);
        }
        bg.clearSelection();
    }

    private void showNextQuestion() {
        saveAnswer();
        if (currentQuestionIndex < quizData.getQuestions().size() - 1) {
            currentQuestionIndex++;
            loadQuestion();
            submitButton.setEnabled(currentQuestionIndex == quizData.getQuestions().size() - 1);
        }
    }

    private void showPreviousQuestion() {
        saveAnswer();
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
            loadQuestion();
        }
    }

    private void saveAnswer() {
        for (JRadioButton option : options) {
            if (option.isSelected()) {
                userAnswers.put(currentQuestionIndex, option.getText());
            }
        }
    }

    private void submitQuiz() {
        int score = 0;
        for (int i = 0; i < quizData.getQuestions().size(); i++) {
            if (quizData.getCorrectAnswers().get(i).equals(userAnswers.get(i))) {
                score++;
            }
        }
        // Call method from QuizApp to handle quiz completion
        quizApp.onQuizCompleted(score, quizData.getQuestions().size());
    }
}
