package com.example.nezzi.edumx.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.example.nezzi.edumx.APIUtility;
import com.example.nezzi.edumx.R;
import com.example.nezzi.edumx.interfaces.FragmentComunication;
import com.example.nezzi.edumx.models.Course;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CourseDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CourseDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CourseDetailFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int DROP_IN_REQUEST = 200;
    private Button btnCourseBuy;
    private TextView desc;
    Course course;
    TextView nameCourse;

    Activity activity;
    FragmentComunication fragmentComunication;

    private OnFragmentInteractionListener mListener;

    public CourseDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CourseDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CourseDetailFragment newInstance(String param1, String param2) {
        CourseDetailFragment fragment = new CourseDetailFragment();
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
        View view = inflater.inflate(R.layout.fragment_course_detail, container, false);
        desc= (TextView) view.findViewById(R.id.txt_courseDesc);
        nameCourse = view.findViewById(R.id.txt_courseName);
        btnCourseBuy = view.findViewById(R.id.btnCourseBuy);

        Bundle appObject = getArguments();
        course=null;
        if (appObject != null) {
            course= (Course) appObject.getSerializable("app_object");

            setCourseData(course);

        }

        /*btnAppEdit.setOnClickListener(this);
        btnAppDelete.setOnClickListener(this);*/
        btnCourseBuy.setOnClickListener(this);

        getActivity().setTitle("Detalle del Curso");

        return view;
    }

    public void setCourseData(Course course) {
        desc.setText(course.getDescription());
        nameCourse.setText(course.getName());
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof Activity){
            this.activity= (Activity) context;
            fragmentComunication= (FragmentComunication) this.activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public void setInfo(Course course) {
        desc.setText(course.getDescription());
        nameCourse.setText(course.getName());
    }

    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnCourseBuy: buyCourse();
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == DROP_IN_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                String paymentMethodNonce = result.getPaymentMethodNonce().getNonce();
                // send paymentMethodNonce to your server

                Log.d("onActivityResult", "nonce: " + paymentMethodNonce);

                requestCheckout(paymentMethodNonce);

            } else if (resultCode == Activity.RESULT_CANCELED) {
                // canceled
            } else {
                // an error occurred, checked the returned exception
                Exception exception = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                Log.e("PayPalDropIn", exception.toString());
            }

            return;
        }

        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        if(resultCode==RESULT_OK){
            fragmentManager = getActivity().getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            CoursesListFragment courseFragment = new CoursesListFragment();
            fragmentTransaction.replace(R.id.fragment, courseFragment);
            fragmentTransaction.commit();
        }

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void buyCourse() {
        DropInRequest dropInRequest = new DropInRequest()
                .clientToken(APIUtility.clientToken);
        startActivityForResult(dropInRequest.getIntent(getContext()), DROP_IN_REQUEST);
    }

    public void requestCheckout(String nonce) {

        String jsonString = "{\"nonce\": \"" +  nonce + "\", \"course_id\": \"" + course.getId() + "\"}";

        Log.d("requestCheckout", "jsonstring: " + jsonString);

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    APIUtility.PAYPAL_CHECKOUT_URL,
                    new JSONObject(jsonString),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            progressDialog.dismiss();
                            Log.e("PayPalService", response.toString());
                            fragmentComunication.setFragment(1);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Log.e("PayPalService", error.toString());
                        }
                    }
            ) {

                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params = new HashMap<>();

                    params.put("Content-Type", "application/json");

                    if (APIUtility.ACCESS_TOKEN != null) {

                        params.put("Authorization", "Bearer " + APIUtility.ACCESS_TOKEN);
                    }

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(jsonObjectRequest);

        } catch (JSONException error) {
            progressDialog.dismiss();
            Log.e("PayPalService", error.toString());
        }
    }
}