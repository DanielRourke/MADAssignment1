package com.example.madass1.ui.main;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

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
            name.setText(cursor.getColumnIndexOrThrow("Name"));
            location.setText(cursor.getColumnIndexOrThrow("Location"));
            type.setText(cursor.getColumnIndexOrThrow("Type"));

        }
}
