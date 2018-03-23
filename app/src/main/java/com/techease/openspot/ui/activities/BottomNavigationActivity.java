package com.techease.openspot.ui.activities;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.techease.openspot.R;
import com.techease.openspot.fragments.ChooseLoginMethodForProfile;
import com.techease.openspot.fragments.ChooseSignUpMethodForBooking;
import com.techease.openspot.fragments.ListOfAllBooking;
import com.techease.openspot.fragments.ProfileFragment;
import com.techease.openspot.fragments.UserBookingFragment;

public class BottomNavigationActivity extends AppCompatActivity {

    String strFrom;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.search:
                    Fragment fragment=new ListOfAllBooking();
                    getFragmentManager().beginTransaction().replace(R.id.containerMain,fragment).commit();
                    return true;
                case R.id.booking:
                    Fragment fragment2=new ChooseSignUpMethodForBooking();
                    getFragmentManager().beginTransaction().replace(R.id.containerMain,fragment2).commit();
                    return true;
                case R.id.profile:
                    if (strFrom!=null)
                    {
                        Fragment fragment3=new ProfileFragment();
                        getFragmentManager().beginTransaction().replace(R.id.containerMain,fragment3).commit();
                    }
                    else
                    {
                        Fragment fragment1=new ChooseLoginMethodForProfile();
                        getFragmentManager().beginTransaction().replace(R.id.containerMain,fragment1).commit();
                    }

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_bottom_navigation);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        strFrom=getIntent().getExtras().getString("aaa");
        if (strFrom!=null)
        {
            Fragment fragment=new UserBookingFragment();
            getFragmentManager().beginTransaction().replace(R.id.containerMain,fragment).addToBackStack("abc").commit();
        }
        else
        {
            Fragment fragment=new ListOfAllBooking();
            getFragmentManager().beginTransaction().replace(R.id.containerMain,fragment).commit();
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (android.support.v4.app.Fragment fragment : getSupportFragmentManager().getFragments()) {
            //System.out.println("@#@");
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

}
