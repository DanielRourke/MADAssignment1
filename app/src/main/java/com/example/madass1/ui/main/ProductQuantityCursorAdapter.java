package com.example.madass1.ui.main;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.TextView;

import com.example.madass1.R;

public class ProductQuantityCursorAdapter extends ProductCursorAdapter {

    public ProductQuantityCursorAdapter(Context context, int layout, Cursor c, int flags) {
        super(context, layout, c, flags);
    }

        @Override
    public void bindView(View view, Context context, Cursor cursor) {
      super.bindView(view, context, cursor);

      TextView quantity = (TextView) view.findViewById(R.id.textView_Quantity);

      quantity.setText(cursor.getString(cursor.getColumnIndexOrThrow("Quantity")));

    }
}
