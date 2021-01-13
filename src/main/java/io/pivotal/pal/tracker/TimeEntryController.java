package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TimeEntryController {
    private TimeEntryRepository timeEntryRepository;
    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }
    @PostMapping(value = "/time-entries")
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry t = timeEntryRepository.create(timeEntryToCreate);
        ResponseEntity r = new ResponseEntity(t, HttpStatus.CREATED);
        return r;
    }

    @GetMapping(value = "/time-entries/{timeEntryId}")
    public ResponseEntity<TimeEntry> read(@PathVariable long timeEntryId) {
        TimeEntry t = timeEntryRepository.find(timeEntryId);
        if(t == null) {
            return new ResponseEntity<>(t,HttpStatus.NOT_FOUND);
        }
        ResponseEntity<TimeEntry> r = new ResponseEntity<>(t,HttpStatus.OK);
        return r;
    }

    @GetMapping(value = "/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {
        List list = timeEntryRepository.list();
        ResponseEntity<List<TimeEntry>> r = new ResponseEntity<>(list,HttpStatus.OK);
        return r;
    }

    @PutMapping(value =  "/time-entries/{timeEntryId}")
    public ResponseEntity update(@PathVariable long timeEntryId,@RequestBody TimeEntry expected) {
        TimeEntry t = timeEntryRepository.update(timeEntryId,expected);
        if(t==null) return new ResponseEntity(t,HttpStatus.NOT_FOUND);
        ResponseEntity r = new ResponseEntity(t,HttpStatus.OK);
        return r;
    }

    @DeleteMapping(value =  "/time-entries/{timeEntryId}")
    public ResponseEntity delete(@PathVariable long timeEntryId) {
        timeEntryRepository.delete(timeEntryId);
        ResponseEntity r = new ResponseEntity(HttpStatus.NO_CONTENT);
        return r;
    }
}
