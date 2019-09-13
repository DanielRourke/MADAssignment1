package com.example.madass1.ui.main;

import android.R.color;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.madass1.MainActivity;
import com.example.madass1.R;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link //OnListFragmentInteractionListener}
 * interface.
 */
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        MainActivity main = (MainActivity)getActivity();
        Context context = view.getContext();

        //Instantiate  a custom cursor adatper for product list
        ProductCursorAdapter adapter = new ProductCursorAdapter(context,
                R.layout.fragment_product,main.DBmanager.retrieveProducts() ,0);

        setListAdapter(adapter);

        ListView listView = view.findViewById(R.id.listView_Products);
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
