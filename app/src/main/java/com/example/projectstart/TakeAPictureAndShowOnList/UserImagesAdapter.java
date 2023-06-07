package com.example.projectstart.TakeAPictureAndShowOnList;


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

import Model.UserImage;

public class UserImagesAdapter extends ArrayAdapter<UserImage> {
    private Context ctx;
    private ArrayList<UserImage> userImagesArrayList;
    private int resourceId;

    public UserImagesAdapter(Context context, int resource, ArrayList<UserImage> objects)
    {
        super(context,resource,objects);
        this.ctx=context;
        this.resourceId=resource;
        this.userImagesArrayList=objects;
    }

    @Override
    public int getCount()
    {
        return this.userImagesArrayList.size();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater layoutInflator=(LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View userImageView=layoutInflator.inflate(this.resourceId,null);
        UserImage userImage=this.userImagesArrayList.get(position);

        ImageView imageViewUserImage=userImageView.findViewById(R.id.user_img_image_view);
        TextView userNametextView=userImageView.findViewById(R.id.editTextUserName);

        byte[] imageBytesArray=userImage.getLastTakenUserImage();
        if(imageBytesArray!=null)
        {
            Bitmap bitmap= BitmapFactory.decodeByteArray(imageBytesArray,0,imageBytesArray.length);
            imageViewUserImage.setImageBitmap(bitmap);
        }
        return userImageView;
    }


}
