package per_joelsompio.thirty;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by per-joelsompio on 28/03/17.
 */

public class ScoreList implements Parcelable{
    private int value;

    public ScoreList(int i) {
        this.value = i;
    }

    public void setValue(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    protected ScoreList(Parcel in) {
        value = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(value);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ScoreList> CREATOR = new Parcelable.Creator<ScoreList>() {
        @Override
        public ScoreList createFromParcel(Parcel in) {
            return new ScoreList(in);
        }

        @Override
        public ScoreList[] newArray(int size) {
            return new ScoreList[size];
        }
    };
}
