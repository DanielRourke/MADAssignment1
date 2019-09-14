package com.example.madass1;

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

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.madass1.ui.main.SectionsPagerAdapter;


public class MainActivity extends AppCompatActivity implements ShopListFragment.OnFragmentInteractionListener {
    public static final String TAG = "MyActivity";
    public DatabaseManager DBmanager;
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



}