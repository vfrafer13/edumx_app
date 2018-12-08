package com.example.nezzi.edumx.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nezzi.edumx.R;
import com.example.nezzi.edumx.models.Course;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyCourseDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyCourseDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyCourseDetailFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView desc;
    Course course;
    TextView nameCourse;

    private OnFragmentInteractionListener mListener;

    public MyCourseDetailFragment() {
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
    public static MyCourseDetailFragment newInstance(String param1, String param2) {
        MyCourseDetailFragment fragment = new MyCourseDetailFragment();
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
        View view = inflater.inflate(R.layout.fragment_mycourse_detail, container, false);
        desc= (TextView) view.findViewById(R.id.txt_courseDesc);
        nameCourse = view.findViewById(R.id.txt_courseName);

        Bundle appObject = getArguments();
        course=null;
        if (appObject != null) {
            course= (Course) appObject.getSerializable("app_object");

            setCourseData(course);

        }

        /*btnAppEdit.setOnClickListener(this);
        btnAppDelete.setOnClickListener(this);*/

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
        /**
        switch (view.getId()){
            case R.id.btnAppEdit: updateCourse();
                break;
            case R.id.btnAppDelete: deleteCourse();
                break;
        }**/

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        if(resultCode==RESULT_OK){
            fragmentManager = getActivity().getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            MyCoursesListFragment myCoursesListFragment = new MyCoursesListFragment();
            fragmentTransaction.replace(R.id.fragment, myCoursesListFragment);
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
}