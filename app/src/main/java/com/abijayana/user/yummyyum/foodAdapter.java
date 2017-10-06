package com.abijayana.user.yummyyum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 31-08-2016.
 */
public class foodAdapter extends ArrayAdapter<food> {int count;
    Context context;
    int resource;
    ArrayList<food> objects;
    public foodAdapter(Context context, int resource, ArrayList<food> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
       ViewHoldera vw=new ViewHoldera();
        if(convertView==null){
            LayoutInflater lf=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=lf.inflate(resource,null);
            vw.iv=(ImageView)convertView.findViewById(R.id.imageView2);
            vw.tv1=(TextView)convertView.findViewById(R.id.textView2);
            vw.tv2=(TextView)convertView.findViewById(R.id.textView3);
            vw.tv3=(TextView)convertView.findViewById(R.id.button42);
            vw.tv4=(TextView)convertView.findViewById(R.id.textView42);
            convertView.setTag(vw);



        }
        else  vw=(ViewHoldera)convertView.getTag();
        count=objects.get(position).getCount();
        if(count==0){
            vw.tv4.setVisibility(View.INVISIBLE);

            vw.tv3.setVisibility(View.INVISIBLE);
        }

        final ViewHoldera finalVw = vw;
        vw.tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count=objects.get(position).getCount();
                if(count>0)count--;
                if(count==0){ finalVw.tv4.setVisibility(View.INVISIBLE);
                finalVw.tv3.setVisibility(View.INVISIBLE);}
                objects.get(position).setCount(count);
                finalVw.tv4.setText(String.valueOf(objects.get(position).getCount()));
            }
        });
        vw.tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count=objects.get(position).getCount();
                count++;if(count==1){finalVw.tv4.setVisibility(View.VISIBLE);
                    finalVw.tv3.setVisibility(View.VISIBLE);
                }

                objects.get(position).setCount(count);
                finalVw.tv4.setText(String.valueOf(objects.get(position).getCount()));
            }
        });
        vw.tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count=objects.get(position).getCount();
                count++;
                if(count==1){finalVw.tv4.setVisibility(View.VISIBLE);
                    finalVw.tv3.setVisibility(View.VISIBLE);
                }

                objects.get(position).setCount(count);
                finalVw.tv4.setText(String.valueOf(objects.get(position).getCount()));
            }
        });
        vw.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count=objects.get(position).getCount();
                count++;
                if(count==1){finalVw.tv4.setVisibility(View.VISIBLE);
                    finalVw.tv3.setVisibility(View.VISIBLE);
                }

                objects.get(position).setCount(count);
                finalVw.tv4.setText(String.valueOf(objects.get(position).getCount()));
            }
        });



        Picasso.with(getContext()).load(objects.get(position).getImurl()).into(vw.iv);
        vw.tv1.setText(objects.get(position).getFnme());
        vw.tv2.setText(objects.get(position).getMrp());
        count=objects.get(position).getCount();
        if(count!=0){
            vw.tv4.setVisibility(View.VISIBLE);

            vw.tv3.setVisibility(View.VISIBLE);
            finalVw.tv4.setText(String.valueOf(objects.get(position).getCount()));
        }
        Animation an= AnimationUtils.loadAnimation(getContext(),R.anim.upgo);
        convertView.startAnimation(an);

        return convertView;
    }


    public class ViewHoldera{
        ImageView iv;
        TextView tv1;
        TextView tv2;
        TextView tv3;
        TextView tv4;

    }

}
