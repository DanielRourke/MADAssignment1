package com.example.madass1.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.madass1.MainActivity;
import com.example.madass1.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * 
 * Use the {@link AddProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddProductFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

   // private OnFragmentInteractionListener mListener;

    EditText type ;
    EditText name ;
    EditText location ;

    public AddProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddProductFragment newInstance(String param1, String param2) {
        AddProductFragment fragment = new AddProductFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_product, container, false);
        Button btnAdd = (Button) rootView.findViewById(R.id.button_Add_Product);
        Button btnClear = (Button) rootView.findViewById(R.id.button_Clear_Fields);

        type = (EditText) rootView.findViewById(R.id.editText_Type) ;
        name = (EditText)rootView.findViewById(R.id.editText_Name);
        location = (EditText) rootView.findViewById(R.id.editText_Location) ;

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity main = (MainActivity) getActivity();

                String nameTxt = name.getText().toString().trim();
                String locationText = location.getText().toString().trim();
                String typeTxt = type.getText().toString().trim();

                if( nameTxt.equals("") || locationText.equals("")  || typeTxt.equals(""))
                {
                    Toast.makeText(getContext(), "Recoord Not added", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(main.DBmanager.addProduct(nameTxt, locationText, typeTxt, "NoPic" ))
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

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTxt = "";
                String locationText = "";
                String typeTxt = "";

                type.setText("");
                name.setText("");
                location.setText("");
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
