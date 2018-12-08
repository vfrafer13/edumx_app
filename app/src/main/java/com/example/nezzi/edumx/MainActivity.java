package com.example.nezzi.edumx;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.nezzi.edumx.fragments.CoursesListFragment;
import com.example.nezzi.edumx.fragments.MainFragment;
import com.example.nezzi.edumx.fragments.MyAccountFragment;
import com.example.nezzi.edumx.interfaces.FragmentComunication;
import com.example.nezzi.edumx.models.Category;
import com.example.nezzi.edumx.models.Course;

public class MainActivity extends AppCompatActivity implements FragmentComunication {

    private TextView mTextMessage;
    private CoursesListFragment coursesListFragment;

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

        Intent intent = getIntent();

        setFirstFragment(intent);
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
                /**fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                UserCoursesFragment userCourses = new UserCoursesFragment();
                fragmentTransaction.replace(R.id.fragment, userCourses);
                fragmentTransaction.commit();
                break;**/
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
    public void sendCategory(Category category) {
        coursesListFragment = (CoursesListFragment) this.getSupportFragmentManager().
                findFragmentById(R.id.frag_courses_list);

        coursesListFragment.setCategoryId(category.getId());

        if(coursesListFragment!=null && findViewById(R.id.fragment)==null){
            //setinfo
        }else{
            coursesListFragment=new CoursesListFragment();
            Bundle bundleSend=new Bundle();
            bundleSend.putSerializable("app_object", category);
            coursesListFragment.setArguments(bundleSend);

            getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragment,coursesListFragment).
                    addToBackStack(null).commit();
        }

    }

    @Override
    public void sendCourse(Course course) {

    }
}
