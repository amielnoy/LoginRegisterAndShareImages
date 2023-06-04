package com.example.projectstart.UserDetails;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectstart.R;

import java.util.ArrayList;

import Model.User;

public class UserDetailsAdapter extends ArrayAdapter<User> {
    private Context ctx;
    private ArrayList<User> userArrayList;
    private int resourceId;

    public UserDetailsAdapter(Context context, int resource, ArrayList<User> objects)
    {
        super(context,resource,objects);
        this.ctx=context;
        this.resourceId=resource;
        this.userArrayList=objects;
    }

    @Override
    public int getCount()
    {
        return this.userArrayList.size();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater layoutInflator=(LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflator.inflate(this.resourceId,null);
        User userDetails=this.userArrayList.get(position);
        TextView userId=view.findViewById(R.id.id);
        TextView userName=view.findViewById(R.id.username);
      //  ImageView ivContacts=v.findViewById(R.id.contactImgView);
        userId.setText(userDetails.getId());
        userName.setText(userDetails.getUserName());
       // byte[] ary=c.getContactImage();

        return view;
    }


}
