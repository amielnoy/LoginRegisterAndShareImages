package com.example.projectstart.UserImagesListAndGridViews;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.projectstart.R;

import java.util.ArrayList;

import Model.User;
import Model.UserImage;


public class GridViewAdapter extends ArrayAdapter<UserImage> {
    private ArrayList<UserImage> userImagesArrayList;

    public GridViewAdapter(@NonNull Context context, ArrayList<UserImage> userImagesArrayList) {
        super(context, 0, userImagesArrayList);
        this.userImagesArrayList=userImagesArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_grid_view_adapte_layout, parent, false);
        }

        UserImage userImage = userImagesArrayList.get(position);
        TextView userTextView = listitemView.findViewById(R.id.user_name_text_view);
        ImageView userImageView = listitemView.findViewById(R.id.user_image_view);

        userTextView.setText(userImage.getUserName());
        byte [] imageBytesArray= userImage.getLastTakenUserImage();
        if (imageBytesArray != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytesArray, 0, imageBytesArray.length);
            userImageView.setImageBitmap(bitmap);
        }

        return listitemView;
    }
}
