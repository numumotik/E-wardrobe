package com.discteam.e_wardrobe;

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

public class WardrobeFragment extends Fragment
    implements NumberRequestTask.CallBacks {

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

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wardrobe, container, false);
        setHasOptionsMenu(true);
        mNumberRequest = (Button)view.findViewById(R.id.number_button);
        mNumberView = (TextView)view.findViewById(R.id.number_text_view);
        mCurrNumber = NumberPreferences.getNumber(getContext());
        mCanGetNumber = mCurrNumber == null;
        updateNumber();

        mNumberRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNumberRequest.setEnabled(false);
                doNumberRequest(mCanGetNumber);
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_wardrobe_menu, menu);
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

    @Override
    public void onNumberRequestCompleted(Integer number) {
        mCurrNumber = number;
        NumberPreferences.setNumber(getContext(), mCurrNumber);
        mCanGetNumber = !mCanGetNumber;
        updateNumber();
        mNumberRequest.setEnabled(true);
    }

    public void doNumberRequest(boolean canGetNumber){
        String opType = canGetNumber ? NumberRequestTask.GET_NUMBER : NumberRequestTask.PASS_NUMBER;
        String login = NumberPreferences.getLogin(getActivity());
        String password = NumberPreferences.getPassword(getActivity());
        new NumberRequestTask(this, mCurrNumber).execute(opType, login, password);
    }

    public void updateNumber(){
        String requestInvitation = mCanGetNumber ? getActivity().getResources().getString(R.string.get_text)
                : getActivity().getResources().getString(R.string.return_text);
        int visibility = mCanGetNumber ? View.INVISIBLE : View.VISIBLE;
        mNumberView.setVisibility(visibility);
        if (!mCanGetNumber) {
            mNumberView.setText(String.valueOf(mCurrNumber));
        }
        mNumberRequest.setText(requestInvitation);
    }
}

