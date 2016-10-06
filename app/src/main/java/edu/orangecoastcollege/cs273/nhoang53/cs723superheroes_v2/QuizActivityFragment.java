package edu.orangecoastcollege.cs273.nhoang53.cs723superheroes_v2;

/**
 * Nguyen Hoang C02288487
 * Project 2: CS273 Superheroes
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.os.Handler;

import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.content.Context;

/**
 * A placeholder fragment containing a simple view.
 */
public class QuizActivityFragment extends Fragment {

    private static final String TAG = "SuperpowerQuiz Activity";
    private static final int SUPERHEROES_IN_QUIZ = 10;

    private FragmentActivity context = getActivity(); // get FragmentActivity, // getContext() -> return Context
    private ArrayList<Superheroes> allSuperheroes;
    private String quizType; // the quiz type that user choose on setting
    private List<String> usernameList; // username and image name
    private List<String> nameList; // name of superheroes
    private List<String> oneThingList; // one thing about a hero
    private List<String> superpowerList; // superpower of a hero
    private List<String> imageNameList; // image of a hero
    private String correctAnswer; // correct answer depend on what game was playing
    private int numberCorrect; // number of correct guess
    private int totalGuesses; // total number of guess made (correct and wrong)
    private SecureRandom random;
    private Handler handler;
    private Animation shakeAnimation;
    private List<String> quizList; // save the data of quiztype base on setting

    private LinearLayout quizLinearLayout; // layout that contain the quiz
    private TextView questionNumberTextView; // show current question
    private ImageView heroImageview;
    private TextView answerTextView; // displays corect answer
    private LinearLayout[] guessLinearLayouts; // rows of answer button
    private TextView guessTextView;     // guess type of quiz

    /**
     * Configures the QuizActivityFragment when its View is created.
     * @param inflater the layout inflater
     * @param container The view group contrain in which the fragment resides
     * @param savedInstanceState Any saved state to restore in this fragment
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view =
                inflater.inflate(R.layout.fragment_quiz, container, false);

        allSuperheroes = new ArrayList<Superheroes>();
        usernameList = new ArrayList<>();
        nameList = new ArrayList<>();
        oneThingList = new ArrayList<>();
        usernameList = new ArrayList<>();
        superpowerList = new ArrayList<>();
        imageNameList = new ArrayList<>();
        quizList = new ArrayList<>();
        random = new SecureRandom();
        handler = new android.os.Handler();

        // get references to GUI components
        quizLinearLayout =
                (LinearLayout) view.findViewById(R.id.quizLinearLayout);
        heroImageview = (ImageView) view.findViewById(R.id.heroImageView);
        guessLinearLayouts = new LinearLayout[2];
        guessLinearLayouts[0] = (LinearLayout) view.findViewById(R.id.row1LinearLayout);
        guessLinearLayouts[1] = (LinearLayout) view.findViewById(R.id.row2LinearLayout);
        answerTextView = (TextView) view.findViewById(R.id.answerTextView);
        guessTextView = (TextView) view.findViewById(R.id.guessTextView);

        questionNumberTextView = (TextView) view.findViewById(R.id.questionNumberTextView);
        questionNumberTextView.setText(getString(R.string.question, 1, SUPERHEROES_IN_QUIZ));



        // Load the shake animation
        /*shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.incorrect_shake);
        shakeAnimation.setRepeatCount(3);*/

        // configure listeners for the guess Buttons
        for(LinearLayout row : guessLinearLayouts) {
            for (int column = 0; column < row.getChildCount(); column++) {
                Button button = (Button) row.getChildAt(column);
                button.setOnClickListener(guessButtonListener);
            }
        }

        // set questionNumberTextView's text


        return view; // return the fragment's view for display
    }

    /**
     * changeQuizType is called from QuizActivity when the app is launched and each time the
     * user changes the quiz type they want to play.
     * It will change the quiz type and reset the quiz
     * @param sharedPreferences The shared preferences from preferences.xml
     */
    public void changeQuizType(SharedPreferences sharedPreferences, ArrayList<Superheroes> allSuper){
        // get the number of guess buttons that should be displayed
        quizType = sharedPreferences.getString(QuizActivity.QUIZ_TYPE, null);

        // reset quiz
        correctAnswer = "";
        numberCorrect = 0;
        totalGuesses = 0;
        quizList.clear();

        // get data
        if(allSuperheroes.isEmpty())
        {
            allSuperheroes = allSuper;
            // add data to List
            for (int i = 0; i < allSuperheroes.size(); i++)
            {
                Superheroes heroes = allSuperheroes.get(i);
                nameList.add(heroes.getName());
                oneThingList.add(heroes.getOneThing());
                superpowerList.add(heroes.getSuperpower());
                usernameList.add(heroes.getUserName());
                imageNameList.add(heroes.getImageName());
            }
        }

        if(quizType.equals("Superheroes Name"))
        {
            System.out.println("QUIZTYPE: " + quizType);
            guessTextView.setText("Guess The " + quizType);
            guessSuperheroesQuiz();
        }
        else if(quizType.equals("Superpower"))
        {
            System.out.println("QUIZTYPE: " + quizType);
            guessTextView.setText("Guess The " + quizType);
            guessSuperheroesQuiz();
        }
        else{
            System.out.println("QUIZTYPE: " + quizType);
            guessTextView.setText("Guess The " + quizType);
            guessSuperheroesQuiz();
        }
    }

    /**
     * Configure and start up a new quiz based on the settings.
     */
    public void guessSuperheroesQuiz(){
        int flagCounter = 0;
        int numberOfHeroes = nameList.size();
        System.out.println("size: " + numberOfHeroes);

        // add SUPERHEROES_IN_QUIZ random file names to the nameList
        while(flagCounter < SUPERHEROES_IN_QUIZ)
        {
            int randomIndex = random.nextInt(numberOfHeroes);
            String quiz;

            if(quizType.equals("Superheroes Name"))
                quiz = nameList.get(randomIndex); // get the random hero name
            else if(quizType.equals("Superpower"))
                quiz = superpowerList.get(randomIndex); // get the random superpower
            else
                quiz = oneThingList.get(randomIndex); // get the random oneThing

            if(!quizList.contains(quiz))
            {
                quizList.add(quiz); // // add the index of superheroes to List
                ++flagCounter;

                System.out.println("Quiz Type name: " + quiz);
            }
        }

        loadNextQuiz(); // start the quiz by loading the first hero*/
    }

    /**
     * After user guesses a superhero correctly, load the next hero.
     */
    private  void loadNextQuiz(){
        // get file name of the next flag and remove it from the list
        correctAnswer = quizList.remove(0); // update the correct answer
        answerTextView.setText(""); // clear answerTextView

        System.out.println("Correct: " + correctAnswer);

        // get the index of correct answer in the data Lists
        int correctIndex;
        if(quizType.equals("Superheroes Name"))
            correctIndex = nameList.indexOf(correctAnswer);
        else if(quizType.equals("Superpower"))
            correctIndex = superpowerList.indexOf(correctAnswer);
        else
            correctIndex = oneThingList.indexOf(correctAnswer);

        // display current question number
        questionNumberTextView.setText(getString(
                R.string.question, (numberCorrect + 1), SUPERHEROES_IN_QUIZ));

        // use AssetManager to load next image from assets folder
        AssetManager assets = getActivity().getAssets(); // getActivity() -> get Fragment
        String imageName = imageNameList.get(correctIndex);

        // get an InputStream to the asset representing the next flag
        // require API level 19+ on build.gradle
        try
        {
            InputStream stream = assets.open(imageName); // have .png
            // load the asset as a Drawable and display on the flagImageView
            Drawable flag = Drawable.createFromStream(stream, imageName); // don't have .png extend
            heroImageview.setImageDrawable(flag);
            System.out.println("Image name: " + imageNameList.get(correctIndex));

            // Will not run when the app just created
            //animate(false); // animate the flag onto the screen
        }
        catch (IOException exception){
            Log.e(TAG, "Error loading" + correctAnswer, exception);
        }

        Collections.shuffle(quizList); // shuffle index List


        // add correct answer to the end
        //System.out.println("File name was remove: " + fileNameList.remove(correct));
        //randomList.add(randomList.remove(correctIndex));

        // add 2, 4, 6, or 8 guess Buttons based on the value of guessRows
        for(int row = 0; row < 2; row++){
            // place Buttons in currentTableRow
            for(int column = 0; column < guessLinearLayouts[row].getChildCount(); column++){
                // get reference to Button to configure
                Button newGuessButton = (Button) guessLinearLayouts[row].getChildAt(column);
                newGuessButton.setEnabled(true);

                // get name and set it as newGuessbutton's text
                String name;
                int randomIndex = (row * 2) + column;
                if(randomIndex == correctIndex)
                    randomIndex +=1;

                if(quizType.equals("Superheroes Name"))
                    name = nameList.get(randomIndex);
                else if(quizType.equals("Superpower"))
                    name = superpowerList.get(randomIndex);
                else
                    name = oneThingList.get(randomIndex);

                newGuessButton.setText(name);
                System.out.println("Button name: " + name);
            }
        }

        // randomly replace one Button with the correct answer
        int row = random.nextInt(2); // pick random row < 2
        int column = random.nextInt(2); // pick random column < 2

        System.out.println("Random row: " + row);
        System.out.println("Random column: " + column);

        LinearLayout randomRow = guessLinearLayouts[row]; // get the row
        ((Button) randomRow.getChildAt(column)).setText(correctAnswer);
    }

    /**
     * Called when a guess button is clicked. this listener is uesd for all buttons
     * in the flag quiz
     */
    private View.OnClickListener guessButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button guessButton = ((Button) v);
            String guess = guessButton.getText().toString();
            //String answer = correctAnswer;
            ++totalGuesses; // increment number of guesses the user has made

            if (guess.equals(correctAnswer)) { // if the guess is correct
                ++numberCorrect; // increment the number of correct answers

                // display correct answer in green text
                answerTextView.setText(correctAnswer + "!");
                // require API 23
                answerTextView.setTextColor(
                        getResources().getColor(R.color.correct_answer,
                                getContext().getTheme()));

                disableButtons(); // disable all guess Buttons

                // if the user has correctly identified FLAGS_IN_QUIZ flags
                if (numberCorrect == SUPERHEROES_IN_QUIZ) {
                    // DialogFragment to display quiz stats and start new quiz
                    /*DialogFragment quizResults =
                            new DialogFragment() {
                                // create an AlertDialog and return it
                                @Override
                                public Dialog onCreateDialog(Bundle bundle) {
                                    AlertDialog.Builder builder =
                                            new AlertDialog.Builder(getActivity());
                                    builder.setMessage(
                                            getString(R.string.results,
                                                    totalGuesses,
                                                    (1000 / (double) totalGuesses)));

                                    // "Reset Quiz" Button
                                    builder.setPositiveButton(R.string.reset_quiz,
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog,
                                                                    int id) {
                                                    guessSuperheroesQuiz();
                                                }
                                            }
                                    );

                                    return builder.create(); // return the AlertDialog
                                }
                            };*/

                    // reset quiz
                    numberCorrect = 0;
                    guessSuperheroesQuiz();

                    // use FragmentManager to display the DialogFragment
                    /*quizResults.setCancelable(false);
                    quizResults.show(getFragmentManager(), "quiz results");*/
                }
                else { // answer is correct but quiz is not over
                    // load the next flag after a 2-second delay
                    handler.postDelayed(
                            new Runnable() {
                                @Override
                                public void run() {
                                    loadNextQuiz();; // animate the flag off the screen
                                }
                            }, 2000); // 2000 milliseconds for 2-second delay
                }
            }
            else { // answer was incorrect
                shakeAnim(); // play shake

                // display "Incorrect!" in red
                answerTextView.setText(R.string.incorrect_answer);
                answerTextView.setTextColor(getResources().getColor(
                        R.color.incorrect_answer, getContext().getTheme()));
                guessButton.setEnabled(false); // disable incorrect answer
            }
        }
    };

    /**
     *  Function that disables all answer Buttons
     */
    private void disableButtons() {
        for (int row = 0; row < 2; row++) {
            LinearLayout guessRow = guessLinearLayouts[row];
            for (int i = 0; i < guessRow.getChildCount(); i++)
                guessRow.getChildAt(i).setEnabled(false);
        }
    }

    public void shakeAnim(){
        // getContext() -> return Context
        shakeAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.shake_anim);
        shakeAnimation.setRepeatCount(5);
        heroImageview.startAnimation(shakeAnimation);
    }

}
