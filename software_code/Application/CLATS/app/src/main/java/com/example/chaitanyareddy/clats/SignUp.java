package com.example.chaitanyareddy.clats;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
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

public class SignUp extends AppCompatActivity {
    Button B;
    EditText Name, RollNo, PhoneNo, Pass1, CycleNo, CycleDes, Pass2;
    String Pass1Str, CycleNoStr, CycleDesStr, NameStr, RollNoStr, PhoneStr, Pass2Str;
    AlertDialog.Builder builder;
    String reg_url ="http://172.16.184.150/register.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        B = (Button) findViewById(R.id.signup);

        Name = (EditText) findViewById(R.id.FName);
        RollNo = (EditText) findViewById(R.id.FRollNo);
        PhoneNo = (EditText) findViewById(R.id.FPhoneNo);
        Pass1 = (EditText) findViewById(R.id.FPass1);
        CycleNo = (EditText) findViewById(R.id.FCycleNo);
        CycleDes = (EditText) findViewById(R.id.FCycleDeteils);
        Pass2 = (EditText)findViewById(R.id.FPass2);

        builder = new AlertDialog.Builder(SignUp.this);

        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NameStr = Name.getText().toString();
                RollNoStr = RollNo.getText().toString();
                PhoneStr = PhoneNo.getText().toString();
                Pass1Str = Pass1.getText().toString();
                CycleNoStr = CycleNo.getText().toString();
                CycleDesStr = CycleDes.getText().toString();
                Pass2Str = Pass2.getText().toString();

                if (!Pass1Str.equals(Pass2Str)) {
                    Toast.makeText(SignUp.this, "Password does not match", Toast.LENGTH_SHORT).show();
                }
                else {

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, reg_url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONArray jsonArray = new JSONArray(response);
                                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                                        String code = jsonObject.getString("code");
                                        String message = jsonObject.getString("message");
                                        if (code.equals("reg_failed")) {
                                            Toast.makeText(SignUp.this, "qewrwtwf", Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (JSONException e) {
                                        Toast.makeText(SignUp.this, "trump u", Toast.LENGTH_SHORT).show();
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                Toast.makeText(SignUp.this, "Check Network Connection",
                                        Toast.LENGTH_LONG).show();
                            } else if (error instanceof AuthFailureError) {
                                Toast.makeText(SignUp.this, "Authentication Failure",
                                        Toast.LENGTH_LONG).show();
                                //TODO
                            } else if (error instanceof ServerError) {
                                Toast.makeText(SignUp.this, "Server Error",
                                        Toast.LENGTH_LONG).show();

                            }
                            //TODO


                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String, String> params = new HashMap<String, String>();

                            params.put("Name", NameStr);
                            params.put("Roll_Number", RollNoStr);
                            params.put("Phone_Number", PhoneStr);
                            params.put("Cycle_Number", CycleNoStr);
                            params.put("Cycle_Description", CycleDesStr);
                            params.put("Password", Pass1Str);

                            return params;
                        }
                    };
                    MySingleton.getInstance(SignUp.this).addToRequestque(stringRequest);

                    startActivity(new Intent(SignUp.this, StudentSignup.class));
                }
            }
        });
    }

    public void displayAlert(String message){
        builder.setMessage(message);
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
