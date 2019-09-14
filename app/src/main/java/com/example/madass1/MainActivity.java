package com.example.madass1;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import com.example.madass1.ui.main.AddProductFragment;
import com.example.madass1.ui.main.EditProductFragment;
import com.example.madass1.ui.main.MainFragment;
import com.example.madass1.ui.main.ProductListFragment;
import com.example.madass1.ui.main.ShopListFragment;
import com.example.madass1.ui.main.TabsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.madass1.ui.main.SectionsPagerAdapter;


//Daniel Rourke 19386993
//DECLARATION
//        I hold a copy of this assignment that I can produce if the original is lost or damaged.
//        I hereby certify that no part of this assignment/product has been copied from any other
//        student’s work or from any other source except where due acknowledgement is made in the
//        assignment.  No part of this assignment/product has been written/produced for me by another
//        person except where such collaboration has been authorised by the subject lecturer/tutor concerned.


public class MainActivity extends AppCompatActivity implements ShopListFragment.OnFragmentInteractionListener {
    public static final String TAG = "MyActivity";
    //private final static int REQUEST_PICK_CONTACT = 1;
    public final int PICK_CONTACT = 2015;
    public DatabaseManager DBmanager;
    SendSMS mSender = new SendSMS();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Start the database
        DBmanager = new DatabaseManager(this);

        //setup the menu and tittle
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Begin the transaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.fragment_placeholder, new MainFragment());
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Complete the changes added above
        ft.commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            home();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void home()
    {
        getSupportFragmentManager().popBackStack(null, getSupportFragmentManager().POP_BACK_STACK_INCLUSIVE);
        // Begin the transaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.fragment_placeholder, new MainFragment());
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Complete the changes added above
        ft.commit();
    }

    public void launchTabs(int index)
    {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_placeholder, TabsFragment.newInstance(index));
        ft.addToBackStack(null);
        ft.commit();
    }

    public void launchProductList()
    {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_placeholder, new ProductListFragment());
        ft.addToBackStack(null);
        ft.commit();
    }


    public void  launchAddProduct()
    {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_placeholder, new AddProductFragment());
        ft.addToBackStack(null);
        ft.commit();
    }

    public void launchEditProduct(int productId)
    {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_placeholder, EditProductFragment.newInstance(productId));
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onFragmentInteraction(String location) {


        //noinspection SimplifiableIfStatement
        if (location.equals("Shopping")) {
            getSupportFragmentManager().popBackStack(null, getSupportFragmentManager().POP_BACK_STACK_INCLUSIVE);
            launchTabs(0);
        }
        else if(location.equals("Pantry"))
        {
            getSupportFragmentManager().popBackStack(null, getSupportFragmentManager().POP_BACK_STACK_INCLUSIVE);
            launchTabs(1);
        }

    }

//http://www.enkeladress.com/article/android_snippen_show_contact_picker
    public void sendShoppingList(View v) {

        // This intent will fire up the contact picker dialog
        Intent intent = new Intent(Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);
    }
//https://stackoverflow.com/questions/18559574/android-contact-picker-with-only-phone-numbers
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        String phoneNo = "0404040404";
        if (requestCode == PICK_CONTACT && resultCode == RESULT_OK)
        {
            Uri contactUri = data.getData();
            Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
            cursor.moveToFirst();
            int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            phoneNo = cursor.getString(column);
            Log.d("phone number", cursor.getString(column));
        }

            Cursor shoppingList = DBmanager.retrieveShopping();

        String message = "";

        while(shoppingList.moveToNext())
        {
            message += shoppingList.getString(1) + shoppingList.getString(2) + shoppingList.getString(5);
        }


       //boolean success =  mSender.sendSMSMessage(phoneNo, message );

//        Toast.makeText(this, "Message sent " + (
//                        success ? "successfully" : "unsuccessfully"),
//                Toast.LENGTH_SHORT).show();

        Toast.makeText(this, "Sample code provided crashes phone",
                Toast.LENGTH_SHORT).show();



        super.onActivityResult(requestCode, resultCode, data);
    }



}