package com.example.jasonmitropoulos.jsbookstoreapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.jasonmitropoulos.jsbookstoreapp.data.BookContract.BookEntry;

public class BookCursorAdapter extends CursorAdapter {

    public BookCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView summaryTextView = (TextView) view.findViewById(R.id.price);
        TextView quantityTextView = (TextView) view.findViewById(R.id.quantity);
        TextView supplierNameTextView = (TextView) view.findViewById(R.id.supplier_name);
        TextView supplierPhoneTextView = (TextView) view.findViewById(R.id.supplier_phone);
        Button shopView = (Button) view.findViewById(R.id.sale);

        int idColumnIndex = cursor.getColumnIndex(BookEntry._ID);
        int nameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_QUANTITY);
        int supplierNameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_SUPPLIER_NAME);
        int supplierPhoneColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_SUPPLIER_PHONE);


        final int bookId = cursor.getInt(idColumnIndex);
        String name = cursor.getString(nameColumnIndex);
        String price = cursor.getString(priceColumnIndex);
        final int quantity = cursor.getInt(quantityColumnIndex);
        String supplierName = cursor.getString(supplierNameColumnIndex);
        String supplierPhone = cursor.getString(supplierPhoneColumnIndex);


        nameTextView.setText(name);
        summaryTextView.setText(price);
        quantityTextView.setText(String.valueOf(quantity));
        supplierNameTextView.setText(supplierName);
        supplierPhoneTextView.setText(supplierPhone);

        shopView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri currentBookUri = ContentUris.withAppendedId(BookEntry.CONTENT_URI, bookId);
                Sale(context, currentBookUri, quantity);


            }
        });
    }

    private void Sale(Context context, Uri bookUri, int quantity) {

        if (quantity == 0) {
            return;
        }
        quantity--;


        ContentValues values = new ContentValues();
        values.put(BookEntry.COLUMN_QUANTITY, quantity);
        context.getContentResolver().update(bookUri, values, null, null);

    }

}

