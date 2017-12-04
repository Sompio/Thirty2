package per_joelsompio.thirty;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * startscreen. supposed to work as a "Loadingscreen"
 * should show tips and rules the user can read during loading.
 * a timer is set. when timer is reached it starts the next activity
 * and destroys this activity.
 */
public class StartActivity extends AppCompatActivity {
    private TextView intro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        intro = (TextView) findViewById(R.id.Intro);
        intro.setText(getResources().getString(R.string.Intro));
        intro.setTextColor(Color.WHITE);

        TimerTask loadingScreen = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(StartActivity.this, GameActivity.class);
                startActivity(intent);
                finishLoadingScreen();

            }
        };
        Timer t = new Timer();
        t.schedule(loadingScreen, 5000);

    }

    private void finishLoadingScreen() {
        this.finish();
    }

    View.OnClickListener buttonStartHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(StartActivity.this, GameActivity.class));
        }
    };
}

