package per_joelsompio.thirty;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by per-joelsompio on 07/03/17.
 */

public class ValueLocks implements Parcelable {
    private int value;

    public ValueLocks(int i) {
        this.value = i;
    }

    public void setValue(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    protected ValueLocks(Parcel in) {
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
    public static final Parcelable.Creator<ValueLocks> CREATOR = new Parcelable.Creator<ValueLocks>() {
        @Override
        public ValueLocks createFromParcel(Parcel in) {
            return new ValueLocks(in);
        }

        @Override
        public ValueLocks[] newArray(int size) {
            return new ValueLocks[size];
        }
    };
}