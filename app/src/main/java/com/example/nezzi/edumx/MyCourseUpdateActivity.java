package com.example.nezzi.edumx;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nezzi.edumx.models.Course;

public class MyCourseUpdateActivity extends AppCompatActivity {

    Course course;
    EditText updateTxtName, updateTxtDesc, updateTxtPrice, updateTxtDuration, updateTxtTopics, updateTxtRequirements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycourse_update);

        updateTxtName= findViewById(R.id.updateTxtName);
        updateTxtDesc= findViewById(R.id.updateTxtDesc);
        updateTxtPrice = findViewById(R.id.updateTxtPrice);
        updateTxtDuration  = findViewById(R.id.updateTxtDuration);
        updateTxtTopics = findViewById(R.id.updateTxtTopics);
        updateTxtRequirements = findViewById(R.id.updateTxtRequirements);

        Intent intent = getIntent();
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            course = (Course) intent.getSerializableExtra("course");
        }

        updateTxtName.setText(course.getName());
        updateTxtDesc.setText(course.getDescription());
        updateTxtPrice.setText(String.valueOf(course.getPrice()));
        updateTxtDuration.setText(String.valueOf(course.getDuration()));
        updateTxtRequirements.setText(course.getRequirements());
        updateTxtTopics.setText(course.getTopics());

        TypedValue typedValueColorPrimaryDark = new TypedValue();
        MyCourseUpdateActivity.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);
        }
        setTitle("Editar Curso");
    }

    public void onClick(View view) {
        updateCourse();
    }

    public void updateCourse() {
        String name = updateTxtName.getText().toString();
        String desc = updateTxtDesc.getText().toString();
        double price = Double.parseDouble(updateTxtPrice.getText().toString());
        int duration = Integer.parseInt(updateTxtDuration.getText().toString());
        String requirements = updateTxtRequirements.getText().toString();
        String topics = updateTxtTopics.getText().toString();

        course = new Course(name, desc, price, duration, requirements, topics);

        //TODO update

        setResult(RESULT_OK, null);
        finish();
    }
}
