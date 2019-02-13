package ca.ulaval.ima.tp2;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class InternetStatusFragment extends Fragment {

    public static final int NETWORK_STATUS_NOT_CONNECTED=0,NETWORK_STAUS_WIFI=1,NETWORK_STATUS_MOBILE=2;
    private Context _context;

    public InternetStatusFragment() {
        // Required empty public constructor
    }

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return NETWORK_STAUS_WIFI;
            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return NETWORK_STATUS_MOBILE;
        }
        return NETWORK_STATUS_NOT_CONNECTED;
    }

    public void setInternetStatus(View view) {
        TextView text = view.findViewById(R.id.internet_status_text);
        ImageView indicator = view.findViewById(R.id.internet_status_indicator);
        switch (getConnectivityStatus(_context)) {
            case NETWORK_STAUS_WIFI:
                text.setText(getString(R.string.internet_status_wifi));
                indicator.setColorFilter(getResources().getColor(R.color.colorPrimaryGreen));
                break;
            case NETWORK_STATUS_MOBILE:
                text.setText(getString(R.string.internet_status_3g_lte));
                indicator.setColorFilter(getResources().getColor(R.color.colorPrimaryGreen));
                break;
            case NETWORK_STATUS_NOT_CONNECTED:
                text.setText(getString(R.string.internet_status_no_connexion));
                indicator.setColorFilter(getResources().getColor(R.color.colorPrimaryRed));
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _context = container.getContext();
        final View view = inflater.inflate(R.layout.fragment_internet_status, container, false);
        Button button = view.findViewById(R.id.internet_status_button);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) { setInternetStatus(view); }
        });
        return view;
    }
}
