package com.ttknp.understandspringbootcachecrudh2.cmd;

import com.ttknp.understandspringbootcachecrudh2.logger.Log;
import com.ttknp.understandspringbootcachecrudh2.model.Faculty;
import com.ttknp.understandspringbootcachecrudh2.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ModifiedCMDRunner implements CommandLineRunner {

    private final FacultyService facultyService;
    private final Log log;

    @Autowired
    public ModifiedCMDRunner(FacultyService facultyService) {
        this.facultyService = facultyService;
        log = new Log(ModifiedCMDRunner.class);
    }

    @Override
    public void run(String... args) throws Exception {
        /**
          // test fetching faculties
          // fetchingFacultiesAndCaches();
          // fetchingFacultyAndCaches();
          // fetchingFacultiesAndFacultyAndCaches();
          fetchingFacultyAndEditAndCaches();
        */
        fetchingFacultyAndEditAndCaches();
    }

    public void fetchingFacultiesAndCaches() { // reads and clear cached
        log.application.debug(".... Fetching Faculties and Caches ....");
        log.application.debug(".... faculties (called : {}) store {} ....",1,facultyService.getAllFaculty()); // 3s.
        log.application.debug(".... faculties (called : {}) store {} ....",2,facultyService.getAllFaculty()); // 0s.
        log.application.debug(".... faculties (called : {}) store {} ....",3,facultyService.getAllFaculty()); // 0s.
        facultyService.clearCache();
        log.application.debug(".... faculties (called : {}) store {} ....",4,facultyService.getAllFaculty()); // 3s.
    }

    public void fetchingFacultyAndCaches() { // read/delete and clear cached
        log.application.debug(".... Fetching Faculty and Caches ....");
        log.application.debug(".... faculty (called : {}) store {} ....",1,facultyService.getFacultyById(1)); // 3s.
        log.application.debug(".... faculty (called : {}) store {} ....",2,facultyService.getFacultyById(1)); // 0s.
        log.application.debug(".... faculty (called : {}) store {} ....",3,facultyService.getFacultyById(5)); // 3s.
        log.application.debug(".... faculty (called : {}) store {} ....",4,facultyService.getFacultyById(5)); // 0s.
        facultyService.removeFacultyById(1);
        log.application.debug(".... faculty (called : {}) store {} ....",5,facultyService.getFacultyById(1)); // 3s.
    }

    public void fetchingFacultiesAndFacultyAndCaches() { // read/reads and clear cached
        log.application.debug(".... Fetching Faculty and Caches ....");
        log.application.debug(".... faculty (called : {}) store {} ....",1,facultyService.getFacultyById(2)); // 3s.
        log.application.debug(".... faculties (called : {}) store {} ....",2,facultyService.getAllFaculty()); // 3s.
        log.application.debug(".... faculty (called : {}) store {} ....",3,facultyService.getFacultyById(2)); // 0s.
        log.application.debug(".... faculties (called : {}) store {} ....",4,facultyService.getAllFaculty()); // 0s.
        facultyService.clearCache();
        log.application.debug(".... faculty (called : {}) store {} ....",5,facultyService.getFacultyById(2)); // 3s. // 0s. no remove cached because it do not remove key *** if you use @CacheEvict(value = {"findAll","findById"})
        log.application.debug(".... faculties (called : {}) store {} ....",6,facultyService.getAllFaculty()); // 3s.
    }

    public void fetchingFacultyAndEditAndCaches() { // read/update and clear cached
        log.application.debug(".... Fetching Faculty and Caches ....");
        log.application.debug(".... faculty (called : {}) store {} ....",1,facultyService.getFacultyById(2)); // 3s.
        log.application.debug(".... faculty (called : {}) store {} ....",2,facultyService.getFacultyById(2)); // 0s.
        facultyService.editFaculty(new Faculty(0L,"Faculty of Nursing",8),2);
        log.application.debug("after update and clear cache");  // cached will clear auto because i updated id 2
        log.application.debug(".... faculty (called : {}) store {} ....",3,facultyService.getFacultyById(2)); // 3s.
    }

}
