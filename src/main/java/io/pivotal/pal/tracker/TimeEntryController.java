package io.pivotal.pal.tracker;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TimeEntryController {
    private TimeEntryRepository timeEntryRepository;
    private final CounterService counter;
    private final GaugeService gauge;
    private long id;

    public TimeEntryController(TimeEntryRepository timeEntryRepository, CounterService counter, GaugeService gauge) {

        this.timeEntryRepository=timeEntryRepository;
        this.counter = counter;
        this.gauge = gauge;
    }
    @PostMapping("/time-entries")
    public ResponseEntity<TimeEntry>create(@RequestBody TimeEntry timeEntry)
    {

        TimeEntry createdTimeEntry = timeEntryRepository.create(timeEntry);
        counter.increment("TimeEntry Created");
        gauge.submit("timeEntries.count", timeEntryRepository.list().size());
        return new ResponseEntity<>(createdTimeEntry, HttpStatus.CREATED);
    }

    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list()
    {
        counter.increment("TimeEntry.listed");
        return new ResponseEntity<>(timeEntryRepository.list(),HttpStatus.OK);
    }
    @RequestMapping("/time-entries/{id}")
    public ResponseEntity update(@PathVariable  long  id,@RequestBody TimeEntry timeEntry) {

        TimeEntry timedEntry = timeEntryRepository.update(id, timeEntry);
        if (timedEntry != null) {
            counter.increment("TimeEntry.updated");
            return new ResponseEntity<>(timedEntry, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/time-entries/{id}")
    public ResponseEntity delete(@PathVariable long id)
    {
        timeEntryRepository.delete(id);
        counter.increment("TimeEntry.deleted");
        gauge.submit("timeEntries.deleted", timeEntryRepository.list().size());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
  @GetMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable Long id) {
      TimeEntry timeEntry = timeEntryRepository.find(id);
      if (timeEntry != null) {
          counter.increment("TimeEntry.read");
          return new ResponseEntity<>(timeEntry, HttpStatus.OK);
      } else {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
  }}