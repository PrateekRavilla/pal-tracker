package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {
    private HashMap<Long, TimeEntry> timeEntries = new HashMap<>();

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        Long id=timeEntries.size()+1L;
        timeEntry.setId(id);
        System.out.println(id);
        timeEntries.put(id,timeEntry);

        return timeEntry;
    }

    @Override
    public TimeEntry find(long id) {
       return timeEntries.get(id);
    }

    @Override
    public List<TimeEntry> list() {
        return new ArrayList<>(timeEntries.values());
    }



    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {

        TimeEntry updatedTimeEntry = new TimeEntry(id, timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours());

        timeEntries.put(id, updatedTimeEntry);
        return updatedTimeEntry;
        }

    @Override
    public void delete(long id) {
        timeEntries.remove(id);

    }
}

