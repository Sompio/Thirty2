package per_joelsompio.thirty;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;


/**
 * This is the gamescreen. here the user can roll the dice is able to chose wether to
 * save a dice or not.
 *
 * NOTE:
 * the rounds are not implemented yet. So the game runs only one round.
 */
public class GameActivity extends AppCompatActivity {
    ArrayList<Die> diceList = new ArrayList<Die>();
    ArrayList<ValueLocks> valueLocksList = new ArrayList<ValueLocks>();
    ArrayList<ScoreList> scoreList = new ArrayList<>();

    // storage variables for images.
    private int[] imageIDs;
    private int[] imageWhenClickedIDs;

    // Constants for bundles and extras
    private static final String DIE_COLLECTION = "Collection of dice";
    private static final String DIE_COLLECTION_ON_ROTATION = "Dice Savedstate";
    private static final String STATE_PLAYERROLLS = "PlayerRolls";
    private static final String STATE_PLAYERROUNDS = "PlayerRounds";
    private static final String STATE_GAMERULES = "GameStats";
    private static final String STATE_PLAYERSCORE = "PlayerScore";
    private static final String STATE_VALUELOCKS = "ValueLocks";
    private static final String STATE_SCORELIST = "ScoreList";

    private Button buttonRoll;
    private Button restartButton;
    GameStats gameStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        imageIDs = setImageResources();
        imageWhenClickedIDs = setImageResourcesWhenClicked();
        gameStats = new GameStats();

        Bundle baba = getIntent().getExtras();
        if(baba != null) {
            gameStats.score = baba.getInt(STATE_PLAYERSCORE);
            gameStats.round = baba.getInt(STATE_PLAYERROUNDS);
            valueLocksList = baba.getParcelableArrayList(STATE_VALUELOCKS);
            scoreList = baba.getParcelableArrayList(STATE_SCORELIST);

            diceList.add(new Die((ImageButton)findViewById(R.id.dice1)));
            diceList.add(new Die((ImageButton)findViewById(R.id.dice2)));
            diceList.add(new Die((ImageButton)findViewById(R.id.dice3)));
            diceList.add(new Die((ImageButton)findViewById(R.id.dice4)));
            diceList.add(new Die((ImageButton)findViewById(R.id.dice5)));
            diceList.add(new Die((ImageButton)findViewById(R.id.dice6)));
            setImgButtonListeners();
            getDiceImage();

            buttonRoll = (Button)findViewById(R.id.buttonRoll);
            buttonRoll.setOnClickListener(rollButtonHandler);
            buttonRoll.setText("Roll("+ gameStats.rolls+")");

            restartButton = (Button)findViewById(R.id.restartButton);
            restartButton.setOnClickListener(restartButtonListener);
        } else {
            diceList.add(new Die((ImageButton)findViewById(R.id.dice1)));
            diceList.add(new Die((ImageButton)findViewById(R.id.dice2)));
            diceList.add(new Die((ImageButton)findViewById(R.id.dice3)));
            diceList.add(new Die((ImageButton)findViewById(R.id.dice4)));
            diceList.add(new Die((ImageButton)findViewById(R.id.dice5)));
            diceList.add(new Die((ImageButton)findViewById(R.id.dice6)));
            setImgButtonListeners();
            getDiceImage();

            buttonRoll = (Button)findViewById(R.id.buttonRoll);
            buttonRoll.setOnClickListener(rollButtonHandler);
            buttonRoll.setText("Roll("+ gameStats.rolls+")");

            restartButton = (Button)findViewById(R.id.restartButton);
            restartButton.setOnClickListener(restartButtonListener);
        }

        for(int i = 0; i < diceList.size(); i++) {
            diceList.get(i).imgButton.setClickable(false);
        }
    }

    // Listener for the rollbutton. rolls dice depending on users choice
    /**
     * listener for the rollbutton.
     */
    View.OnClickListener rollButtonHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for(int i = 0; i < diceList.size(); i++) {
                diceList.get(i).imgButton.setClickable(true);
            }
            int i = 0;
            gameStats.rolls--;
            if(gameStats.rolls >=0) {
                buttonRoll.setText("Roll("+ gameStats.rolls+")");
                for (Die die : diceList) {
                    if (!die.getClicked()) {
                        diceList.get(i).rollDice();
                        updateDiceImage(diceList.get(i));
                    }
                    i++;
                }
                if(gameStats.rolls == 0) {
                    // sets the dicebuttons clickable again before sending them to the next activity
                    for(int j = 0; j < diceList.size(); j++) {
                        diceList.get(j).resetDieClicked();
                    }
                    Intent intent = new Intent(GameActivity.this, ScoreActivity.class);
                    intent.putExtra(DIE_COLLECTION, diceList);
                    intent.putExtra(STATE_GAMERULES, gameStats);
                    intent.putExtra(STATE_PLAYERROUNDS, gameStats.round);
                    intent.putExtra(STATE_PLAYERSCORE, gameStats.score);
                    intent.putExtra(STATE_VALUELOCKS, valueLocksList);
                    intent.putExtra(STATE_SCORELIST, scoreList);
                    startActivity(intent);
                }
            }
        }
    };

    View.OnClickListener restartButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //navigateUpTo(new Intent(GameActivity.this, GameActivity.class));

            Intent i = new Intent(GameActivity.this, GameActivity.class); //change it to your main class
            //the following 2 tags are for clearing the backStack and start fresh
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            finish();
            startActivity(i);
        }
    };

    /**
     * Setting up listeners for every die.
     */
    private void setImgButtonListeners() {
        for(int i = 0; i < diceList.size(); i++) {
            final int index = i;
            final Die activeButton = diceList.get(index);

            activeButton.imgButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    diceList.get(index).setClicked();
                    if(!diceList.get(index).clicked) {
                        diceList.get(index).imgButton.setImageResource(imageIDs[diceList.get(index).getImageValue()-1]);
                    } else{
                        diceList.get(index).imgButton.setImageResource(imageWhenClickedIDs[diceList.get(index).getImageValue()-1]);
                    }
                }
            });
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(DIE_COLLECTION_ON_ROTATION, diceList);
        outState.putInt(STATE_PLAYERROLLS, gameStats.rolls);
        outState.putInt(STATE_PLAYERROUNDS, gameStats.round);
        outState.putInt(STATE_PLAYERSCORE, gameStats.getScore());
        outState.putParcelableArrayList(STATE_SCORELIST, scoreList);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        diceList = savedInstanceState.getParcelableArrayList(DIE_COLLECTION_ON_ROTATION);
        scoreList = savedInstanceState.getParcelableArrayList(STATE_SCORELIST);
        gameStats.rolls = savedInstanceState.getInt(STATE_PLAYERROLLS);
        gameStats.round = savedInstanceState.getInt(STATE_PLAYERROUNDS);

        diceList.get(0).setDieButton((ImageButton)findViewById(R.id.dice1));
        diceList.get(1).setDieButton((ImageButton)findViewById(R.id.dice2));
        diceList.get(2).setDieButton((ImageButton)findViewById(R.id.dice3));
        diceList.get(3).setDieButton((ImageButton)findViewById(R.id.dice4));
        diceList.get(4).setDieButton((ImageButton)findViewById(R.id.dice5));
        diceList.get(5).setDieButton((ImageButton)findViewById(R.id.dice6));
        setImgButtonListeners();
        getDiceImage();

        if(gameStats.rolls >= 3) {
            for(int i = 0; i < diceList.size(); i++) {
                diceList.get(i).imgButton.setClickable(false);
            }
        }

        buttonRoll = (Button)findViewById(R.id.buttonRoll);
        buttonRoll.setOnClickListener(rollButtonHandler);
        buttonRoll.setText("Roll("+ gameStats.rolls+")");

        restartButton = (Button)findViewById(R.id.restartButton);
        restartButton.setOnClickListener(restartButtonListener);



    }

    /**
     * updates the images for the dice.
     */
    private void getDiceImage() {
        for(Die die:diceList) {
            if(!die.clicked) {
                die.imgButton.setImageResource(imageIDs[die.getImageValue()-1]);
            }
            else {
                die.imgButton.setImageResource(imageWhenClickedIDs[die.getImageValue()-1]);
            }
        }
    }

    // Keep in note that the resources indexes are -1 than the rollvalue
    private void updateDiceImage(Die die) {
        int k = die.getImageValue() -1;
        die.imgButton.setImageResource(imageIDs[k]);
    }

    // obtaining resources from XML-files
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

    // Obtaining resources from XML-files
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
}
