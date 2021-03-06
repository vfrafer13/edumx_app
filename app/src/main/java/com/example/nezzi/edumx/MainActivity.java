package com.example.nezzi.edumx;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.nezzi.edumx.fragments.CourseDetailFragment;
import com.example.nezzi.edumx.fragments.CoursesListFragment;
import com.example.nezzi.edumx.fragments.MainFragment;
import com.example.nezzi.edumx.fragments.MyAccountFragment;
import com.example.nezzi.edumx.fragments.MyCourseDetailFragment;
import com.example.nezzi.edumx.fragments.MyCoursesListFragment;
import com.example.nezzi.edumx.interfaces.FragmentComunication;
import com.example.nezzi.edumx.models.Course;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements FragmentComunication {

    private TextView mTextMessage;
    private CoursesListFragment coursesListFragment;
    private CourseDetailFragment courseDetailFragment;
    private MyCourseDetailFragment myCourseDetailFragment;
    private MyCoursesListFragment myCourseListFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setFragment(0);
                    return true;
                case R.id.navigation_dashboard:
                    setFragment(1);
                    return true;
                case R.id.navigation_notifications:
                    setFragment(2);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        initializeFragmentsActivities();

        Intent intent = getIntent();

        setFirstFragment(intent);

        requestToken();
    }

    private void initializeFragmentsActivities () {
        coursesListFragment = new CoursesListFragment();
        courseDetailFragment = new CourseDetailFragment();
        myCourseListFragment = new MyCoursesListFragment();
        myCourseDetailFragment = new MyCourseDetailFragment();
    }

    private void setFirstFragment(Intent intent) {
        Bundle extras = intent.getExtras();

        int pos=0;
        if (extras != null) {
            pos= intent.getIntExtra("main_pos", 0);
        }
        setFragment(pos);
    }

    public void setFragment(int position) {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        switch (position) {
            case 0:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                MainFragment mainCategories = new MainFragment();
                fragmentTransaction.replace(R.id.fragment, mainCategories);
                fragmentTransaction.commit();
                break;
            case 1:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                MyCoursesListFragment userCourses = new MyCoursesListFragment();
                fragmentTransaction.replace(R.id.fragment, userCourses);
                fragmentTransaction.commit();
                break;
            case 2:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                MyAccountFragment myAccount= new MyAccountFragment();
                fragmentTransaction.replace(R.id.fragment, myAccount);
                fragmentTransaction.commit();
                break;
        }
    }


    @Override
    public void sendCategory(int id) {
        coursesListFragment = (CoursesListFragment) getSupportFragmentManager().
                findFragmentById(R.id.frag_courses_list);


        if(coursesListFragment!=null && findViewById(R.id.fragment)==null){
            coursesListFragment.setCategoryId(id);
        }else{
            coursesListFragment = new CoursesListFragment();
            Bundle bundleSend=new Bundle();
            coursesListFragment.setCategoryId(id);
            bundleSend.putSerializable("app_object", id);
            coursesListFragment.setArguments(bundleSend);

            getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragment,coursesListFragment).
                    addToBackStack(null).commit();
        }

    }

    @Override
    public void sendCourse(Course course) {
        courseDetailFragment = (CourseDetailFragment) getSupportFragmentManager().
                findFragmentById(R.id.frag_courses_detail);


        if(courseDetailFragment!=null && findViewById(R.id.fragment)==null){
            courseDetailFragment.setCourseData(course);
        }else{
            courseDetailFragment = new CourseDetailFragment();
            Bundle bundleSend=new Bundle();
            bundleSend.putSerializable("app_object", course);
            courseDetailFragment.setArguments(bundleSend);

            getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragment,courseDetailFragment).
                    addToBackStack(null).commit();
        }
    }

    public void sendMyCourse(Course course) {
        myCourseDetailFragment = (MyCourseDetailFragment) getSupportFragmentManager().
                findFragmentById(R.id.frag_mycourses_detail);


        if(myCourseDetailFragment!=null && findViewById(R.id.fragment)==null){
            myCourseDetailFragment.setCourseData(course);
        }else{
            myCourseDetailFragment = new MyCourseDetailFragment();
            Bundle bundleSend=new Bundle();
            bundleSend.putSerializable("app_object", course);
            myCourseDetailFragment.setArguments(bundleSend);

            getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragment,myCourseDetailFragment).
                    addToBackStack(null).commit();
        }
    }

    public void requestToken() {
        final String accessToken = APIUtility.getAccesToken(this);

        if (APIUtility.clientToken != null && !APIUtility.clientToken.isEmpty()) {
            return;
        }

        Log.d("CourseDetailFragment", "Requesting paypal token");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                APIUtility.PAYPAL_TOKEN_URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("CourseDetailFragment", "onResponse: " + response.toString());
                        try {
                            APIUtility.clientToken = response.getString("clientToken");
                        } catch (JSONException error) {
                            Log.e("PayPalService", error.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("PayPalService", error.toString());
                    }
                }
        ) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();

                if (accessToken != null) {
                    params.put("Authorization", "Bearer " + accessToken);
                }

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }
}
