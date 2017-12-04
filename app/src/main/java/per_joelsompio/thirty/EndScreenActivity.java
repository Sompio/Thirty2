package per_joelsompio.thirty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class EndScreenActivity extends AppCompatActivity {

    int score;
    public final String STATE_PLAYERSCORE = "PlayerScore";
    public final String STATE_SCORELIST = "ScoreList";
    ArrayList<ScoreList> scoreList = new ArrayList<ScoreList>();
    TextView scoreView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_screen);
        Bundle scores = getIntent().getExtras();
        scoreList = scores.getParcelableArrayList(STATE_SCORELIST);
        score = scores.getInt(STATE_PLAYERSCORE);

        scoreView = (TextView)findViewById(R.id.scoreView);

        //System.out.println("This is endScreen");
        //System.out.println(scoreList.size());
        for(int i = 0; i < scoreList.size(); i++) {
            scoreView.append("Score in round: (" + (i+1) +") =" +Integer.toString(scoreList.get(i).getValue())+ "\n");
        }

        scoreView.append("\n Total score is: " + score);
        //scoreView = (TextView)findViewById(R.id.scoreView);
        //scoreView.setText("Your score is:" + score);
    }
}
