package com.example.projectstart.UserImagesListAndGridViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.projectstart.R;

import java.util.ArrayList;

import Model.User;

public class UsersAdapter extends ArrayAdapter<User> {
    private Context ctx;
    private ArrayList<User> usersArrayList;
    private int resourceId;
    private TextView textViewUserName;

    public UsersAdapter(Context context, int resource, ArrayList<User> objects)
    {
        super(context,resource,objects);
        this.ctx=context;
        this.resourceId=resource;
        this.usersArrayList=objects;
    }

    @Override
    public int getCount()
    {
        return this.usersArrayList.size();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater layerInflator=(LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layerInflator.inflate(this.resourceId,null);
        User user=this.usersArrayList.get(position);
        textViewUserName=view.findViewById(R.id.user_text_view);
        textViewUserName.setText(user.getUserName());

        return view;
    }


}
