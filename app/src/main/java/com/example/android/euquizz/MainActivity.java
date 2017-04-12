package com.example.android.euquizz;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int REFRESH_SCREEN = 1;
    private ViewSwitcher switcher; //View that switches between two views: Presentation View and Question View
    private TextView titleTextView; // Title of the question view
    private TextView qTextView; // question sentence of the question view
    private TextView resultTextView; // result (wrong or right) of the question view
    private List<Button> btn = new ArrayList<Button>(4); // 4 possible answer buttons of the question view
    private Button nextButton; // button next of the question view
    private int score = 0; // score of the player
    private List<QuizzQuestion> qQuestions = new ArrayList<>(20); // List of all the questions
    private List<Integer> order = new ArrayList<>(4); // List used for shuffling of the 4 answers
    private int qNumber = 0; // question number, from 0 to 9
    private boolean qAnswered = false; // states if the user has already answered to the current question
    private TextView quizzTitleTextview; // title of the presentation view
    private TextView quizzScoreTextview; // points statement of the presentation view
    private TextView quizzMessageTextview; // message of the presentation view
    private Button quizzStartButton; // start button of the presentation view


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* Initialization of all the variables */
        switcher = (ViewSwitcher) findViewById(R.id.profileSwitcher);
        titleTextView = (TextView) findViewById(R.id.title_textview);
        qTextView = (TextView) findViewById(R.id.question_textview);
        resultTextView = (TextView) findViewById(R.id.result_textview);
        quizzTitleTextview = (TextView) findViewById(R.id.eu_quizz_title_textview);
        quizzScoreTextview = (TextView) findViewById(R.id.eu_quizz_score_textview);
        quizzMessageTextview = (TextView) findViewById(R.id.eu_quizz_message_textview);
        quizzStartButton = (Button) findViewById(R.id.start_button);
        btn.add(0, (Button) findViewById(R.id.answer1_button));
        btn.add(1, (Button) findViewById(R.id.answer2_button));
        btn.add(2, (Button) findViewById(R.id.answer3_button));
        btn.add(3, (Button) findViewById(R.id.answer4_button));
        nextButton = (Button) findViewById(R.id.next_button);
        order.add(0, 0);
        order.add(1, 1);
        order.add(2, 2);
        order.add(3, 3);
        loadQuestions(); // load all the questions
    }


    /**
     * clickStart is used when the user clicks on the start button. It must reinitialize the parameters like score and qNumber, and shuffle the questions.
     *
     * @param view is the clicked button
     */
    public void clickStart(View view) {
        Collections.shuffle(order);
        Collections.shuffle(qQuestions);
        qNumber = 0;
        score = 0;
        showQuestion(qQuestions.get(qNumber), qNumber, order);
        switcher.showNext();
    }

    /**
     * clickAnswer is used when the user clicks on one of the 4 answers. Must check if the answer is right or wrong, and shows the button next
     *
     * @param view is the clicked button
     */
    public void clickAnswer(View view) {
        int clickedButtonNumber = Integer.parseInt(view.getTag().toString()) - 1;
        if (qAnswered) {
            return;
        }
        qAnswered = true;
        if (order.get(clickedButtonNumber) == qQuestions.get(qNumber).getRightAnswer()) {
            score++;
            view.setBackgroundColor(Color.GREEN);
            resultTextView.setText(R.string.right);
            resultTextView.setTextColor(Color.GREEN);
        } else {
            view.setBackgroundColor(Color.RED);
            resultTextView.setText(R.string.wrong);
            resultTextView.setTextColor(Color.RED);
        }
        resultTextView.setVisibility(View.VISIBLE);
        nextButton.setVisibility(View.VISIBLE);
    }

    /**
     * clickNext is launched when the user clicks on the button Next. Prepares the next question or sends the user to the final page if the questions are done
     *
     * @param view is the clicked button
     */
    public void clickNext(View view) {
        if (qNumber == 9) {
            if (score == 10) {
                quizzTitleTextview.setText(R.string.perfect);
                quizzMessageTextview.setText(R.string.perfect_comment);
            } else if (score >= 8) {
                quizzTitleTextview.setText(R.string.very_good);
                quizzMessageTextview.setText(R.string.very_good_comment);
            } else if (score >= 5) {
                quizzTitleTextview.setText(R.string.good);
                quizzMessageTextview.setText(R.string.good_comment);
            } else {
                quizzTitleTextview.setText(R.string.can_do_better);
                quizzMessageTextview.setText(R.string.can_do_better_comment);
            }
            quizzScoreTextview.setText(getString(R.string.score, "" + score));
            quizzScoreTextview.setVisibility(View.VISIBLE);
            quizzStartButton.setText(R.string.play_again);
            switcher.showPrevious();
        } else {
            qNumber++;
            Collections.shuffle(order);
            showQuestion(qQuestions.get(qNumber), qNumber, order);
        }

    }

    /**
     * loadQuestions loads all the questions from data. For now, this is hardcoded, but will be externalized by reading a file in the future.
     */
    private void loadQuestions() {
        qQuestions.add(qQuestions.size(), new QuizzQuestion("When was created the European Union?", "1945", "2001", "1957", "1889", 2));
        qQuestions.add(qQuestions.size(), new QuizzQuestion("What country financially contributed most to the European Union in 2015?", "Germany", "United Kingdom", "France", "Italy"));
        qQuestions.add(qQuestions.size(), new QuizzQuestion("What treaty signed in 1992 formally creates the European Union?", "Rome", "Maastricht", "Lisbon", "Paris", 1));
        qQuestions.add(qQuestions.size(), new QuizzQuestion("How many countries were in the European Union in 2016?", "6", "12", "25", "28", 3));
        qQuestions.add(qQuestions.size(), new QuizzQuestion("How many stars are there on the European Union flag?", "10", "12", "15", "28", 1));
        qQuestions.add(qQuestions.size(), new QuizzQuestion("When was created the European Central Bank?", "1998", "1945", "2001", "1985"));
        qQuestions.add(qQuestions.size(), new QuizzQuestion("How many countries use the Euro currency in 2017?", "19", "15", "12", "28"));
        qQuestions.add(qQuestions.size(), new QuizzQuestion("How many countries founded the European Union?", "6", "2", "3", "12"));
        qQuestions.add(qQuestions.size(), new QuizzQuestion("When were the Euro banknotes and coins introduced?", "2002", "2000", "1998", "2004"));
        qQuestions.add(qQuestions.size(), new QuizzQuestion("Who is the president of the European Commission in 2017?", "Jean-Claude Juncker", "José Manuel Barroso", "Jean-Claude Trichet", "Mario Draghi", 0));
        qQuestions.add(qQuestions.size(), new QuizzQuestion("What treaty signed in 1957 creates the European Economic Community?", "Rome", "Maastricht", "Lisbon", "Paris"));
        qQuestions.add(qQuestions.size(), new QuizzQuestion("What city is the capital of the European Union?", "Brussels", "Paris", "London", "Strasbourg"));
        qQuestions.add(qQuestions.size(), new QuizzQuestion("What country will leave the European Union after a referendum in 2016?", "United Kingdom", "Greece", "Spain", "Turkey"));
        qQuestions.add(qQuestions.size(), new QuizzQuestion("How many inhabitants are there in the European Union in 2017?", "500 million", "230 million", "1 billion", "780 million"));
        qQuestions.add(qQuestions.size(), new QuizzQuestion("What is the smallest country in the European Union?", "Malta", "Luxembourg", "Cyprus", "Slovenia"));
        qQuestions.add(qQuestions.size(), new QuizzQuestion("What is the anthem of the European Union?", "Ode to Joy", "God save the Queen", "United", "Enchanted Flute"));
        qQuestions.add(qQuestions.size(), new QuizzQuestion("What country is the latest country to join the European Union in 2013?", "Croatia", "Slovenia", "Sweden", "Poland"));
        qQuestions.add(qQuestions.size(), new QuizzQuestion("How much was the European Union's Gross Domestic Product in 2016?", "16 trillion €", "257 billion €", "800 million €", "483 trillion €"));
        qQuestions.add(qQuestions.size(), new QuizzQuestion("How many official languages are there in the European Union?", "24", "3", "1", "12"));
        qQuestions.add(qQuestions.size(), new QuizzQuestion("How many members of the European Parliament are there?", "751", "28", "1321", "251"));
        qQuestions.add(qQuestions.size(), new QuizzQuestion("Who is the President of the European Council?", "Donald Tusk", "Mario Draghi", "José Manuel Barroso", "Donald Trump"));
        qQuestions.add(qQuestions.size(), new QuizzQuestion("What treaty abolished the border checks in the European Union?", "Schengen", "Madrid", "Rome", "Lisbon"));
        qQuestions.add(qQuestions.size(), new QuizzQuestion("When did the United Kingdom join the European Union?", "1969", "1957", "1961", "1988"));
        qQuestions.add(qQuestions.size(), new QuizzQuestion("What is the name of the rules that define whether a country is eligible to join the European Union?", "Copenhagen criteria", "Berlin criteria", "Oslo criteria", "Rome criteria"));
        qQuestions.add(qQuestions.size(), new QuizzQuestion("Is the European Union's territory bigger or smaller than the USA's?", "Smaller, x0.5", "Same", "Bigger, x2", "Bigger, x3"));
        qQuestions.add(qQuestions.size(), new QuizzQuestion("What was the largest city by population (8.6 billion) of the European Union in 2015?", "London", "Berlin", "Rome", "Copenhagen"));
    }

    /**
     * showQuestion prepares the View in order to show the current question. The 4 answers have already been shuffled before.
     *
     * @param q       is the current question
     * @param qNumber is the number of the question (out of 10)
     * @param order   is the shuffling of the 4 answers
     */
    private void showQuestion(QuizzQuestion q, int qNumber, List<Integer> order) {
        titleTextView.setText(getString(R.string.question_number, "" + (qNumber + 1)));
        qTextView.setText(q.getQuestion());
        for (int i = 0; i < 4; i++) {
            btn.get(i).setText(q.getAnswers().get(order.get(i)));
            btn.get(i).setBackgroundResource(android.R.drawable.btn_default);
        }
        resultTextView.setVisibility(View.INVISIBLE);
        nextButton.setVisibility(View.INVISIBLE);
        qAnswered = false;
    }
}

/**
 * QuizzQuestion is a created class that collects all the data of one question in one class: the question sentence, the 4 answers and the number of the right answer
 */
class QuizzQuestion {
    private String question; //question sentence
    private List<String> answers = new ArrayList<>(4); // 4 answers
    private int rightAnswer; // number of the right answer

    // Constructor
    QuizzQuestion(String question, List<String> answers, int rightAnswer) {
        this.setQuestion(question);
        this.setAnswers(answers);
        this.setRightAnswer(rightAnswer);
    }

    /**
     * Constructor with 4 answers as parameters. The right answer is assumed to be the first one.
     *
     * @param question question sentence
     * @param answer1  first answer. Is the right answer
     * @param answer2  2nd answer
     * @param answer3  3rd answer
     * @param answer4  4th answer
     */
    QuizzQuestion(String question, String answer1, String answer2, String answer3, String answer4) {
        this(question, answer1, answer2, answer3, answer4, 0);
    }

    /**
     * Constructor with 4 answers as parameters.
     *
     * @param question    question sentence
     * @param answer1     1st answer
     * @param answer2     2nd answer
     * @param answer3     3rd answer
     * @param answer4     4th answer
     * @param rightAnswer number of the right answer. Ranges from 0 to 3.
     */
    QuizzQuestion(String question, String answer1, String answer2, String answer3, String answer4, int rightAnswer) {
        this.setQuestion(question);
        List<String> tempAnswer = new ArrayList<>(4);
        tempAnswer.add(0, answer1);
        tempAnswer.add(1, answer2);
        tempAnswer.add(2, answer3);
        tempAnswer.add(3, answer4);
        this.setAnswers(tempAnswer);
        this.setRightAnswer(rightAnswer);
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public int getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(int rightAnswer) {
        this.rightAnswer = rightAnswer;
    }
}
