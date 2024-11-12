import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseManager {
    private static final String URL = "jdbc:postgresql://localhost:5432/mydb";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Chaithu@2006";

    public QuizData loadQuizData() {
        ArrayList<String> questions = new ArrayList<>();
        ArrayList<String[]> optionsData = new ArrayList<>();
        HashMap<Integer, String> correctAnswers = new HashMap<>();

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String questionQuery = "SELECT question_id, question_text FROM quiz_questions";
            try (Statement questionStmt = con.createStatement();
                 ResultSet questionRs = questionStmt.executeQuery(questionQuery)
                ) {

                while (questionRs.next()) {
                    int questionId = questionRs.getInt("question_id");
                    String questionText = questionRs.getString("question_text");
                    questions.add(questionText);

                    String[] optionArray = new String[4];
                    String optionsQuery = "SELECT option_text, is_correct FROM quiz_options WHERE question_id = ?";
                    try (PreparedStatement optionsStmt = con.prepareStatement(optionsQuery)) {
                        optionsStmt.setInt(1, questionId);
                        try (ResultSet optionsRs = optionsStmt.executeQuery()) {
                            int i = 0;
                            while (optionsRs.next()) {
                                String optionText = optionsRs.getString("option_text");
                                boolean isCorrect = optionsRs.getBoolean("is_correct");
                                optionArray[i++] = optionText;
                                if (isCorrect) {
                                    correctAnswers.put(questionId - 1, optionText);
                                }
                            }
                            optionsData.add(optionArray);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new QuizData(questions, optionsData, correctAnswers);
    }

    public void saveQuizResult(String userId, String userName, int score, int totalQuestions) {
        String sql = "INSERT INTO quiz_results (user_id, user_name, score, total_questions) VALUES (?, ?, ?, ?)";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, userId);
            pst.setString(2, userName);
            pst.setInt(3, score);
            pst.setInt(4, totalQuestions);

            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
