package com.example.mados;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Verifyfiles extends AppCompatActivity implements JsonResponse {

    EditText e1;
    Button b1;
    SharedPreferences sh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifyfiles);

        e1=(EditText)findViewById(R.id.etverfiy);
        b1=(Button)findViewById(R.id.btverify);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                JsonReq JR=new JsonReq();
                JR.json_response=(JsonResponse) Verifyfiles.this;
                String q = "/verifyfiles?fid="+sh.getString("fid","")+"&content="+e1.getText().toString();
                q=q.replace(" ","%20");
                JR.execute(q);
            }
        });


    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("verifyfiles")) {

                String status = jo.getString("status");
                Log.d("pearl", status);



                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "Verified Successfully", Toast.LENGTH_LONG).show();

                    if(sh.getString("ft","").equalsIgnoreCase("Type2")) {
                        startActivity(new Intent(getApplicationContext(), ViewImage.class));
                    }
                    if(sh.getString("ft","").equalsIgnoreCase("Type3")) {
                        startActivity(new Intent(getApplicationContext(), AndroidBarcodeQrExample.class));
                    }
                    if(sh.getString("ft","").equalsIgnoreCase("Type4")) {
                        startActivity(new Intent(getApplicationContext(), AndroidBarcodeQrExample.class));
                    }
                } else {

                    Toast.makeText(getApplicationContext(), " Enterd key is not correct!!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Employee_view_uploaded_files.class));
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