package com.ttknp.understandspringbootcachecrudh2.service;

import com.ttknp.understandspringbootcachecrudh2.model.Faculty;

public interface FacultyService {
    Iterable<Faculty> getAllFaculty();
    Faculty getFacultyById(long id);
    Faculty getFacultyByName(String name);
    Boolean editFaculty(Faculty faculty, long id);
    Boolean saveFaculty(Faculty faculty);
    void removeFacultyById(long id);
    void clearCache();
}
