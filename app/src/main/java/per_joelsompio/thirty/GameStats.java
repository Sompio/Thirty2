package per_joelsompio.thirty;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by per-joelsompio on 20/01/17.
 * this class holds all the variables that are essential to the game, such as score, rounds
 * and rolls. could have used it a little bit better as ive done with all the other objects
 * that are parcelable.
 */

public class GameStats implements Parcelable {
    private ArrayList<Die> diceList = new ArrayList<>();
    public int round = 10;
    public int rolls = 3;
    public int score = 0;

    public GameStats() {
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return this.score;
    }

    public int getRound() {
        return this.round;
    }

    public String getRules() {
        return "HELLO YOU ARE NOT A WINNER";
    }

    private GameStats(Parcel in) {
        if (in.readByte() == 0x01) {
            diceList = new ArrayList<Die>();
            in.readList(diceList, Die.class.getClassLoader());
        } else {
            diceList = null;
        }
        round = in.readInt();
        rolls = in.readInt();
        score = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (diceList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(diceList);
        }
        dest.writeInt(round);
        dest.writeInt(rolls);
        dest.writeInt(score);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<GameStats> CREATOR = new Parcelable.Creator<GameStats>() {
        @Override
        public GameStats createFromParcel(Parcel in) {
            return new GameStats(in);
        }

        @Override
        public GameStats[] newArray(int size) {
            return new GameStats[size];
        }
    };
}

