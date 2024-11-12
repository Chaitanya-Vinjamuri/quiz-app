import java.util.ArrayList;
import java.util.HashMap;

public class QuizData {
    private ArrayList<String> questions;
    private ArrayList<String[]> optionsData;
    private HashMap<Integer, String> correctAnswers;

    public QuizData(ArrayList<String> questions, ArrayList<String[]> optionsData, HashMap<Integer, String> correctAnswers) {
        this.questions = questions;
        this.optionsData = optionsData;
        this.correctAnswers = correctAnswers;
    }

    public ArrayList<String> getQuestions()
    {
        return questions;
    }

    public ArrayList<String[]> getOptionsData()
    {
        return optionsData;
    }

    public HashMap<Integer, String> getCorrectAnswers()
    {
        return correctAnswers;
    }
}
