package com.example.madass1.ui.main;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madass1.MainActivity;
import com.example.madass1.R;


public class ShopListFragment extends ListFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    private String mParam1;

    private OnFragmentInteractionListener mListener;

    public ShopListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param param1 Parameter 1.
     * @return A new instance of fragment ShopListFragment.
     */
    public static ShopListFragment newInstance(String param1) {
        ShopListFragment fragment = new ShopListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop_list, container, false);


        MainActivity main = (MainActivity) getActivity();
        Context context = getContext();
      //  ListView listView = view.findViewById(R.id.listView_Shop);

        ProductQuantityCursorAdapter adapter;
        if(mParam1.equals("Shopping"))
        {
            adapter  = new ProductQuantityCursorAdapter(context,
                    R.layout.fragment_shop_item, main.DBmanager.retrieveShopping(), 0);
        }
        else if(mParam1.equals("Pantry"))
        {
            adapter  = new ProductQuantityCursorAdapter(context,
                    R.layout.fragment_shop_item, main.DBmanager.retrievePantry(), 0);
        }
        else
        {
            adapter  = new ProductQuantityCursorAdapter(context,
                    R.layout.fragment_shop_item, main.DBmanager.retrieveShopping(), 0);
        }


       setListAdapter(adapter);



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
            v.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
        }
        else
        {
            v.setBackgroundColor(getResources().getColor(android.R.color.background_light));
        }

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
                    main.DBmanager.addShoppingItem((int) list.getAdapter().getItemId(key), 1 );

                    //uncheck the list item
                    list.setItemChecked(key, false);

                    //cast get item to list item and change background color
                    RelativeLayout r = (RelativeLayout)getListView().getChildAt(key);
                    r.setBackgroundColor(getResources().getColor(android.R.color.background_light));

                }
            }
            onMenuButtonPressed();
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
                    RelativeLayout r = (RelativeLayout)getListView().getChildAt(key);
                    TextView txt = (TextView) r.findViewById(R.id.textView_Quantity);


                    //Add checked item to pantry
                    main.DBmanager.addPantryItem((int) list.getAdapter().getItemId(key), Integer.parseInt(txt.getText().toString() ));
                    main.DBmanager.deleteShoppingItem((int) list.getAdapter().getItemId(key));
                    //uncheck the list item
                    list.setItemChecked(key, false);

                    //cast get item to list item and change background color
                    r.setBackgroundColor(getResources().getColor(android.R.color.background_light));

                }
            }
            onMenuButtonPressed();
            return true;
        }
        else if (id == R.id.action_delete) {
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

                    if(mParam1.equals("Shopping"))
                    {

                        main.DBmanager.deleteShoppingItem((int) list.getAdapter().getItemId(key));
                    }
                    else if(mParam1.equals("Pantry"))
                    {
                        main.DBmanager.deletePantryItem((int) list.getAdapter().getItemId(key));
                    }


                    //uncheck the list item
                    list.setItemChecked(key, false);
//
//                    //cast get item to list item and change background color
//                    RelativeLayout r = (RelativeLayout)getListView().getChildAt(key);
//                    r.setBackgroundColor(getResources().getColor(android.R.color.background_light));
                    onMenuButtonPressed();
                }
            }
            return true;
        }

        onMenuButtonPressed();

        return false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        if(mParam1.equals("Shopping"))
        {
            inflater.inflate(R.menu.menu_shopping_list, menu);
        }
        else if(mParam1.equals("Pantry"))
        {
            inflater.inflate(R.menu.menu_pantry_list, menu);
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

//    // TODO: Rename method, update argument and hook method into UI event
    public void onMenuButtonPressed() {
        if (mListener != null) {
            mListener.onFragmentInteraction(mParam1);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String mParam1);
    }
}
