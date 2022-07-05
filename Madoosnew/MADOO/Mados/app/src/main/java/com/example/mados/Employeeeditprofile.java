package com.example.mados;

import androidx.appcompat.app.AppCompatActivity;

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

public class Employeeeditprofile extends AppCompatActivity implements JsonResponse{

    EditText e1,e2,e3,e4,e5,e6;
    String fname,lname,place,email,qualification,phone;
    Button b1;
    String[] firstname,lastname,places,emails,phones,qualifications;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employeeeditprofile);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1=(EditText)findViewById(R.id.firstname);
        e2=(EditText)findViewById(R.id.lastname);
        e3=(EditText)findViewById(R.id.place);
        e4=(EditText)findViewById(R.id.phone);
        e5=(EditText)findViewById(R.id.email);
        e6=(EditText)findViewById(R.id.qualification);
        b1=(Button)findViewById(R.id.button4);
        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse)Employeeeditprofile.this;
        String q="/employeeviewprofile?lid="+sh.getString("log_id","");
        q=q.replace(" ","%20");
        JR.execute(q);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fname=e1.getText().toString();
                lname=e2.getText().toString();
                place=e3.getText().toString();
                phone=e4.getText().toString();
                email=e5.getText().toString();
                qualification=e6.getText().toString();
                JsonReq JR = new JsonReq();
                JR.json_response = (JsonResponse) Employeeeditprofile.this;
                String q = "/employeeeditprofile?fname=" + fname +"&lname="+lname+"&place="+place+"&phone="+phone+"&email="+email+"&qualification="+qualification+ "&lid=" + sh.getString("log_id", "");
                q = q.replace(" ", "%20");
                JR.execute(q);
            }
        });
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method = jo.getString("method");
            if (method.equalsIgnoreCase("employeeeditprofile")) {

                String status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "updated SUCCESSFULLY", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Employeeeditprofile.class));

                } else {

                    Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
                }
            } else if (method.equalsIgnoreCase("employeeviewprofile")) {
                String status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {
                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");

                    e1.setText( ja1.getJSONObject(0).getString("first_name"));
                    e2.setText( ja1.getJSONObject(0).getString("last_name"));
                    e3.setText( ja1.getJSONObject(0).getString("place"));
                    e4.setText( ja1.getJSONObject(0).getString("phone"));
                    e5.setText( ja1.getJSONObject(0).getString("email"));
                    e6.setText( ja1.getJSONObject(0).getString("qualification"));



                }

            }
        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),Employee_home.class));
    }
}