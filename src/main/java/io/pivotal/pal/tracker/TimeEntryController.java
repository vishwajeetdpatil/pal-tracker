package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TimeEntryController {
    private TimeEntryRepository timeEntriesRepo;
    private final DistributionSummary timeEntrySummary;
    private final Counter actionCounter;

    public TimeEntryController(
            TimeEntryRepository timeEntriesRepo,
            MeterRegistry meterRegistry
    ) {
        this.timeEntriesRepo = timeEntriesRepo;
        timeEntrySummary = meterRegistry.summary("timeEntry.summary");
        actionCounter = meterRegistry.counter("timeEntry.actionCounter");
    }

/*    public TimeEntryController(TimeEntryRepository timeEntriesRepo) {
        this.timeEntriesRepo = timeEntriesRepo;
    }*/
    @PostMapping(value = "/time-entries")
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry t = timeEntriesRepo.create(timeEntryToCreate);
        actionCounter.increment();
        timeEntrySummary.record(timeEntriesRepo.list().size());
        ResponseEntity r = new ResponseEntity(t, HttpStatus.CREATED);
        return r;
    }

    @GetMapping(value = "/time-entries/{timeEntryId}")
    public ResponseEntity<TimeEntry> read(@PathVariable long timeEntryId) {
        TimeEntry t = timeEntriesRepo.find(timeEntryId);
        if (t != null) {
            actionCounter.increment();
            return new ResponseEntity<>(t, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping(value = "/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {
        List list = timeEntriesRepo.list();
        actionCounter.increment();
        ResponseEntity<List<TimeEntry>> r = new ResponseEntity<>(list,HttpStatus.OK);
        return r;
    }

    @PutMapping(value =  "/time-entries/{timeEntryId}")
    public ResponseEntity update(@PathVariable long timeEntryId,@RequestBody TimeEntry expected) {
        TimeEntry t = timeEntriesRepo.update(timeEntryId,expected);
        if(t==null) return new ResponseEntity(t,HttpStatus.NOT_FOUND);
        actionCounter.increment();
        ResponseEntity r = new ResponseEntity(t,HttpStatus.OK);
        return r;
    }

    @DeleteMapping(value =  "/time-entries/{timeEntryId}")
    public ResponseEntity delete(@PathVariable long timeEntryId) {
        actionCounter.increment();
        timeEntriesRepo.delete(timeEntryId);
        ResponseEntity r = new ResponseEntity(HttpStatus.NO_CONTENT);
        return r;
    }
}
