package com.ttknp.understandspringbootcachecrudh2.repository;

import com.ttknp.understandspringbootcachecrudh2.model.Faculty;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface FacultyRepo extends CrudRepository<Faculty,Long> {
    List<Faculty> findByName(String name);    // ** behind the sense it find by name
    boolean removeById(Long id);
}
