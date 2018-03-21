package com.techease.openspot.ui.activities;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.anthonyfdev.dropdownview.DropDownView;
import com.techease.openspot.R;
import com.techease.openspot.fragments.ChooseSignUpMethodForBooking;
import com.techease.openspot.fragments.ListOfAllBooking;
import com.techease.openspot.fragments.ProfileFragment;

public class BottomNavigationActivity extends AppCompatActivity {


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
                    Fragment fragment3=new ProfileFragment();
                    getFragmentManager().beginTransaction().replace(R.id.containerMain,fragment3).commit();
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

        Fragment fragment=new ListOfAllBooking();
        getFragmentManager().beginTransaction().replace(R.id.containerMain,fragment).commit();




    }

}
