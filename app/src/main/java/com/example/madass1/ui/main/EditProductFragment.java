package com.example.madass1.ui.main;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madass1.DatabaseManager;
import com.example.madass1.MainActivity;
import com.example.madass1.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * 
 * Use the {@link EditProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProductFragment extends Fragment {

    private static final String ARG_PARAM1 = "productId";

    private Integer productId;
    private MainActivity main;

   // private OnFragmentInteractionListener mListener;
    TextView textView_productId;
    EditText editText_type ;
    EditText editText_name ;
    EditText editText_location ;

    public EditProductFragment() {
        // Required empty public constructor
    }


    public static EditProductFragment newInstance(Integer param1) {
        EditProductFragment fragment = new EditProductFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            productId = getArguments().getInt(ARG_PARAM1);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_edit_product, container, false);
        Button btnEdit = (Button) rootView.findViewById(R.id.button_Edit_Product);
        Button btnDelete = (Button) rootView.findViewById(R.id.button_Delete_Product);
        main = (MainActivity) getActivity();


        textView_productId = (TextView) rootView.findViewById(R.id.textView_ProductId);
        editText_name = (EditText)rootView.findViewById(R.id.editText_Name);
        editText_location = (EditText) rootView.findViewById(R.id.editText_Location) ;
        editText_type = (EditText) rootView.findViewById(R.id.editText_Type) ;

        DatabaseManager.Product currentProduct = main.DBmanager.getProduct(productId);

        if(productId != currentProduct.id)
        {
            //TODO handl erroe here
        }

        textView_productId.setText(currentProduct.getId());
        editText_name.setText(currentProduct.name);
        editText_location.setText(currentProduct.location);
        editText_type.setText(currentProduct.type);

        btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                String name = editText_name.getText().toString().trim();
                String location = editText_location.getText().toString().trim();
                String type = editText_type.getText().toString().trim();

                if( name.equals("") || location.equals("")  || type.equals(""))
                {
                    Toast.makeText(getContext(), "Recoord Not added", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(main.DBmanager.editProduct(productId, name, location, type, "NoPic" ))
                    {
                        Toast.makeText(getContext(), "Recoord added", Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                    else
                    {
                        Toast.makeText(getContext(), "Recoord Not added Error Failed", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(main.DBmanager.deleteProduct(productId))
                {
                    Toast.makeText(getContext(), "Product Delete", Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager().popBackStack();
                }
                else
                {
                    Toast.makeText(getContext(), "Product Delete Error Failed", Toast.LENGTH_SHORT).show();
                }
            }
            });
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
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
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
