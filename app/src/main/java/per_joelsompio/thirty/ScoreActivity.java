package per_joelsompio.thirty;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.lang.String;
import java.util.ArrayList;


/**
 * ScoreActivity. Here the user can chose the dice he/she wants and
 * make a combination out of them and add them to their scorevalue
 *
 * how it works:
 * The user has to press the diecombination he/she wants. And then click on a
 * scorebutton that matches the value of the combined value of the pressed dice.
 *
 * NOTE:
 * in the current implementation the user is able to choose different scorevalues
 * during the same round. it is supposed to work like this:
 * once the user choses the first scorevalue for example the value 4 and picks the dice
 * thereafter the user cant chose another scorevalue like 6.
 */
public class ScoreActivity extends AppCompatActivity {
    private static final String DIE_COLLECTION = "Collection of dice";
    private static final String STATE_GAMERULES = "GameStats";
    private static final String STATE_PLAYERROUNDS = "PlayerRounds";
    private static final String STATE_PLAYERSCORE = "PlayerScore";
    private static final String STATE_VALUELOCKS = "ValueLocks";
    private static final String STATE_SCORELIST = "ScoreList";


    GameStats gameStats;
    ArrayList<Die> diceList = new ArrayList<Die>();
    ArrayList<ValueLocks> valueLocksList = new ArrayList<ValueLocks>();
    ArrayList<ScoreList> scoreList = new ArrayList<>();

    // containers for resource images
    private int[] imageIDs;
    private int[] imageWhenClickedIDs;

    private int combinationValue;
    private int lockedCombinationValue;
    private int totalScore;
    private ArrayList<Button> buttons;
    private int usedScoreButton;
    private int checkUsedScoreButton;
    private TextView scoreView;

    private Button scoreThree;
    private Button scoreFour;
    private Button scoreFive;
    private Button scoreSix;
    private Button scoreSeven;
    private Button scoreEight;
    private Button scoreNine;
    private Button scoreTen;
    private Button scoreEleven;
    private Button scoreTwelve;
    private Button addScore;
    private Button restart;
    private Button nextRound;

    /**
     * Starts the scoreactivity. checks if there are any saved bundles. if not it creates
     * a new set.
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        buttons = new ArrayList<Button>();

        // acquires the vales saved into the bundle from the past activity
        Bundle collectionDice = getIntent().getExtras();
        imageIDs = setImageResources();
        imageWhenClickedIDs = setImageResourcesWhenClicked();

        if(collectionDice != null) {
            diceList = collectionDice.getParcelableArrayList(DIE_COLLECTION);
            valueLocksList = collectionDice.getParcelableArrayList(STATE_VALUELOCKS);
            scoreList = collectionDice.getParcelableArrayList(STATE_SCORELIST);

            gameStats = collectionDice.getParcelable(STATE_GAMERULES);
            gameStats.score = collectionDice.getInt(STATE_PLAYERSCORE);
            gameStats.round = collectionDice.getInt(STATE_PLAYERROUNDS);

            scoreView = (TextView)findViewById(R.id.textView4);
            scoreView.setText(Integer.toString(gameStats.score));
            setDiceButtons();
            setButtons();
        } else {
            checkUsedScoreButton = 0;
            setDiceButtons();
            setButtons();
            scoreView = (TextView)findViewById(R.id.textView4);
            scoreView.setText("Should show score");
        }



        //ACTION LISTENERS
        for(int i=0; i < buttons.size(); i++){
            final int index = i;
            final Button activeButton = buttons.get(index);
            if(buttons.get(i).isClickable())
                activeButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    checkUsedScoreButton = 1;
                    usedScoreButton = index;
                    valueLocksList.add(new ValueLocks(index));
                    lockedCombinationValue = index+3;
                    activeButton.setBackgroundColor(Color.GREEN);
                    for(int currentButton = 0; currentButton < buttons.size(); currentButton++) {
                        if(currentButton != index) {
                            buttons.get(currentButton).setClickable(false);
                        }

                    }
                }

            });
        }

    }

    /**
     * saves the current values in a bundle. The bundle is later aquired when for example
     * the user rotates the screen.
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(DIE_COLLECTION, diceList);
        outState.putParcelableArrayList(STATE_VALUELOCKS, valueLocksList);
        outState.putParcelableArrayList(STATE_SCORELIST, scoreList);
        outState.putInt("usedScoreButton", usedScoreButton);
        outState.putInt("checkUsedScoreButton", checkUsedScoreButton);
        outState.putInt("CombinationValue", combinationValue);
        outState.putString("ScoreView", scoreView.toString());
        outState.putInt(STATE_PLAYERSCORE, gameStats.getScore());
        outState.putInt(STATE_PLAYERROUNDS, gameStats.getRound());


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        diceList = savedInstanceState.getParcelableArrayList(DIE_COLLECTION);
        valueLocksList = savedInstanceState.getParcelableArrayList(STATE_VALUELOCKS);
        scoreList = savedInstanceState.getParcelableArrayList(STATE_SCORELIST);
        gameStats.score = savedInstanceState.getInt(STATE_PLAYERSCORE);
        gameStats.round = savedInstanceState.getInt(STATE_PLAYERROUNDS);
        combinationValue = savedInstanceState.getInt("CombinationValue");
        usedScoreButton = savedInstanceState.getInt("usedScoreButton");
        checkUsedScoreButton = savedInstanceState.getInt("checkUsedScoreButton");

        scoreView = (TextView)findViewById(R.id.textView4);
        scoreView.setText(Integer.toString(gameStats.score));
        setDiceButtons();
        setButtons();
        System.out.println("on rotation");
    }

    /**
     * updates the images for the dice
     * @param die
     */
    private void updateDiceImage(Die die) {
        if(!die.clicked) {
            int k = die.getImageValue() - 1;
            die.imgButton.setImageResource(imageIDs[k]);
        } else {
            int k = die.getImageValue() -1;
            die.imgButton.setImageResource(imageWhenClickedIDs[k]);
        }
    }
    /**
     * obtaining resources from XML-files
     * @return
     */
    private int[] setImageResources() {
        Resources res = getResources();
        TypedArray images = res.obtainTypedArray(R.array.images);
        int len = images.length();
        imageIDs = new int[len];
        for(int i = 0; i < len; i++) {
            imageIDs[i] = images.getResourceId(i, 0);
        }
        images.recycle();
        return imageIDs;
    }
    /**
     * Obtaining resources from XML-files. The resources are images.
     * @return
     */
    private int[] setImageResourcesWhenClicked() {
        Resources reso = getResources();
        TypedArray imageWhenClicked = reso.obtainTypedArray(R.array.imagesWhenClicked);
        int leng = imageWhenClicked.length();
        imageWhenClickedIDs = new int[leng];
        for(int i = 0; i < leng; i++) {
            imageWhenClickedIDs[i] = imageWhenClicked.getResourceId(i, 0);
        }
        imageWhenClicked.recycle();
        return imageWhenClickedIDs;
    }
    /**
     * Adding listeners to every diebutton.
     */
    private void setImgButtonListeners() {
        for(Die die: diceList) {
            die.imgButton.setOnClickListener(diceHandler);
        }
    }
    /**
     * Listener for all dicebuttons. When a die is clicked it locks the die and
     * changes the visual of it.
     */
    View.OnClickListener diceHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.scoreDie1:
                    diceList.get(0).setClicked();
                    onClickedDice(diceList.get(0));
                    break;
                case R.id.scoreDie2:
                    diceList.get(1).setClicked();
                    onClickedDice(diceList.get(1));
                    break;
                case R.id.scoreDie3:
                    diceList.get(2).setClicked();
                    onClickedDice(diceList.get(2));
                    break;
                case R.id.scoreDie4:
                    diceList.get(3).setClicked();
                    onClickedDice(diceList.get(3));
                    break;
                case R.id.scoreDie5:
                    diceList.get(4).setClicked();
                    onClickedDice(diceList.get(4));
                    break;
                case R.id.scoreDie6:
                    diceList.get(5).setClicked();
                    onClickedDice(diceList.get(5));
                    break;
            }
        }
    };

    /**
     * locks a die in scorescreen so that the die can't be used for adding more score
     * @param die
     * @return
     */
    private int onClickedDice(Die die) {
        if(die.clicked) {
            die.imgButton.setImageResource(imageWhenClickedIDs[die.getImageValue()-1]);
            combinationValue += die.getImageValue();
        }
        else {
            die.imgButton.setImageResource(imageIDs[die.getImageValue()-1]);
            combinationValue -= die.getImageValue();
        }
        return die.getImageValue();
    }

    /**
     * Addscore button listener. lockcombinationvalue is the chosen result of
     * the die combinations. if the combinationvalues of the dice are appropriate
     * the score is added.
     * also locks the dice that are used.
     */
    View.OnClickListener addScoreListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(lockedCombinationValue == combinationValue) {
                gameStats.score += combinationValue;
                totalScore += combinationValue;

                scoreView.setText(Integer.toString(gameStats.getScore()));
                combinationValue = 0;

                //Locks the dice that are being used
                for(int i = 0; i < diceList.size(); i++) {
                    if(diceList.get(i).clicked) {
                        diceList.get(i).imgButton.setClickable(false);
                    }
                }
            }
            else if(combinationValue <= 3) {
                gameStats.score += combinationValue;
                scoreView.setText(Integer.toString(gameStats.getScore()));
                totalScore += combinationValue;
                combinationValue = 0;

                for(int i = 0; i < diceList.size(); i++) {
                    if(diceList.get(i).clicked) {
                        diceList.get(i).imgButton.setClickable(false);
                    }
                }
            }
            nextRound.setClickable(true);
            nextRound.setBackgroundColor(Color.LTGRAY);
        }
    };

    View.OnClickListener nextRoundListener = new View.OnClickListener() {
      @Override
        public void onClick(View v) {
          gameStats.round -= 1;
          scoreList.add(new ScoreList(totalScore));
          for(int i = 0; i < scoreList.size(); i++) {
              System.out.println(scoreList.get(i));
          }
          totalScore = 0;
          if(gameStats.round == 0) {
              Intent intent = new Intent(ScoreActivity.this, EndScreenActivity.class);
              intent.putExtra(STATE_PLAYERSCORE, gameStats.getScore());
              intent.putExtra(STATE_SCORELIST, scoreList);
              startActivity(intent);
          } else {
              Intent intent = new Intent(ScoreActivity.this, GameActivity.class);
              intent.putExtra(STATE_PLAYERROUNDS, gameStats.getRound());
              intent.putExtra(STATE_PLAYERSCORE, gameStats.getScore());
              intent.putExtra(STATE_VALUELOCKS, valueLocksList);
              intent.putExtra(STATE_SCORELIST, scoreList);
              startActivity(intent);

          }

      }
    };

    View.OnClickListener restartListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(ScoreActivity.this, GameActivity.class); //change it to your main class
            //the following 2 tags are for clearing the backStack and start fresh
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            finish();
            startActivity(i);
        }
    };

    /**
     * Setting up all the scorebuttons
     */
    public void setButtons() {
        restart = (Button)findViewById(R.id.Restart);
        restart.setOnClickListener(restartListener);

        nextRound = (Button)findViewById(R.id.nextRound);
        nextRound.setOnClickListener(nextRoundListener);
        nextRound.setClickable(false);
        nextRound.setBackgroundColor(Color.RED);
        nextRound.setText("Next Round("+(gameStats.round-1)+")");

        addScore = (Button)findViewById(R.id.addScore);
        addScore.setOnClickListener(addScoreListener);
        addScore.setText("Add score");

        scoreThree = (Button)findViewById(R.id.button13);
        buttons.add(scoreThree);
        scoreThree.setText("Value of >=3");

        scoreFour = (Button)findViewById(R.id.button14);
        buttons.add(scoreFour);
        scoreFour.setText("Value of 4");

        scoreFive = (Button)findViewById(R.id.button15);
        buttons.add(scoreFive);
        scoreFive.setText("Value of 5");

        scoreSix = (Button)findViewById(R.id.button16);
        buttons.add(scoreSix);
        scoreSix.setText("Value of 6");

        scoreSeven = (Button)findViewById(R.id.button17);
        buttons.add(scoreSeven);
        scoreSeven.setText("Value of 7");

        scoreEight = (Button)findViewById(R.id.button18);
        buttons.add(scoreEight);
        scoreEight.setText("Value of 8");

        scoreNine = (Button)findViewById(R.id.button19);
        buttons.add(scoreNine);
        scoreNine.setText("Value of 9");

        scoreTen = (Button)findViewById(R.id.button20);
        buttons.add(scoreTen);
        scoreTen.setText("Value of 10");

        scoreEleven = (Button)findViewById(R.id.button21);
        buttons.add(scoreEleven);
        scoreEleven.setText("Value of 11");

        scoreTwelve = (Button)findViewById(R.id.button22);
        buttons.add(scoreTwelve);
        scoreTwelve.setText("Value of 12");

        for(int i = 0; i < buttons.size(); i++) {
            for(int j = 0; j < valueLocksList.size(); j++) {
                if(i == valueLocksList.get(j).getValue() && i != usedScoreButton) {
                    buttons.get(i).setClickable(false);
                    buttons.get(i).setBackgroundColor(Color.RED);
                }
            }
        }
        if(checkUsedScoreButton == 1) {
            buttons.get(usedScoreButton).performClick();
            buttons.get(usedScoreButton).setPressed(true);
        }
    }

    /**
     * A method to set the imagebuttons for the diebuttons.
     */
    private void setDiceButtons() {

        diceList.get(0).setDieButton((ImageButton)findViewById(R.id.scoreDie1));
        diceList.get(1).setDieButton((ImageButton)findViewById(R.id.scoreDie2));
        diceList.get(2).setDieButton((ImageButton)findViewById(R.id.scoreDie3));
        diceList.get(3).setDieButton((ImageButton)findViewById(R.id.scoreDie4));
        diceList.get(4).setDieButton((ImageButton)findViewById(R.id.scoreDie5));
        diceList.get(5).setDieButton((ImageButton)findViewById(R.id.scoreDie6));

        setImgButtonListeners();

        for(Die die:diceList) {
            updateDiceImage(die);
        }
    }
}
