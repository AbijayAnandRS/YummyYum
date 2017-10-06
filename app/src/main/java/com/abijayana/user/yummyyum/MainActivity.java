package com.abijayana.user.yummyyum;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;GridView gv1,gv2;foodAdapter fo1;ArrayList<food> list1,list2;Animation an;
    static View rootView1;LayoutInflater lf1;foodAdapter veg;
    static View rootView2;LayoutInflater lf2;String rslt;ArrayList<String> ab;
    static View rootView3;Button b1;
    public SectionsPagerAdapter mSectionsPagerAdapter;

    public ViewPager mViewPager;Firebase fr,or,gr;SharedPreferences sec,n1,n2,spin;String result1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.app_bar_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Maai Canteen");
        b1=(Button)findViewById(R.id.faba) ;
        sec=this.getSharedPreferences("Security",MODE_PRIVATE);
        n1=this.getSharedPreferences("name",MODE_PRIVATE);
        n2=this.getSharedPreferences("phone",MODE_PRIVATE);
        spin=this.getSharedPreferences("spinner",MODE_PRIVATE);
        SharedPreferences.Editor edr=sec.edit();
        edr.putInt("HAI",1);
        edr.commit();
        fr=new Firebase("https://yummyyum.firebaseio.com/snacks");
        or=new Firebase("https://yummyyum.firebaseio.com/ordered");
        gr=new Firebase("https://yummyyum.firebaseio.com/vegetables");
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container1);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs1);
        tabLayout.setupWithViewPager(mViewPager);
        lf1=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE) ;
        lf2=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE) ;
        rootView1 =lf1.inflate(R.layout.content_main,null);
        rootView2=lf2.inflate(R.layout.contentmain2,null);
        gv1=(GridView) rootView1.findViewById(R.id.gridView1) ;
        gv2=(GridView)rootView2.findViewById(R.id.gridView2);
        list1=new ArrayList<food>();
        list2=new ArrayList<food>();int yu;


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
int sum=0;int op;
                for(op=0;op<list1.size();op++){
                    sum=sum+list1.get(op).getCount();
                }
                for(op=0;op<list2.size();op++){
                    sum=sum+list2.get(op).getCount();
                }
                if(sum==0)donot();
                else douio();



            }
        });
        fo1=new foodAdapter(MainActivity.this,R.layout.griditem,list1);
        veg=new foodAdapter(MainActivity.this,R.layout.griditem,list2);
        gv2.setAdapter(veg);
        gv1.setAdapter(fo1);
        Query av=gr.orderByKey();
        Query  cv=fr.orderByKey();
        av.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list2.clear();
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    food kj=new food();
                    kj.setFnme(String.valueOf(dataSnapshot1.child("name").getValue()));
                    kj.setImurl(String.valueOf(dataSnapshot1.child("imgurl").getValue()));
                    kj.setMrp(String.valueOf(dataSnapshot1.child("mrp").getValue()));
                    list2.add(kj);
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                    error();
            }
        });
        cv.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list1.clear();findViewById(R.id.loadingPanel1).setVisibility(View.GONE);
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    food jk=new food();
                    jk.setFnme(String.valueOf(dataSnapshot1.child("name").getValue()));
                    jk.setImurl(String.valueOf(dataSnapshot1.child("imgurl").getValue()));
                    jk.setMrp(String.valueOf(dataSnapshot1.child("mrp").getValue()));
                    list1.add(jk);
                }
                fo1.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        for (yu = 0; yu < list1.size(); yu++) {
            list1.get(yu).setCount(0);
        }
        for (yu = 0; yu < list2.size(); yu++) {
            list2.get(yu).setCount(0);
        }
        gr.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                veg.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                veg.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                   veg.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                     veg.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                      error();
            }
        });
        fr.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                fo1.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fo1.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                fo1.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                fo1.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                    error();
            }
        });




    }

    @Override
    public void onBackPressed() {


            super.onBackPressed();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent i= new Intent(Intent.ACTION_CALL);

            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                return super.onOptionsItemSelected(item);

            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                i.setPackage("com.android.server.telecom");
            }

            i.setData(Uri.parse("tel:9414903875"));

            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public static class abi1 extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            return rootView1;
        }

    }
    public static class abi2 extends Fragment{

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            return rootView2;
        }

    }



    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:

                    return new abi1();


                case 1:

                    return new abi2();


                default:
                    //this page does not exists
                    return null;
            }
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

        }

        @Override
        public int getCount() {

            return 2;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "FOOD";
                case 1:
                    return "VEGETABLES";


            }
            return null;
        }

    }
    public void error(){
        Toast.makeText(MainActivity.this,"ERROR",Toast.LENGTH_SHORT).show();
    }
    public void donot(){
        final Dialog dialog=new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dlog);
        dialog.setCancelable(true);
        dialog.show();
        Button n=(Button)dialog.findViewById(R.id.buttony);
        n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

    }
    public void douio(){
        final Dialog dial=new Dialog(MainActivity.this,android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        dial.setContentView(R.layout.orderlcn);
        dial.setCancelable(true);
        dial.show();

        final Button bhj=(Button)dial.findViewById(R.id.button565) ;
        TextView edyu=(TextView) dial.findViewById(R.id.textView5yui) ;
        bhj.setVisibility(View.INVISIBLE);
        String []b={"IIT","NISER","CUTM","KIST"};
        ab=new ArrayList<String>();ab.add("Select Location");
        String []a={"Select Location","MHR","SHR","StaffQuaters","FacultyQuaters","LabComplex","SBS","SES","SIF","SMS","GuestHouse","DOH-1","DOH-2","DOH-3","SOH-1","THN","MainGate","Girls Hostel-1","Girls Hostel-2","Boys Hostel-1","Boys Hostel-2","Boys Hostel-3","Boys Hostel-4","Boys Hostel-5","Boys Hostel-6","GTET Hostel","Staff Residence"};
        ArrayAdapter<String> adp=new ArrayAdapter<String>(MainActivity.this,R.layout.spnnr,b);
        final ArrayAdapter<String> adp2=new ArrayAdapter<String>(MainActivity.this,R.layout.spnnr,ab);
        final Spinner sp=(Spinner)dial.findViewById(R.id.editText55) ;
        final Spinner sp2=(Spinner)dial.findViewById(R.id.cosmic);
        sp2.setAdapter(adp2);
        sp.setAdapter(adp);
        int y=spin.getInt("SPIN",0);
        sp.setSelection(y);
        String jk;
        String fnllist = "";

        int vb;int xc=0;
        for (vb = 0; vb < list2.size(); vb++) {
            if (list2.get(vb).getCount() > 0) {
                jk = list2.get(vb).getFnme() + " " + String.valueOf(list2.get(vb).getCount()) + "\n";
                fnllist = fnllist + jk;
                xc=xc+(stringtoint(list2.get(vb).getMrp())*list2.get(vb).getCount());
            }
        }
        for (vb = 0; vb < list1.size(); vb++) {
            if (list1.get(vb).getCount() > 0) {
                jk = list1.get(vb).getFnme() + " " + String.valueOf(list1.get(vb).getCount()) + "\n";
                fnllist = fnllist + jk;
                xc=xc+(stringtoint(list1.get(vb).getMrp())*list1.get(vb).getCount());
            }
        }
        fnllist=fnllist+"Total :"+String.valueOf(xc)+"RS"+"\n";
        edyu.setText("Total :"+String.valueOf(xc)+"RS");


        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rslt= String.valueOf(sp.getItemAtPosition(position));
                SharedPreferences.Editor yhu=spin.edit();
                yhu.putInt("SPIN",position);
                yhu.commit();
                if(position==0){
                    ab.clear();
                    ab.add("Select Location");
                    ab.add("MHR");ab.add("SHR");ab.add("LBC");ab.add("SBS");ab.add("SES");ab.add("SMS");ab.add("SIF");ab.add("Stsff Quaters");ab.add("Faculty Quarters");ab.add("Guest House");
                    adp2.notifyDataSetChanged();
                }
                else if(position==1){
                    ab.clear();
                    ab.add("Select Location");
                    ab.add("DOH-1");ab.add("DOH-2");ab.add("DOH-3");ab.add("SOH-1");ab.add("THN");ab.add("Main Gate");adp2.notifyDataSetChanged();
                }
                else if(position==2){
                    ab.clear();
                    ab.add("Select Location");ab.add("Girls Hostel 1");ab.add("Girls Hostel 2");ab.add("Boys Hostel 1");
                    ab.add("Boys Hostel 2");ab.add("Boys Hostel 3");ab.add("Boys Hostel 4");ab.add("Boys Hostel 5");ab.add("Boys Hostel 6");
                    ab.add("Gtet Hostel");ab.add("Staff Residence");adp2.notifyDataSetChanged();

                }
                else {
                    ab.clear(); ab.add("Select Location");ab.add("Main Gate");adp2.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                result1= String.valueOf(sp2.getItemAtPosition(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Button ordr=(Button)dial.findViewById(R.id.button55);
an=AnimationUtils.loadAnimation(MainActivity.this,R.anim.rotate);

        final String finalFnllist = fnllist;
        final int finalXc = xc;
        ordr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             if((result1.compareTo("Select Location")==0)||(finalXc ==0))Toast.makeText(MainActivity.this,"Fill Properly",Toast.LENGTH_SHORT).show();
                else {
                 sp.setVisibility(View.INVISIBLE);
                 sp2.setVisibility(View.INVISIBLE);
                 bhj.setVisibility(View.VISIBLE);
                 bhj.startAnimation(an);



                 String name, phone_number;
                 name = n1.getString("NAME", "Abijay");
                 phone_number = n2.getString("PHONE", "123456789");

                 HashMap<String, Object> mp = new HashMap<String, Object>();
                 mp.put("Location",rslt+" "+result1);
                 mp.put("p1", finalFnllist);
                 mp.put("phone", phone_number);
                 mp.put("customer", name);
                 long gh = (System.currentTimeMillis()) / 1000;


                 or.child(String.valueOf(gh)).updateChildren(mp, new Firebase.CompletionListener() {
                     @Override
                     public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                         if (firebaseError != null) {
                             error();
                             bhj.setVisibility(View.INVISIBLE);
                             sp.setVisibility(View.VISIBLE);
                             sp2.setVisibility(View.VISIBLE);

                         } else {
                             Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                             int yu;
                             dial.cancel();
                             success();
                             for (yu = 0; yu < list1.size(); yu++) {
                                 list1.get(yu).setCount(0);
                             }
                             for (yu = 0; yu < list2.size(); yu++) {
                                 list2.get(yu).setCount(0);
                             }
                             fo1.notifyDataSetChanged();
                             veg.notifyDataSetChanged();


                         }
                     }
                 });
             }


                }

        });



}
    public void success(){
       final  Dialog d=new Dialog(MainActivity.this);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.dlog_2);
        d.setCancelable(true);
        d.show();
        Button hn=(Button)d.findViewById(R.id.buttonyop);
        hn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.cancel();
            }
        });



    }
    public int stringtoint(String s){
        String g="";int y;int i;

        for(i=0;(i<s.length()&&(s.charAt(i)!='/'));i++){

            y=(int)s.charAt(i);
            if((y>=48)&&(y<=57)){

                g=g+String.valueOf(s.charAt(i));

            }

        }



        if(g.compareTo("")==0)return 0;


        int h=Integer.parseInt(g);
        return h;


    }
}
