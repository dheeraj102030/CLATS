package com.example.chaitanyareddy.clats;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebViewFragment;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
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

import static android.R.attr.fragment;

public class Mainpage2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Button B, CheckPass;
    CheckBox ridingCheck;
    EditText Pass;
    String PasswordOriginal, b, c, Password, ridingcheck, Name, RollNo, CycleDescription, PhoneNumber;
    TextView Pl, Pl1,Pl2, T, T1, T2, name, Pl3, Pl4, T3, T4;
    String url = "http://172.16.184.150/getdata.php";
    String url2 = "http://172.16.184.150/rideCheck.php";
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage2);
        B = (Button)findViewById(R.id.button) ;
        Pl = (TextView)findViewById(R.id.Present_Location);
        Pl1 = (TextView)findViewById(R.id.Past_location_1);
        Pl2 = (TextView)findViewById(R.id.Past_location_2);
        Pl3 = (TextView)findViewById(R.id.Past_location_3);
        Pl4 = (TextView)findViewById(R.id.Past_location_4);
        T = (TextView)findViewById(R.id.Time);
        T1 = (TextView)findViewById(R.id.Past_time_1);
        T2 = (TextView)findViewById(R.id.Past_time_2);
        T3 = (TextView)findViewById(R.id.Past_time_3);
        T4 = (TextView)findViewById(R.id.Past_time_4);

       // name = (TextView)findViewById(R.id.username);

        CheckPass = (Button)findViewById(R.id.PassCheck);
        CheckPass.setVisibility(View.INVISIBLE);
        ridingCheck = (CheckBox)findViewById(R.id.checkBox2);
        Pass = (EditText)findViewById(R.id.PasswordInput2);
        Pass.setVisibility(View.INVISIBLE);



        Bundle bundle = getIntent().getExtras();
        Name = bundle.getString("Name");
        RollNo = bundle.getString("RollNo");
        CycleDescription = bundle.getString("CycleDescription");
        PhoneNumber = bundle.getString("PhoneNumber");

        b = bundle.getString("Cycle_Number");
        Pl.setText(bundle.getString("Present_location"));
        Pl1.setText(bundle.getString("Past_location_1"));
        Pl2.setText(bundle.getString("Past_location_2"));
        Pl3.setText(bundle.getString("Past_location_3"));
        Pl4.setText(bundle.getString("Past_location_4"));

        T.setText(bundle.getString("Time"));
        T1.setText(bundle.getString("Past_time_1"));
        T2.setText(bundle.getString("Past_time_2"));
        T3.setText(bundle.getString("Past_time_3"));
        T4.setText(bundle.getString("Past_time_4"));

        c = bundle.getString("riding_check");
      //name.setText(b);
       // NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if(c.equals("1")){
            ridingCheck.setChecked(true);
        }
        else{
            ridingCheck.setChecked(false);
        }
        PasswordOriginal = bundle.getString("Password");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        B.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v){
               StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                       new Response.Listener<String>() {
                           @Override
                           public void onResponse(String response) {
                               try{
                                   JSONArray jsonArray = new JSONArray(response);
                                   JSONObject jsonObject =jsonArray.getJSONObject(0);
                                   Pl.setText(jsonObject.getString("Present_location"));
                                   Pl1.setText(jsonObject.getString("Past_location_1"));
                                   Pl2.setText(jsonObject.getString("Past_location_2"));
                                   Pl3.setText(jsonObject.getString("Past_location_3"));
                                   Pl4.setText(jsonObject.getString("Past_location_4"));
                                   ridingcheck = jsonObject.getString("riding_check");
                                   T.setText(jsonObject.getString("Time"));

                                   T1.setText(jsonObject.getString("Past_time_1"));
                                   T2.setText(jsonObject.getString("Past_time_2"));
                                   T3.setText(jsonObject.getString("Past_time_3"));
                                   T4.setText(jsonObject.getString("Past_time_4"));
                                   if(ridingcheck.equals("0"))
                                       ridingCheck.setChecked(false);
                                   else
                                       ridingCheck.setChecked(true);

                               }catch (JSONException e){
                                   e.printStackTrace();
                               }
                           }
                       }, new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {
                       if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                           Toast.makeText(Mainpage2.this,"Check network connection",
                                   Toast.LENGTH_LONG).show();
                       } else if (error instanceof AuthFailureError) {
                           Toast.makeText(Mainpage2.this,"Authentication failure",
                                   Toast.LENGTH_LONG).show();
                           //TODO
                       } else if (error instanceof ServerError) {Toast.makeText(Mainpage2.this,"Server is down",
                               Toast.LENGTH_LONG).show();

                       }
                       //TODO


                   }
               })
               {
                   @Override
                   protected Map<String, String> getParams() throws AuthFailureError {

                       Map<String,String> params = new HashMap<String, String>();
                       params.put("Cycle_Number", b);
                       return params;
                   }
               };
               MySingleton.getInstance(Mainpage2.this).addToRequestque(stringRequest);

           }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        name = (TextView)hView.findViewById(R.id.username);
        name.setText(Name);
        navigationView.setNavigationItemSelectedListener(this);

    }

    public void onClickCheckBox(View view){

        final boolean checked = ((CheckBox) view).isChecked();

        builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter password");
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT |InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Password = input.getText().toString();
                if(Password.equals(PasswordOriginal)){

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url2,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try{
                                        JSONArray jsonArray = new JSONArray(response);
                                        JSONObject jsonObject =jsonArray.getJSONObject(0);


                                    }catch (JSONException e){
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error instanceof NoConnectionError) {
                                Toast.makeText(Mainpage2.this, "Check your network connection",
                                        Toast.LENGTH_LONG).show();
                            }
                                if(error instanceof TimeoutError){
                                    Toast.makeText(Mainpage2.this,"Server is down",
                                            Toast.LENGTH_LONG).show();
                                }
                             else if (error instanceof AuthFailureError) {
                                Toast.makeText(Mainpage2.this,"Authentication failure",
                                        Toast.LENGTH_LONG).show();
                                //TODO
                            } else if (error instanceof ServerError) {Toast.makeText(Mainpage2.this,"Server is down",
                                    Toast.LENGTH_LONG).show();

                            }
                            //TODO


                        }
                    })
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String,String> params = new HashMap<String, String>();
                            params.put("Password", PasswordOriginal);
                            if(checked)
                                params.put("riding_check", "1");
                            else
                                params.put("riding_check", "0");

                            return params;
                        }
                    };
                    MySingleton.getInstance(Mainpage2.this).addToRequestque(stringRequest);
                    Toast.makeText(Mainpage2.this,"Update successful!",
                            Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(Mainpage2.this,"Incorrect password!",
                            Toast.LENGTH_LONG).show();
                    if(checked)
                        ridingCheck.setChecked(false);
                    else
                        ridingCheck.setChecked(true);
                }
            }

        });
        builder.show();
        Pass.setText("");
    }

    @Override
    public void onBackPressed() {
        B.setVisibility(View.VISIBLE);
        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();


        } else {
            getFragmentManager().popBackStack();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mainpage2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fg;
        if (id == R.id.nav_help) {
            B.setVisibility(View.INVISIBLE);
            getFragmentManager().beginTransaction().replace(R.id.content_frame,new Help()).addToBackStack("help")
                    .commit();
            // Handle the camera action
        }

        else if (id == R.id.nav_accDet) {
            Bundle bundle = new Bundle();
            bundle.putString("Name", Name);
            bundle.putString("roll", RollNo);
            bundle.putString("phone", PhoneNumber);
            bundle.putString("cycle", CycleDescription);
            MyAccount fragment = new MyAccount();
            fragment.setArguments(bundle);
            B.setVisibility(View.INVISIBLE);
            getFragmentManager().beginTransaction().replace(R.id.content_frame,fragment).addToBackStack("AccDet")
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
