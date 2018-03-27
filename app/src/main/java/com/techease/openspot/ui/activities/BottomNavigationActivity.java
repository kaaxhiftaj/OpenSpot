package com.techease.openspot.ui.activities;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.techease.openspot.R;
import com.techease.openspot.fragments.ChooseLoginMethodForProfile;
import com.techease.openspot.fragments.ChooseSignUpMethodForBooking;
import com.techease.openspot.fragments.ListOfAllBooking;
import com.techease.openspot.fragments.ProfileFragment;
import com.techease.openspot.fragments.UserBookingFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class BottomNavigationActivity extends AppCompatActivity {

    String strFrom,provider,strEmail,fullname;
    CallbackManager callbackManager;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String token;
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
                    if (token!=null)
                    {
                        Fragment fragment2=new UserBookingFragment();
                        getFragmentManager().beginTransaction().replace(R.id.containerMain,fragment2).addToBackStack("abc").commit();
                    }
                    else
                    {
                        Fragment fragment2=new ChooseSignUpMethodForBooking();
                        getFragmentManager().beginTransaction().replace(R.id.containerMain,fragment2).commit();
                    }

                    return true;
                case R.id.profile:
                    if (token!=null)
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

        sharedPreferences = this.getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        token=sharedPreferences.getString("token","");
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

    public  void facebook() {

        callbackManager = CallbackManager.Factory.create();
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logInWithReadPermissions(BottomNavigationActivity.this, Arrays.asList("email"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.d("LoginActivity", String.valueOf(response));

                                Bundle bundle=getFacebookData(object);
                                editor.putString("token","login").commit();


                            }
                        });
                       Bundle parameters = new Bundle();
                       parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location");                         request.setParameters(parameters);
                        request.setParameters(parameters);
                         request.executeAsync();
                        // App code
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private Bundle getFacebookData(JSONObject object) {

        try {
            Bundle bundle = new Bundle();
            String id = object.getString("id");
            try {
                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                Log.i("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());
                editor.putString("image",profile_pic.toString()).commit();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.d("zmaE",String.valueOf(e.getCause()));
                return null;
            }
            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));

            fullname = object.getString("first_name") + object.getString("last_name");
            editor.putString("name",fullname).commit();
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            strEmail = object.getString("email");

            editor.putString("email",strEmail).commit();
            provider = "facebook";
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));
            if (object.has("location"))
                bundle.putString("location", object.getJSONObject("location").getString("name"));

            return bundle;
        }
        catch(JSONException e) {
            Log.d("Error","Error parsing JSON");
        }
        return null;
    }
}
