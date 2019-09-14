package com.example.madass1.ui.main;

import android.R.color;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.ListFragment;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.madass1.MainActivity;
import com.example.madass1.R;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */

//TODO add scrollable


public class ProductListFragment extends ListFragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    private static final String TAG = "ListFragment";
    //private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ProductListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ProductListFragment newInstance(int columnCount) {
        ProductListFragment fragment = new ProductListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        setHasOptionsMenu(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        MainActivity main = (MainActivity) getActivity();
        ListView list = getListView();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_shop_add)
        {
            //Gets a list of all checked items
            //adds to shopping list if they are checked items
            SparseBooleanArray checked =  list.getCheckedItemPositions();

            Toast.makeText(getContext(), String.valueOf(list.getCheckedItemCount()), Toast.LENGTH_SHORT).show();
            for ( int i =0; i <checked.size(); i++ )
            {
                int key = checked.keyAt(i);
                if(checked.get(key))
                {
                    //Add checked item to database;
                    main.DBmanager.addShoppingItem((int) list.getAdapter().getItemId(key), 1);

                    //uncheck the list item
                    list.setItemChecked(key, false);

                    //cast get item to list item and change background color
                    RelativeLayout r = (RelativeLayout)getListView().getChildAt(key);
                    r.setBackgroundColor(getResources().getColor(color.background_light));

                }
            }
            return true;
        }
        else if (id == R.id.action_pantry_add) {
            //Gets a list of all checked items
            //adds to shopping list if they are checked items
            SparseBooleanArray checked =  list.getCheckedItemPositions();
            Toast.makeText(getContext(), String.valueOf(list.getCheckedItemCount()), Toast.LENGTH_SHORT).show();
            for ( int i =0; i <checked.size(); i++ )
            {
                int key = checked.keyAt(i);
                if(checked.get(key))
                {
                    //Add checked item to database;
                    main.DBmanager.addPantryItem((int) list.getAdapter().getItemId(key), 1);

                    //uncheck the list item
                    list.setItemChecked(key, false);

                    //cast get item to list item and change background color
                    RelativeLayout r = (RelativeLayout)getListView().getChildAt(key);
                    r.setBackgroundColor(getResources().getColor(color.background_light));

                }
            }
            return true;
        }
        else if (id == R.id.action_product_add)
        {
            main.launchAddProduct();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_product_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        final MainActivity main = (MainActivity)getActivity();
        Context context = view.getContext();

        //Instantiate  a custom cursor adatper for product list
        ProductCursorAdapter adapter = new ProductCursorAdapter(context,
                R.layout.fragment_product_item,main.DBmanager.retrieveProducts() ,0);

        setListAdapter(adapter);

       ListView listView = view.findViewById(R.id.listView_Products);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    Cursor myCursor = (Cursor) getListView().getItemAtPosition(position);
//
//                    Toast.makeText(getContext(), myCursor.getString(0), Toast.LENGTH_SHORT).show();
                    main.launchEditProduct(Integer.parseInt(myCursor.getString(0)) );

                    return true;
                }
            });

//        listView.setChoiceMode(listView.CHOICE_MODE_MULTIPLE);

//        for(int i =0; i < adapter.getCount(); i++)
//        {
//            listView.setItemChecked(i,false);
//        }

        return view;
    }

    @Override
    public void onListItemClick(ListView list, View v, int position, long id)
    {

       SparseBooleanArray checked =  list.getCheckedItemPositions();

        //the selection is toggled on default for on click so we just
        //update background to show selection
        if(checked.get(position, true))
        {
            v.setBackgroundColor(getResources().getColor(color.holo_blue_light));
        }
        else
        {
            v.setBackgroundColor(getResources().getColor(color.background_light));
        }

    }

}
