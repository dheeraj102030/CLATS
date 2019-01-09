package com.example.chaitanyareddy.clats;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.provider.Telephony.Carriers.PASSWORD;


public class StudentSignup extends AppCompatActivity {

    EditText RollNo, Password;
    String RollNoStr, PasswordStr;
    CheckBox chkRememberMe;
    TextView textView;

    private static final String SPF_NAME = "vidslogin";
    private static final String RollNumber = "rollno";
    private static final String PASSWORD = "password";

    Button Login_Buttton;
    String login_url ="http://172.16.184.150/login.php";

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_signup);

        RollNo = (EditText) findViewById(R.id.InputRoll);
        Password = (EditText) findViewById(R.id.PasswordInput);
        chkRememberMe = (CheckBox)findViewById(R.id.checkBox);
        if (chkRememberMe.isChecked()) {
            chkRememberMe.setChecked(true);
        }
        else {
            chkRememberMe.setChecked(true);
        }

        SharedPreferences loginPreferences = getSharedPreferences(SPF_NAME,
                Context.MODE_PRIVATE);
        RollNo.setText(loginPreferences.getString(RollNumber, ""));
        Password.setText(loginPreferences.getString(PASSWORD, ""));

        builder = new AlertDialog.Builder(StudentSignup.this);
        Login_Buttton = (Button) findViewById(R.id.LogInButton);
        Login_Buttton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RollNoStr = RollNo.getText().toString();
                PasswordStr = Password.getText().toString();

                if(RollNoStr.equals("") || PasswordStr.equals("")){

                    builder.setTitle("Login Error");
                    displayAlert("Enter a valid roll number and password");
                }
                else{
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, login_url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try{
                                        JSONArray jsonArray = new JSONArray(response);
                                        JSONObject jsonObject =jsonArray.getJSONObject(0);
                                        String code = jsonObject.getString("code");
                                        if(code.equals("login_failed")){
                                            builder.setTitle("Login Error");
                                            displayAlert(jsonObject.getString("message"));

                                        }
                                        else {

                                            Intent i = new Intent(StudentSignup.this, Mainpage2.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putString("Name", jsonObject.getString("Name"));
                                            bundle.putString("PhoneNumber", jsonObject.getString("Phone_Number"));
                                            bundle.putString("CycleDescription", jsonObject.getString("Cycle_Description"));
                                            bundle.putString("RollNo", RollNoStr);
                                            bundle.putString("riding_check", jsonObject.getString("riding_check"));
                                            bundle.putString("Cycle_Number", jsonObject.getString("Cycle_Number"));
                                            bundle.putString("Present_location", jsonObject.getString("Present_location"));
                                            bundle.putString("Past_location_1", jsonObject.getString("Past_location_1"));
                                            bundle.putString("Past_location_2", jsonObject.getString("Past_location_2"));
                                            bundle.putString("Past_location_3", jsonObject.getString("Past_location_3"));
                                            bundle.putString("Past_location_4", jsonObject.getString("Past_location_4"));

                                            bundle.putString("Time", jsonObject.getString("Time"));
                                            bundle.putString("Past_time_1", jsonObject.getString("Past_time_1"));
                                            bundle.putString("Past_time_2", jsonObject.getString("Past_time_2"));
                                            bundle.putString("Past_time_3", jsonObject.getString("Past_time_3"));
                                            bundle.putString("Past_time_4", jsonObject.getString("Past_time_4"));
                                            bundle.putString("Password", PasswordStr);
                                            i.putExtras(bundle);

                                            if (chkRememberMe.isChecked())
                                            {
                                                SharedPreferences loginPreferences = getSharedPreferences(SPF_NAME, Context.MODE_PRIVATE);
                                                loginPreferences.edit().putString(RollNumber, RollNoStr).putString(PASSWORD, PasswordStr).commit();
                                            } else
                                            {
                                                SharedPreferences loginPreferences = getSharedPreferences(SPF_NAME, Context.MODE_PRIVATE);
                                                loginPreferences.edit().clear().commit();
                                            }
                                            startActivity(i);
                                        }
                                    }catch (JSONException e){
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                Toast.makeText(StudentSignup.this,"Check Network Connection",
                                        Toast.LENGTH_LONG).show();
                            } else if (error instanceof AuthFailureError) {
                                Toast.makeText(StudentSignup.this,"Authentication Failure",
                                        Toast.LENGTH_LONG).show();
                                //TODO
                            } else if (error instanceof ServerError) {Toast.makeText(StudentSignup.this,"Server Error",
                                    Toast.LENGTH_LONG).show();

                            }
                                //TODO


                        }
                    })
                {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String,String> params = new HashMap<String, String>();

                            params.put("Roll_Number", RollNoStr);
                            params.put("Password", PasswordStr);

                            return params;
                        }
                    };
                    MySingleton.getInstance(StudentSignup.this).addToRequestque(stringRequest);
                }
            }
        });
    }

    public void  onClickCreateAccount(View view){

        Intent i = new Intent(this, SignUp.class);
        startActivity(i);

    }

    public void displayAlert(String message){
        builder.setMessage(message);
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RollNo.setText("");
                Password.setText("");
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
