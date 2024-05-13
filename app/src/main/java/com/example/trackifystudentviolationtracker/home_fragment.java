package com.example.trackifystudentviolationtracker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link home_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class home_fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public home_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static home_fragment newInstance(String param1, String param2) {
        home_fragment fragment = new home_fragment();
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
       View view = inflater.inflate(R.layout.fragment_home_fragment, container, false);

       //Initializing Buttons
       Button btn_scanner = (Button) view.findViewById(R.id.scan_Btn);
       Button btn_manInput = (Button) view.findViewById(R.id.inputID_Btn);


       //Camera Scanner Intent
        btn_scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeScan();
            }
        });

        //Manual Input Intent

        btn_manInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    codeInput();
            }
        });




       return view;
    }

    private void codeScan(){
        ScanOptions options = new ScanOptions();
        options.setCameraId(0);
        options.setPrompt(null);
        options.setCaptureActivity(camera_scanner.class);
        options.setOrientationLocked(true);
        options.setBarcodeImageEnabled(true);
        options.setBeepEnabled(false);
        barLauncher.launch(options);

        options.setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES);
    }

    //To show the result to edit_details.class
    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result ->{

        if (result.getContents() != null){
            Intent Resultintent = new Intent (getActivity(), edit_details.class);
            Resultintent.putExtra("",result.getContents());
            startActivity(Resultintent);

        }
    });



    private void codeInput(){
        // Create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), androidx.appcompat.R.style.Base_Theme_AppCompat_Light_Dialog_Alert);
        builder.setTitle("Enter student ID number");

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.input_custom_layout, null);
        builder.setView(customLayout);

        // add a button
        builder.setPositiveButton("SUBMIT", (dialog, which) -> {

            // send data from the AlertDialog to the Activity
            EditText editText = customLayout.findViewById(R.id.input_id);
            sendDialogDataToActivity(editText.getText().toString());
        });
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }



    // To show the result in edit_details.class
    private void sendDialogDataToActivity(String data) {

        //To check when the input field is empty before proceeding
        if (data.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomAlertDialogTheme);
            builder.setTitle("")
                    .setMessage("Please enter student ID number")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

            //Creating dialog box
            AlertDialog dialog = builder.create();
            dialog.show();

        } else{
            //To show the result to edit_details.class
            Intent Resultintent = new Intent (getActivity(), edit_details.class);
            Resultintent.putExtra("",data.toString());
            startActivity(Resultintent);
        }


    }
}

