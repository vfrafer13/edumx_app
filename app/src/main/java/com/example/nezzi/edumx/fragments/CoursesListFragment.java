package com.example.nezzi.edumx.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.nezzi.edumx.APIUtility;
import com.example.nezzi.edumx.R;
import com.example.nezzi.edumx.adapters.CategoryAdapter;
import com.example.nezzi.edumx.adapters.CourseAdapter;
import com.example.nezzi.edumx.interfaces.FragmentComunication;
import com.example.nezzi.edumx.models.Course;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CoursesListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CoursesListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CoursesListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView mList;

    Activity activity;

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<Course> courseList;
    private CourseAdapter adapter;
    private int categoryId;
    private String URL;

    FragmentComunication fragmentComunication;

    private OnFragmentInteractionListener mListener;
    Handler handler;

    public CoursesListFragment(){
        handler = new Handler();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CoursesListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CoursesListFragment newInstance(String param1, String param2) {
        CoursesListFragment fragment = new CoursesListFragment();
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
        View view = inflater.inflate(R.layout.fragment_courses_list, container, false);

        courseList = new ArrayList<>();
        mList = view.findViewById(R.id.course_list);

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());

        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);

        URL = APIUtility.COURSE_CAT_URL + String.valueOf(categoryId);

        adapter = new CourseAdapter(courseList);
        getData();

        mList.setAdapter(adapter);


        getActivity().setTitle("Categoría - ");
        TextView title = view.findViewById(R.id.txt_ListTitle);
        title.setText("Cursos Disponibles");

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentComunication.sendCourse(courseList.get(mList.getChildAdapterPosition(view)));
            }
        });
        return view;
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

    public void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        jsonArrayRequest(URL, progressDialog);
    }

    public void jsonArrayRequest(String url, final ProgressDialog progressDialog) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(response.getJSONObject(i)));

                        Course course = new Course();
                        course.setId(jsonObject.getInt("id"));
                        course.setName(jsonObject.getString("name"));
                        course.setDescription(jsonObject.getString("description"));
                        course.setPrice(jsonObject.getDouble("price"));
                        course.setInstructor(jsonObject.getInt("instructor"));
                        course.setDuration(jsonObject.getInt("duration"));
                        course.setRequirements(jsonObject.getString("requirements"));
                        course.setRating(jsonObject.getDouble("rating"));
                        course.setTopics(jsonObject.getString("topics"));

                        courseList.add(course);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);
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

    public void setCategoryId (int id) {
        categoryId = id;
    }
}
