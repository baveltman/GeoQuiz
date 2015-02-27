package geoquiz.apps.baveltman.geoquiz;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;


public class QuizActivity extends ActionBarActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;

    private TextView mQuestionTextView;

    private TrueFalse[] mQuestionBank = new TrueFalse[] {
            new TrueFalse(R.string.question_oceans, true),
            new TrueFalse(R.string.question_mideast, false),
            new TrueFalse(R.string.question_africa, false),
            new TrueFalse(R.string.question_americas, true),
            new TrueFalse(R.string.question_asia, true),
    };

    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        BindButtons();
        BindFirstQuestion();
        BindButtonListeners();

    }

    /*
    Sets up listeners for true, false, and next buttons
     */
    private void BindButtonListeners() {
        //set onclick listeners for buttons
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ++mCurrentIndex;
                if (mCurrentIndex >= mQuestionBank.length){
                    mCurrentIndex = 0;
                }
                updateQuestion();
            }

        });
    }

    /**
     * evaluates whether the user has selected the correct answer and makes a toast accordingly
     * @param answer is the answer, true or false, selected by the user
     */
    private void checkAnswer(boolean answer){

        int messageId = 0;

        if (mQuestionBank[mCurrentIndex].isTrueQuestion() == answer){
            messageId = R.string.correct_toast;
        } else {
            messageId = R.string.incorrect_toast;
        }

        Toast.makeText(QuizActivity.this,
                messageId,
                Toast.LENGTH_SHORT).show();
    }

    /*
    updates to the next question in the question bank
     */
    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getQuestion();
        mQuestionTextView.setText(question);
    }

    /*
    Creates and binds the first question for the geoquiz game
     */
    private void BindFirstQuestion() {
        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
        updateQuestion();
    }


    /*
    binds references to the true, false, and next buttons
     */
    private void BindButtons(){
        //get references to the inflated view objects for the buttons
        mTrueButton = (Button)findViewById(R.id.true_button);
        mFalseButton = (Button)findViewById(R.id.false_button);
        mNextButton = (Button)findViewById(R.id.next_button);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
