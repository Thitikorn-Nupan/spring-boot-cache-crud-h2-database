package com.ttknp.understandspringbootcachecrudh2.dto;

import com.ttknp.understandspringbootcachecrudh2.logger.Log;
import com.ttknp.understandspringbootcachecrudh2.model.Faculty;
import com.ttknp.understandspringbootcachecrudh2.repository.FacultyRepo;
import com.ttknp.understandspringbootcachecrudh2.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

/**
 *** Enables the cache mechanism. in the Spring Boot application by using the annotation @EnableCaching.
 The auto-configuration enables caching and setup a CacheManager, if there is no already defined instance of CacheManager.
 It scans for a specific provider, and when it does not find, it creates an in-memory cache using concurrent HashMap.
 The @EnableCaching annotation triggers a post-processor that inspects every Spring bean for the presence of caching annotations
 on public methods. If such an annotation is found, a proxy is automatically created to intercept the method call and handle the caching behavior accordingly.
 */
@EnableCaching // ** if you don't specify all cache won work
@Service
public class FacultyDTO implements FacultyService {

    private final Log log;
    private final FacultyRepo facultyRepo;
    private final CacheManager cacheManager;

    @Autowired
    public FacultyDTO(FacultyRepo facultyRepo,CacheManager cacheManager) {
        log = new Log(FacultyDTO.class);
        this.facultyRepo = facultyRepo;
        this.cacheManager = cacheManager;
    }

    // ** add this method to the cache
    // The Spring Framework ** manages the requests and responses of the method to the cache that is specified in the annotation attribute.
    // The @Cacheable annotation contains more options.
    // It defines a cache for a method's return value.
    // We can also specify the key attribute of the annotation that uniquely identifies each entry in the cache.
    // If we do not specify the key, Spring uses the default mechanism to create the key
    @Cacheable(value = "findAll")
    @Override
    public Iterable<Faculty> getAllFaculty() {
        simulateDelay3s();
        return  facultyRepo.findAll();
    }

    /// *** We can also apply a condition in the annotation by using the condition attribute. ** When we apply the condition in the annotation, it is called conditional caching.
    // This case i do not want
    @Cacheable(value = "findById" , key = "#id") // work with remove
    @Override
    public Faculty getFacultyById(long id) {
        simulateDelay3s();
        return facultyRepo.findById(id).orElse(new Faculty());
    }

    @Override
    public Faculty getFacultyByName(String name) {
        return null;
    }

    // only void method work!
    // In the Spring Framework, the @CacheEvict annotation is used to remove one or more entries from a cache.
    // When a method annotated with @CacheEvict is called, Spring will remove the cached data associated with
    // The specified cache name and key (or keys) along with the method execution.
    // The key parameter specifies cache entry (key) to be evicted. It’s the Id of the faculty passed as a parameter.
    // So when the update or deleteById method is called, Spring will remove the corresponding entry from the "findById"
    // after the method is executed. If you want to evict cache before method invocation, set beforeInvocation to true.
    // *** key have to same key you wanna remove cached
    // after method worked  findById key(some id) cached will remove that means simulateDelay3s() method work again
    @CacheEvict(value = "findById" , key = "#id")
    @Override
    public void removeFacultyById(long id) {
        Boolean isRemoved = facultyRepo.findById(id).map(faculty -> {
            facultyRepo.delete(faculty);
            return true;
        }).orElse(false);
        if (isRemoved) {
            log.application.debug("clear cached and remove {} faculty successfully",id);
        } else {
            log.application.debug("clear cached and remove {} faculty failed",id);
        }
    }

    @CacheEvict(value = "findById" , key = "#id") // or "#faculty.id"
    @Override
    public Boolean editFaculty(Faculty faculty, long id) {
        Faculty oldFaculty = facultyRepo.findById(id).orElse(null);
        if (oldFaculty != null) {
            faculty.setId(oldFaculty.getId());
            facultyRepo.save(faculty);
            return true;
        }
        return false;
    }

    /// ** When you added faculty , but you have cached after get all faculties
    /// You get all again its new faculty won't show
    /// Because it gets all from cached , and this how to fix
    @Override
    public Boolean saveFaculty(Faculty faculty) {
        // clear all cache
        clearCache();
        return facultyRepo.save(faculty).getId() != null;
    }


    /// Note! you can use @CacheEvict annotation for remove , if you have method without key and parameter ** will work fine but key will not remove
    /// @CacheEvict(value = {"findAll","findById"}) // doesn't need code for remove cached if you use @CacheEvict annotation
    @Override
    public void clearCache() {
        // If you have to clear all cached no care keys use CacheManager class **
        cacheManager.getCacheNames()
                .stream()
                .forEach(cacheName -> cacheManager.getCache(cacheName).clear());
        log.application.debug("clear all cached successfully");
    }

    /// For testing spring boot cached  // If you don't do this you won't understand
    private void simulateDelay3s() {
        try {
            Thread.sleep(3000L);
            log.application.debug("*********** simulate delay 3 sec. then get data");
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

}
