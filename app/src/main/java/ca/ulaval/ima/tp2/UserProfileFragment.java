package ca.ulaval.ima.tp2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class UserProfileFragment extends Fragment {


    public UserProfileFragment() {
        // Required empty public constructor
    }

    public void setValues(View view, User user) {
        TextView firstname = view.findViewById(R.id.firstname_input);
        TextView lastname = view.findViewById(R.id.lastname_input);
        TextView birthdate = view.findViewById(R.id.birthdate_input);
        TextView sex = view.findViewById(R.id.sex_input);
        TextView program = view.findViewById(R.id.program_input);

        firstname.setText(user.getFirstname());
        lastname.setText(user.getLastname());
        birthdate.setText(user.getBirthdate());
        sex.setText(user.getSex());
        program.setText(user.getProgram());
    }

    public void setUserValues(View view) {
        User user = null;
        if (this.getArguments() != null) {
            user = this.getArguments().getParcelable("parcel_user");
        }
        if (user == null) {
            user = new User(getString(R.string.firstname_developer),
                    getString(R.string.lastname_developer), getString(R.string.birthdate_developer),
                    getString(R.string.sex_male_field), getString(R.string.program_developer));
        }
        this.setValues(view, user);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        this.setUserValues(view);
        return view;
    }
}
