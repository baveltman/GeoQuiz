package geoquiz.apps.baveltman.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends ActionBarActivity {

    private Button mShowAnswerButton;
    private TextView mAnswerToQuestionText;
    private boolean mHasCheated = false;
    private boolean mAnswerIsTrue = false;

    public static final String EXTRA_ANSWER_WAS_SHOWN =
            "baveltman.apps.geoquiz.answer_shown";

    //key for bundle to retrieve index state
    private static final String HAS_CHEATED = "hasCheated";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerIsTrue = getIntent().getBooleanExtra(QuizActivity.EXTRA_ANSWER_IS_TRUE, false);

        bindUiElementReferences();
        bindOnClickListeners();
        seeIfUserHasCheated(savedInstanceState);
        setAnswerShownResult(mHasCheated);
    }

    private void seeIfUserHasCheated(Bundle savedInstanceState) {
        if (savedInstanceState != null){
            mHasCheated = savedInstanceState.getBoolean(HAS_CHEATED, false);

            if(mHasCheated){
                setAnswerText();
            }
        }
    }

    private void setAnswerText() {
        if (mAnswerIsTrue){
            mAnswerToQuestionText.setText(R.string.true_button);
        } else {
            mAnswerToQuestionText.setText(R.string.false_button);
        }
    }

    private void bindUiElementReferences() {
        mShowAnswerButton = (Button)findViewById(R.id.showAnswerButton);
        mAnswerToQuestionText = (TextView)findViewById(R.id.answerTextView);
    }

    private void bindOnClickListeners() {
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setAnswerText();
                mHasCheated = true;
                setAnswerShownResult(mHasCheated);
            }
        });
    }

    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_WAS_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(HAS_CHEATED, mHasCheated);
    }
}
