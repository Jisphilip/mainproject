package com.example.mados;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.prefs.PreferenceChangeEvent;

public class ViewImage extends AppCompatActivity implements JsonResponse {

    SharedPreferences sh;
    String path;
    ImageView i1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        i1=(ImageView)findViewById(R.id.imageView);
        if(sh.getString("ft","").equalsIgnoreCase("Type1")) {
            String pth = "http://" + sh.getString("ip", "") + "/" + sh.getString("uf", "");
            pth = pth.replace("~", "");
//	       Toast.makeText(context, pth, Toast.LENGTH_LONG).show();

            Log.d("-------------", pth);
            Picasso.with(getApplicationContext())
                    .load(pth)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background).into(i1);
        }
        else{
            JsonReq JR=new JsonReq();
            JR.json_response=(JsonResponse) ViewImage.this;
            String q = "/downloadfile?mid="+sh.getString("fid","");
            q=q.replace(" ","%20");
            JR.execute(q);
        }

    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method = jo.getString("method");
            if (method.equalsIgnoreCase("downloadfile")) {
                String status = jo.getString("status");
                Log.d("pearl", status);
                Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
                if (status.equalsIgnoreCase("success")) {

                    String path = jo.getString("path");
                    if(sh.getString("checkval","").equalsIgnoreCase("ch")) {
                        path = "static/sorry.png";
                    }

                    SharedPreferences.Editor e=sh.edit();
                    e.putString("checkval","");
                    e.commit();

                    String pth = "http://" + sh.getString("ip", "") + "/" + path;
                    pth = pth.replace("~", "");
//	       Toast.makeText(getApplicationContext(), pth, Toast.LENGTH_LONG).show();

                    Log.d("-------------", pth);
                    Picasso.with(getApplicationContext())
                            .load(pth)
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.ic_launcher_background).into(i1);
                }
            }
        }
        catch (Exception e)
        {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),Employee_home.class));
    }
}