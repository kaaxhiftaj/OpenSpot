package com.techease.openspot.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.techease.openspot.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import static com.facebook.FacebookSdk.getApplicationContext;


public class BookingInformationFragment extends Fragment {

    Button btnEmail,btnFb;
    TextView tvConnect,tvPrice,tvType,tvTime,tvName;
    String type,time,timeId,price,groundName,strEmail,fullname;
    String provider;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    LoginButton loginButton;
    CallbackManager callbackManager;
    private static final String EMAIL = "email";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_booking_information, container, false);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        AppEventsLogger.activateApp(getActivity());
        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        type=sharedPreferences.getString("type","");
        groundName=sharedPreferences.getString("groundName","");
        time=getArguments().getString("time");
        timeId=getArguments().getString("timeId");
        price=getArguments().getString("price");
        tvName=(TextView)view.findViewById(R.id.tvGroundNameBookingInfo);
        tvPrice=(TextView)view.findViewById(R.id.tvPriceBookingInfo);
        tvTime=(TextView)view.findViewById(R.id.tvTimeBookingInfo);
        tvType=(TextView)view.findViewById(R.id.tvType);

        btnEmail=(Button)view.findViewById(R.id.btnEmailBookingInfo);
        btnFb=(Button)view.findViewById(R.id.btnFbBookingInfo);
        tvConnect=(TextView)view.findViewById(R.id.tvConnect);
        loginButton = (LoginButton)view.findViewById(R.id.login_button);

        callbackManager = CallbackManager.Factory.create();

        tvType.setText(type);
        tvPrice.setText(price);
        tvTime.setText(time);
        tvName.setText(groundName);

        btnFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();
                Fragment fragment=new BookNowFragment();
                editor.putString("time",tvTime.getText().toString()).commit();
                editor.putString("type",tvType.getText().toString()).commit();
                editor.putString("price",tvPrice.getText().toString()).commit();
                editor.putString("name",tvName.getText().toString()).commit();
                getFragmentManager().beginTransaction().replace(R.id.containerMain,fragment).commit();
                loginButton.performClick();
                callbackManager = CallbackManager.Factory.create();
                loginButton.setReadPermissions(Arrays.asList(EMAIL));
                LoginManager.getInstance().logInWithReadPermissions(getActivity(), Arrays.asList("email"));
                loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("zmaFb",String.valueOf(loginResult));
                        Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();

                        Fragment fragment=new BookNowFragment();
                        Bundle bundle=new Bundle();
                        bundle.putString("time",tvTime.getText().toString());
                        bundle.putString("type",tvType.getText().toString());
                        bundle.putString("price",tvPrice.getText().toString());
                        bundle.putString("name",tvName.getText().toString());
                        fragment.setArguments(bundle);
                        getFragmentManager().beginTransaction().replace(R.id.containerMain,fragment).commit();
//                        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
//
//                            @Override
//                            public void onCompleted(JSONObject object, GraphResponse response) {
//                                Log.d("LoginActivity", String.valueOf(response));
//                                // Get facebook data from login
//                                Bundle bundle=getFacebookData(object);
//                                provider = "facebook";
//                                //apicall();
//
//
//                            }
//                        });
//                        Bundle parameters = new Bundle();
//                        parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location");                         request.setParameters(parameters);
//                        request.setParameters(parameters);
//                        request.executeAsync();
                       // apicall();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(getActivity(), String.valueOf(exception), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new SignUpWithEmailFragment();
                getFragmentManager().beginTransaction().replace(R.id.containerMain,fragment).addToBackStack("abc").commit();
            }
        });

        return view;
    }

    private void apicall() {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            strEmail = object.getString("email");
            Toast.makeText(getActivity(), strEmail, Toast.LENGTH_SHORT).show();
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
