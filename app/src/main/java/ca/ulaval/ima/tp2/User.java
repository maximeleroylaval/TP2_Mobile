package ca.ulaval.ima.tp2;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String firstname;
    private String lastname;
    private String birthdate;
    private String sex;
    private String program;

    User(String firstname, String lastname, String birthdate, String sex, String program) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.sex = sex;
        this.program = program;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getSex() {
        return sex;
    }

    public String getProgram() {
        return program;
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    protected User(Parcel in) {
        firstname = in.readString();
        lastname = in.readString();
        birthdate = in.readString();
        sex = in.readString();
        program = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstname);
        dest.writeString(lastname);
        dest.writeString(birthdate);
        dest.writeString(sex);
        dest.writeString(program);
    }
}