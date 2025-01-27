package com.ttknp.understandspringbootcachecrudh2.controller;

import com.ttknp.understandspringbootcachecrudh2.model.Faculty;
import com.ttknp.understandspringbootcachecrudh2.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RestController
@RequestMapping(value = "{base-rest-prefix}")
public class FacultyControl {

    private FacultyService facultyService;

    @Autowired
    public FacultyControl(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @RequestMapping(value = "/faculties",method = RequestMethod.GET)
    // ** Response annotation can specify next modifier ***
    /*
    private @ResponseBody @ResponseStatus(code = HttpStatus.ACCEPTED) Iterable<Faculty> reads() {
        return facultyService.getAllFaculty();
    }
    */
    @ResponseBody
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    private Map<String,Object> reads() {
        return Map.of("data",facultyService.getAllFaculty(),"status", "success");
    }

    @RequestMapping(value = "/faculty",method = RequestMethod.GET,params = "id")
    private ResponseEntity<Map<String,Object>> read(@RequestParam("id") long id) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(Map.of("data", facultyService.getFacultyById(id),"status", "success"));
    }

    @RequestMapping(value = "/faculty",method = RequestMethod.DELETE,params = "id")
    private ResponseEntity<Map<String,Object>> delete(@RequestParam("id") long id) {
        facultyService.removeFacultyById(id);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(Map.of("message", "remove process is done","status", "success"));
    }

    @RequestMapping(value = "/faculty",method = RequestMethod.PUT,params = "id")
    private ResponseEntity<Map<String,Object>> update(@RequestBody Faculty faculty,@RequestParam("id") long id) {
        boolean isEdited = facultyService.editFaculty(faculty,id);
        String status = "done";
        String message = "faculty edit failed";
        if(isEdited){
            message = "faculty edited";
            status = "success";
        }
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(Map.of("message", message,"status", status));
    }

    @RequestMapping(value = "/faculty",method = RequestMethod.POST)
    private ResponseEntity<Map<String,Object>> save(@RequestBody Faculty faculty) {
        boolean isEdited = facultyService.saveFaculty(faculty);
        String status = "done";
        String message = "faculty add failed";
        if(isEdited){
            message = "faculty added";
            status = "success";
        }
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(Map.of("message", message,"status", status));
    }


    @RequestMapping(value = "/faculty/clear",method = RequestMethod.DELETE)
    private ResponseEntity<Map<String,String>> clearCaches() {
        facultyService.clearCache();
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(Map.of("message", "clear caches","status", "success"));
    }
}
