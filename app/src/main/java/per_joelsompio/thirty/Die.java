/*package per_joelsompio.thirty;

import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.widget.ImageButton;

import java.util.Random;*/

/**
 * Created by per-joelsompio on 20/01/17.
 */

/*public class Die {
    boolean clicked = false;
    int updateValue;
    private int rollValue;
    ImageButton imgButton;
    Random rand = new Random();

    public Die(ImageButton iButton) {
        imgButton = iButton;
        //getDiceImage();
    }

    public void setClicked() {
        if(!clicked) {
            clicked = true;
            //onClickedDice();
        }
        else {
            clicked = false;
            //onClickedDice();
        }
    }

    public Die getDie() {
        return this;
    }

    public Boolean getClicked() {
        return clicked;
    }

    //anropas i rolldice. imagevalue håller reda på vilket värde tärningen har
    public int getDiceImage() {
        int i = rand.nextInt(6)+1;
        if(i == 1) {
            this.rollValue = 1;
            System.out.println("Tärningsvärde" + rollValue);
            return R.drawable.white1;
        }

        else if(i == 2) {
            this.rollValue = 2;
            System.out.println("Tärningsvärde" + rollValue);
            return R.drawable.white2;
        }

        else if(i == 3) {
            this.rollValue = 3;
            System.out.println("Tärningsvärde" + rollValue);
            return R.drawable.white3;
        }

        else if(i == 4) {
            this.rollValue = 4;
            System.out.println("Tärningsvärde" + rollValue);
            return R.drawable.white4;
        }

        else if(i == 5) {
            this.rollValue = 5;
            System.out.println("Tärningsvärde" + rollValue);
            return R.drawable.white5;
        }

        else if(i == 6) {
            this.rollValue = 6;
            System.out.println("Tärningsvärde" + rollValue);
            return R.drawable.white6;
        }
        return i;
    }

    public void onClickedDice(int value) {
        rollValue = value;
        System.out.println("Imagevalue" + rollValue);
        switch (rollValue) {
            case 1:
                if(clicked) {
                    imgButton.setImageResource(R.drawable.grey1);
                }
                else {
                    imgButton.setImageResource(R.drawable.white1);

                }
                break;
            case 2:
                if(clicked) {
                    imgButton.setImageResource(R.drawable.grey2);
                }
                else {
                    imgButton.setImageResource(R.drawable.white2);

                }
                break;
            case 3:
                if(clicked) {
                    imgButton.setImageResource(R.drawable.grey3);
                }
                else {
                    imgButton.setImageResource(R.drawable.white3);

                }
                break;
            case 4:
                if(clicked) {
                    imgButton.setImageResource(R.drawable.grey4);
                }
                else {
                    imgButton.setImageResource(R.drawable.white4);

                }
                break;
            case 5:
                if(clicked) {
                    imgButton.setImageResource(R.drawable.grey5);
                }
                else {
                    imgButton.setImageResource(R.drawable.white5);

                }
                break;
            case 6:
                if(clicked) {
                    imgButton.setImageResource(R.drawable.grey6);
                }
                else {
                    imgButton.setImageResource(R.drawable.white6);

                }
                break;
        }
    }

    public int getImageValue() {
        return this.rollValue;
    }

    public void updateImages(int value) {
        updateValue = value;

        if(updateValue == 1) {
            imgButton.setImageResource(R.drawable.white1);
        }

        else if(updateValue == 2) {
            imgButton.setImageResource(R.drawable.white2);
        }

        else if(updateValue == 3) {
            imgButton.setImageResource(R.drawable.white3);
        }

        else if(updateValue == 4) {
            imgButton.setImageResource(R.drawable.white4);
        }

        else if(updateValue == 5) {
            imgButton.setImageResource(R.drawable.white5);
        }

        else if(updateValue == 6) {
            imgButton.setImageResource(R.drawable.white6);
        }

    }
}*/

package per_joelsompio.thirty;

import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.widget.ImageButton;
import android.os.Parcel;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by per-joelsompio on 20/01/17.
 */

public class Die implements Parcelable {
    boolean clicked = false;
    private boolean clickedScore = false;
    private int updateValue;
    private int rollValue;
    ImageButton imgButton;
    private Random rand = new Random();

    public Die(ImageButton iButton) {
        imgButton = iButton;
        rollValue = rand.nextInt(6)+1;
    }

    public void setDieButton(ImageButton iButton) {
        this.imgButton = iButton;
    }

    public void rollDice() {
        rollValue = rand.nextInt(6)+1;
    }

    public void setImageValue(int value) {
        this.rollValue = value;

    }

    public void resetDieClicked() {
        clicked = false;
    }

    public void setClickedScore() {
        if(!clickedScore) {
            clickedScore = true;
            //onClickedDice();
        }
        else {
            clickedScore = false;
            //onClickedDice();
        }
    }

    public void setClicked() {
        if(!clicked) {
            clicked = true;
            //onClickedDice();
        }
        else {
            clicked = false;
            //onClickedDice();
        }
    }

    public Boolean getClickedScore() {
        return clickedScore;
    }

    public Die getDie() {
        return this;
    }

    public Boolean getClicked() {
        return clicked;
    }


    public int getImageValue() {
        return this.rollValue;
    }


    protected Die(Parcel in) {
        this.clicked = in.readByte() != 0x00;
        this.clickedScore = in.readByte() != 0x00;
        this.updateValue = in.readInt();
        this.rollValue = in.readInt();
        this.rand = (Random) in.readValue(Random.class.getClassLoader());

        // No need to send the imagebuttons to the next activity
        //this.imgButton = (ImageButton) in.readValue(ImageButton.class.getClassLoader());

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (clicked ? 0x01 : 0x00));
        dest.writeByte((byte) (clickedScore ? 0x01 : 0x00));
        dest.writeInt(updateValue);
        dest.writeInt(rollValue);
        dest.writeValue(rand);

        // No need to send the imagebuttons to the next activity
        //dest.writeValue(imgButton);

    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Die> CREATOR = new Parcelable.Creator<Die>() {
        @Override
        public Die createFromParcel(Parcel in) {
            return new Die(in);
        }
        @Override
        public Die[] newArray(int size) {
            return new Die[size];
        }
    };
}
