package geoquiz.apps.baveltman.geoquiz;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.util.Log;


public class QuizActivity extends ActionBarActivity {

    //logging tag
    private static final String TAG = "QuizActivity";

    //key for bundle to retrieve index state
    private static final String KEY_INDEX = "index";

    public static final String EXTRA_ANSWER_IS_TRUE =
            "baveltman.apps.geoquiz.answer_is_true";

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;

    private TextView mQuestionTextView;
    private TextView mApiLevel;

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

        Log.d(TAG, "onCreate(Bundle) called");

        setContentView(R.layout.activity_quiz);

        bindUiElementReferences();
        bindOnClickListeners();
        BindCurrentQuestionIndex(savedInstanceState);
        updateQuestion();
        bindAndroidVersion();

    }

    /**
     * displays current API version to user
     */
    private void bindAndroidVersion() {
        int apiLevel = Build.VERSION.SDK_INT;
        mApiLevel.setText("API Level " + apiLevel);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        boolean isCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_WAS_SHOWN, false);

        if (isCheater){
            Toast.makeText(QuizActivity.this,
                    R.string.judgment_toast,
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * checks the last saved state to see if we had updated the current question index
     * maps the last saved index to mCurrentIndex
     * @param savedInstanceState the last saved state of the app
     */
    private void BindCurrentQuestionIndex(Bundle savedInstanceState){
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }
    }

    /*
    Sets up onClick listeners for the UI elements in the layout
     */
    private void  bindOnClickListeners() {
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
                goToNextQuestion();
            }

        });

        mPrevButton.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View v){
               goToPrevQuestion();
           }
        });

        mQuestionTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                goToNextQuestion();
            }
        });

        mCheatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //start cheat activity
                boolean answerToCurrentQuestion = mQuestionBank[mCurrentIndex].isTrueQuestion();
                Intent i = new Intent(QuizActivity.this, CheatActivity.class);
                i.putExtra(EXTRA_ANSWER_IS_TRUE, answerToCurrentQuestion);
                startActivityForResult(i, 0);
            }
        });
    }

    /**
     * displays previous question for the user
     * goes back to last question if called on question stored in first index of question store
     */
    private void goToPrevQuestion() {
        --mCurrentIndex;
        if (mCurrentIndex < 0){
            mCurrentIndex = mQuestionBank.length - 1;
        }
        updateQuestion();
    }

    /**
     * displays next question for the user
     * goes back to first question if called on question stored in last index of question store
     */
    private void goToNextQuestion(){
        ++mCurrentIndex;
        if (mCurrentIndex >= mQuestionBank.length){
            mCurrentIndex = 0;
        }
        updateQuestion();
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
    binds references to view object defined in the layout file
     */
    private void bindUiElementReferences(){
        //get references to the inflated view objects
        mTrueButton = (Button)findViewById(R.id.true_button);
        mFalseButton = (Button)findViewById(R.id.false_button);
        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mNextButton = (ImageButton)findViewById(R.id.next_button);
        mPrevButton = (ImageButton)findViewById(R.id.prev_button);
        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
        mApiLevel = (TextView)findViewById(R.id.api_level_text);
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
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
