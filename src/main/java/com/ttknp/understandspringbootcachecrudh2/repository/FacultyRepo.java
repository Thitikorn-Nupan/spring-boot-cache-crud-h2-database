package com.ttknp.understandspringbootcachecrudh2.repository;

import com.ttknp.understandspringbootcachecrudh2.model.Faculty;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FacultyRepo extends CrudRepository<Faculty,Long> {
    // ** behind the sense it find by name
    List<Faculty> findByName(String name);
    boolean removeById(Long id);

}
