package com.example.nezzi.edumx.interfaces;

import com.example.nezzi.edumx.models.Category;
import com.example.nezzi.edumx.models.Course;

/**
 * Created by vanessa on 22/02/18.
 */

public interface FragmentComunication {

    public void sendCourse (Course course);

    public void sendCategory (Category category);

}
