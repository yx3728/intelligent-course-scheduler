package com.ics.server.service;

import com.ics.common.dto.*;
import com.ics.engine.core.ScheduleGenerator;
import com.ics.server.mapper.EntityMapper;
import com.ics.server.repo.*;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.ics.engine.core.ScheduleScorer;

@Service
public class ScheduleService {
  private final CourseRepository courseRepo;
  private final SectionRepository sectionRepo;
  private final EntityMapper mapper;
  private final ScheduleGenerator generator = new ScheduleGenerator();
  private final ScheduleScorer scorer = new ScheduleScorer();

  public ScheduleService(
      CourseRepository courseRepo,
      SectionRepository sectionRepo,
      EntityMapper mapper
  ) {
    this.courseRepo = courseRepo;
    this.sectionRepo = sectionRepo;
    this.mapper = mapper;
  }

  public List<ScheduleResult> generate(GenerateRequest req) {


      // Load sections grouped by requested course codes
    var courses = courseRepo.findByCodeIn(req.courseCodes());
    Map<String, List<Section>> byCourse = new LinkedHashMap<>();
    
    for (var c : courses) {
      var secs = sectionRepo.findByCourseId(c.getId())
          .stream()
          .map(mapper::toSection)
          .collect(Collectors.toList());
      byCourse.put(c.getId(), secs);
    }

    var lists = new ArrayList<>(byCourse.values());

    int topK = req.topK() == null ? 5 : req.topK();

    var rawSchedules = generator.generate(lists, req.prefs(), topK * 5);
    List<Map.Entry<List<Section>, Double>> scoredList = new ArrayList<>();
    for (var schedule : rawSchedules) {
        double realScore = calculateScoreWithPenalty(schedule, req.prefs());
        scoredList.add(new AbstractMap.SimpleEntry<>(schedule, realScore));
    }

    scoredList.sort((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()));

    //var top = generator.generate(lists,req.prefs(), topK);
    //var out = new ArrayList<ScheduleResult>();

    var out = new ArrayList<ScheduleResult>();
    int limit = Math.min(topK, scoredList.size());

//    for (var combo : top) {
//        double realScore = calculateScoreWithPenalty(combo, req.prefs());
//      out.add(new ScheduleResult(
//          combo,
//          combo.size(),
//          List.of("prototype score: sections count")
//      ));
//    }
      for (int i = 0; i < limit; i++) {
          var entry = scoredList.get(i);
          out.add(new ScheduleResult(
                  entry.getKey(),
                  entry.getValue(), // <--- !!! 修正点：这里传入 realScore，不再是 combo.size() !!!
                  List.of("Score based on time preferences")
          ));
      }

    return out;
  }

    private double calculateScoreWithPenalty(List<Section> schedule, UserPrefs prefs) {
        double score = 100.0;

        score += scorer.score(schedule);

        if (prefs != null && prefs.preferredStartTime() != null) {
            int minHour = prefs.preferredStartTime();

            for (Section s : schedule) {
                for (var mt : s.meetingTimes()) {
                    try {
                        String startStr = mt.start();
                        int hour;
                        if (startStr.contains(":")) {
                            hour = Integer.parseInt(startStr.split(":")[0]);
                        } else {
                            hour = Integer.parseInt(startStr);
                        }

                        if (hour >= minHour) {
                            int diff = Math.abs(hour - minHour);
                            score -= (diff * 0.5);
                        } else {
                            score -= 200.0;
                        }
                    } catch (Exception e) {
                        System.err.println("Failed to parse meeting time: " + e.getMessage());
                    }
                }
            }
        }

        score += (schedule.hashCode() % 100) / 100.0;

        return score;
    }
}



