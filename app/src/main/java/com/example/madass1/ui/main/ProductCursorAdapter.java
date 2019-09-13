package com.example.madass1.ui.main;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madass1.R;

public class ProductCursorAdapter extends ResourceCursorAdapter {
    public ProductCursorAdapter(Context context, int layout, Cursor c, int flags) {
        super(context, layout, c, flags);
    }

    @Override
        public void bindView(View view, Context context, Cursor cursor) {
            //ImageView image = view.findViewById(R.id.imageView_Image);
            TextView name = view.findViewById(R.id.textView_Name);
            TextView location = view.findViewById(R.id.textView_Location);
            TextView type = view.findViewById(R.id.textView_Type);

            //TODO add Image refence
            //image.setImageResource();
            name.setText(cursor.getString(cursor.getColumnIndexOrThrow("Name")));
            location.setText(cursor.getString(cursor.getColumnIndexOrThrow("Location")));
            type.setText(cursor.getString(cursor.getColumnIndexOrThrow("Type")));

        }

//        @Override
//        public View getView(int position, View convertView, ViewGroup parent)
//        {
//          convertView = super.getView(position, convertView, parent);
//
//          return convertView;
//        }

//        @Override
//        public Object getItem(int position)
//        {
//
//            return getCursor().getPosition(position).getColumnIndex(0)
//        }
}
