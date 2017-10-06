package com.abijayana.user.yummyyum;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by user on 04-09-2016.
 */
public class login extends AppCompatActivity {ConnectivityManager cm;NetworkInfo ni;
    SharedPreferences n1,n2,sec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sec=this.getSharedPreferences("Security",MODE_PRIVATE);
        n1=this.getSharedPreferences("name",MODE_PRIVATE);
        n2=this.getSharedPreferences("phone",MODE_PRIVATE);
        cm=(ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        ni=cm.getActiveNetworkInfo();
        if(ni==null)setContentView(R.layout.nonetwrk);
        else gotosomewhere();
    }


    public void gotosomewhere(){
        setContentView(R.layout.login);
        int f=sec.getInt("HAI",0);
        if(f==1)goclass("MainActivity");
        else fun();



    }

    public void fun(){
        setContentView(R.layout.login);
        final EditText ed1=(EditText)findViewById(R.id.editText54);
        final EditText ed2=(EditText)findViewById(R.id.editText254);
        Button b=(Button)findViewById(R.id.button54);
        hintr(ed1);hintr(ed2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check(ed1,ed2)) {
                    String name=ed1.getText().toString();String phone_number=ed2.getText().toString();
                    SharedPreferences.Editor nmy=n1.edit();
                    nmy.putString("NAME",name);
                    nmy.commit();
                    SharedPreferences.Editor ymn=n2.edit();
                    ymn.putString("PHONE",phone_number);
                    ymn.commit();
                    goclass("MainActivity");
                }
                else Toast.makeText(login.this,"Fill Properly",Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void hintr(final EditText ed){
        ed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ed.getText().toString().compareTo("")==0)ed.setHint("");
            }
        });
    }
    public boolean check(EditText ed1,EditText ed2){
        if((ed1.getText().toString().isEmpty())||(ed2.getText().toString().isEmpty())||((ed2.getText().toString()).length()<9))return false;

        else  return true;


    }
    public void goclass(String Classname){

        try {
            Class g=Class.forName("com.abijayana.user.yummyyum."+Classname);
            Intent i=new Intent(login.this,g);
            startActivity(i);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
