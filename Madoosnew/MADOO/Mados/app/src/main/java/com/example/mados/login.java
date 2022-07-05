package com.example.mados;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class login extends AppCompatActivity implements JsonResponse{

    EditText e1,e2;
    Button b1;
    String username,password;
    public static String logid,usertype,phone;
    SharedPreferences sh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1=(EditText)findViewById(R.id.username);
        e2=(EditText)findViewById(R.id.password);
        b1=(Button)findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Toast.makeText(getApplicationContext(),"hiiiii",Toast.LENGTH_LONG).show();
                username=e1.getText().toString();
                password=e2.getText().toString();

                if(username.equalsIgnoreCase(""))
                {
                    e1.setError("please enter username");
                    e1.setFocusable(true);
                }
                else if(password.equalsIgnoreCase(""))
                {
                    e2.setError("enter correct Password");
                    e2.setFocusable(true);
                }
                else{
//                    Toast.makeText(getApplicationContext(),"Helloo",Toast.LENGTH_LONG).show();
                    JsonReq JR=new JsonReq();
                    JR.json_response=(JsonResponse) login.this;
                    String q = "/login?username="+username+"&password="+password;
                    q=q.replace(" ","%20");
                    JR.execute(q);
                }
            }
        });
    }

    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub
        try {
            String status=jo.getString("status");
            Log.d("pearl",status);
            //Toast.makeText(getApplicationContext(),status, Toast.LENGTH_LONG).show();

            if(status.equalsIgnoreCase("success")){
                JSONArray ja1=(JSONArray)jo.getJSONArray("data");
                logid=ja1.getJSONObject(0).getString("login_id");
                usertype=ja1.getJSONObject(0).getString("usertype");
//                phone=ja1.getJSONObject(0).getString("phone");

			SharedPreferences.Editor e=sh.edit();
				e.putString("log_id", logid);
//                e.putString("phone", phone);
				e.commit();
                if(usertype.equals("employee"))
                {


                    Toast.makeText(getApplicationContext()," You are Login Successfully!...,",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),Employee_home.class));

                }



            }
            else {
                Toast.makeText(getApplicationContext(),"Login failed..!Please enter correct username or password ",Toast.LENGTH_LONG).show();
//				Intent i=new Intent(getApplicationContext(),MainLogin.class);
                startActivity(new Intent(getApplicationContext(),login.class));
            }


        }catch (Exception e) {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
        }


    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
       startActivity(new Intent(getApplicationContext(),ip_setting.class));
    }


}