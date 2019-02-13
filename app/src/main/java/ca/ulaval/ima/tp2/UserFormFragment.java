package ca.ulaval.ima.tp2;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.CompoundButtonCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UserFormFragment extends Fragment {

    private Context _context;
    private String datePattern = "yyyy-MM-dd";

    public UserFormFragment() {
        // Required empty public constructor
    }

    public User getUserByForm(View view) {
        EditText firstname = view.findViewById(R.id.firstname_input);
        EditText lastname = view.findViewById(R.id.lastname_input);
        EditText birthdate = view.findViewById(R.id.birthdate_input);
        RadioGroup sex_grp = view.findViewById(R.id.sex_radiogroup);
        RadioButton sex = view.findViewById(sex_grp.getCheckedRadioButtonId());
        Spinner program = view.findViewById(R.id.program_choice_spinner);

        return new User(firstname.getText().toString(), lastname.getText().toString(),
                birthdate.getText().toString(), sex.getText().toString(),
                program.getSelectedItem().toString());
    }

    public boolean isDateValid(String date, String format) {
        try {
            @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat(format);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public void showAlert(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(_context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public boolean isFormValid(View view) {
        User user = this.getUserByForm(view);

        String errorMsg = "";

        if (user.getFirstname().equals("")) {
            errorMsg = "Le champs Prénom de doit pas être vide !";
        }
        if (errorMsg.equals("") && user.getLastname().equals("")) {
            errorMsg = "Le champs Nom de doit pas être vide !";
        }
        if (errorMsg.equals("") && !this.isDateValid(user.getBirthdate(), datePattern)) {
            errorMsg = "Le format de la date " + datePattern + " n'est pas valide !";
        }

        if (!errorMsg.equals("")) {
            this.showAlert("Erreur", errorMsg);
            return false;
        }
        return true;
    }

    public void submitUserForm(View view) {
        if (this.isFormValid(view)) {
            User user = this.getUserByForm(view);

            Bundle bundle = new Bundle();
            bundle.putParcelable("parcel_user", user);

            Fragment fragment = new UserProfileFragment();
            fragment.setArguments(bundle);

            if (getActivity() != null) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.main_fragment_container, fragment).addToBackStack( "tag" ).commit();
            }
        }
    }

    public void setSpinnerValues(View view) {
        Spinner spinner = view.findViewById(R.id.program_choice_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(_context,
                R.array.program_choice_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        int defaultChoice = adapter.getPosition(getString(R.string.program_developer));
        spinner.setAdapter(adapter);
        spinner.setSelection(defaultChoice);
    }

    public void setDateListener(View view) {
        final EditText text = view.findViewById(R.id.birthdate_input);
        final DatePickerDialog datePickerDialog = new DatePickerDialog(_context, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                DateFormat df = new SimpleDateFormat(datePattern, Locale.CANADA_FRENCH);
                text.setText(df.format(newDate.getTime()));
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
    }

    public void setSubmitListener(final View view) {
        Button button = view.findViewById(R.id.submit_button);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) { submitUserForm(view); }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _context = container.getContext();
        View view = inflater.inflate(R.layout.fragment_user_form, container, false);
        this.setSpinnerValues(view);
        this.setDateListener(view);
        this.setSubmitListener(view);
        return view;
    }
}
