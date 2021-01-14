package io.pivotal.pal.tracker;

import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//@Repository
public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    private HashMap<Long , TimeEntry> inMemDB = new HashMap<>();
    private long key = 0;

    public TimeEntry create(TimeEntry timeEntry) {
        //return new TimeEntry();
        long id = ++key;
        timeEntry.setId(id);
        inMemDB.put(id,timeEntry);
        return timeEntry;
    }

    public TimeEntry find(long id) {
        //return new TimeEntry();

        return inMemDB.get(id);
    }

    public List<TimeEntry> list() {
        // new ArrayList<TimeEntry>();
        return new ArrayList(inMemDB.values());
    }

    public TimeEntry update(long id, TimeEntry timeEntry) {
        //return  timeEntry;
        if(inMemDB.get(id) ==null) return null;
        timeEntry.setId(id);
        inMemDB.put(id, timeEntry);
        return timeEntry;
    }

    public void delete(long id) {
        //
        inMemDB.remove(id);
        // return
    }
}
