package com.example.mados;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class VerifyOTP extends AppCompatActivity {

    EditText e1;
    Button b1;
    SharedPreferences sh;
    Integer count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_o_t_p);

        e1=(EditText)findViewById(R.id.etotp);
        b1=(Button)findViewById(R.id.btotp);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sh.getString("otp","").equalsIgnoreCase(e1.getText().toString()))
                {
                    if(count>3)
                    {
                        SharedPreferences.Editor e=sh.edit();
                        e.putString("checkval","ch");
                        e.commit();
                        startActivity(new Intent(getApplicationContext(), ViewImage.class));
                    }else {
                        startActivity(new Intent(getApplicationContext(), ViewImage.class));
                    }
                }
                else{
                    count=count+1;

                }
            }
        });
    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),Employee_home.class));
    }
}