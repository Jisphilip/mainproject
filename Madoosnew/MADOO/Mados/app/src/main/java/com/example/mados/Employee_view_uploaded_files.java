package com.example.mados;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Employee_view_uploaded_files extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {

    ListView lv1;
    String [] title,file_type,date,image,value,fid;
    public static String mp_ids;
    SharedPreferences sh;
    String file_types;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_view_uploaded_files);
        lv1=(ListView)findViewById(R.id.lvcmp);
        lv1.setOnItemClickListener(this);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) Employee_view_uploaded_files.this;
        String q = "/Employee_view_uploaded_files?login_id="+login.logid;
        q=q.replace(" ","%20");
        JR.execute(q);
    }



    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("Employee_view_uploaded_files")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");

                    fid=new String[ja1.length()];
                    title=new String[ja1.length()];
                    file_type=new String[ja1.length()];
                    date=new String[ja1.length()];
                    image=new String[ja1.length()];
                    value=new String[ja1.length()];



                    for(int i = 0;i<ja1.length();i++)
                    {

                        fid[i]=ja1.getJSONObject(i).getString("file_id");
                        title[i]=ja1.getJSONObject(i).getString("filename");
                        file_type[i]=ja1.getJSONObject(i).getString("file_type");
                        date[i]=ja1.getJSONObject(i).getString("date");
                        image[i]=ja1.getJSONObject(i).getString("file");
                        value[i]="Title : "+title[i]+"\nDate : "+date[i];


                    }
//                    Cust_images clist=new Cust_images(this,image,title,file_type,date);
//                    lv1.setAdapter(clist);
                    ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,value);
                    lv1.setAdapter(ar);


                }

                else {
                    Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();

                }
            }
//			if(method.equalsIgnoreCase("buyprod"))
//			{
//				String status=jo.getString("status");
//				Toast.makeText(getApplicationContext(),status, Toast.LENGTH_LONG).show();
//				if(status.equalsIgnoreCase("success"))
//				{
//					Toast.makeText(getApplicationContext(),"Your order is submitted!", Toast.LENGTH_LONG).show();
//				}
//				else{
//					Toast.makeText(getApplicationContext(),"Your order is not submitted", Toast.LENGTH_LONG).show();
//				}
//			}
        }catch (Exception e)
        {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
        }



    }



    @Override

    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Employee_upload_files.class);
        startActivity(b);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        file_types=file_type[position];
        SharedPreferences.Editor e=sh.edit();
        e.putString("uf",image[position]);
        e.putString("fid",fid[position]);
        e.putString("ft",file_type[position]);
        e.commit();
        if(file_types.equalsIgnoreCase("Type1"))
        {
            startActivity(new Intent(getApplicationContext(),ViewImage.class));

        }if(file_types.equalsIgnoreCase("Type2"))
        {
            startActivity(new Intent(getApplicationContext(),Verifyfiles.class));

        }
        if(file_types.equalsIgnoreCase("Type3"))
        {
            startActivity(new Intent(getApplicationContext(),Verifyfiles.class));

        }
        if(file_types.equalsIgnoreCase("Type4"))
        {
            startActivity(new Intent(getApplicationContext(),Verifyfiles.class));

        }
    }

}
