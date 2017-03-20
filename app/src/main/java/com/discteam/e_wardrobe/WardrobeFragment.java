package com.discteam.e_wardrobe;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class WardrobeFragment extends Fragment {
    private static final String GET_NUMBER = "getNumber";
    private static final String PASS_NUMBER = "passNumber";

    private Integer mCurrNumber;
    private Button mNumberRequest;
    private TextView mNumberView;
    private boolean mCanGetNumber;

    public static WardrobeFragment newInstance(){
        return new WardrobeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wardrobe, container, false);
        mNumberRequest = (Button)view.findViewById(R.id.numb_button);
        mNumberView = (TextView)view.findViewById(R.id.number_text_view);
        mCurrNumber = NumberPreferences.getNumber(getContext());
        mCanGetNumber = mCurrNumber == 0;
        updateNumber();

        mNumberRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doNumberRequest(mCanGetNumber);
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_ecloset_menu, menu);
        MenuItem notificationItem = menu.findItem(R.id.menu_item_notification);
        boolean isAlarmOn = NumberPreferences.isAlarmOn(getContext());
        if (isAlarmOn) {
            notificationItem.setTitle(R.string.disable_notification);
            notificationItem.setIcon(R.drawable.ic_menu_disable_notification);
        } else {
            notificationItem.setTitle(R.string.enable_notification);
            notificationItem.setIcon(R.drawable.ic_menu_enable_notification);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_notification:
                boolean isAlarmOn = NumberPreferences.isAlarmOn(getContext());
                NotificationService.setServiceAlarm(getContext(), !isAlarmOn);
                getActivity().invalidateOptionsMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class NumberRequestTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String opType = params[0];
            switch(opType) {
                case GET_NUMBER:
                    return new NumberWorker(null).getNumber();

                case PASS_NUMBER:
                    new NumberWorker(mCurrNumber).passNumber();
                    return null;

                default:
                    return null;
            }
        }

        @Override
        protected void onPostExecute(String number) {
            mCurrNumber = number == null ? 0 : Integer.parseInt(number);
            NumberPreferences.setNumber(getContext(), mCurrNumber);
            mCanGetNumber = !mCanGetNumber;
            updateNumber();
        }

    }

    public void doNumberRequest(boolean canGetNumber){
        String opType = canGetNumber ? GET_NUMBER : PASS_NUMBER;
        new NumberRequestTask().execute(opType);
    }

    public void updateNumber(){
        mNumberView.setText(String.valueOf(mCurrNumber));
        String requestInvitation = mCanGetNumber ? getActivity().getResources().getString(R.string.get_text)
                : getActivity().getResources().getString(R.string.return_text);
        mNumberRequest.setText(requestInvitation);
    }
}

