package com.ics.server.service;

import com.ics.common.dto.*;
import com.ics.engine.core.ScheduleGenerator;
import com.ics.server.mapper.EntityMapper;
import com.ics.server.repo.*;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {
  private final CourseRepository courseRepo;
  private final SectionRepository sectionRepo;
  private final EntityMapper mapper;
  private final ScheduleGenerator generator = new ScheduleGenerator();

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
    
    var lists = new ArrayList<List<Section>>();
    lists.addAll(byCourse.values());

    int topK = req.topK() == null ? 5 : req.topK();
    var top = generator.generate(lists, topK);
    var out = new ArrayList<ScheduleResult>();
    
    for (var combo : top) {
      out.add(new ScheduleResult(
          combo,
          combo.size(),
          List.of("prototype score: sections count")
      ));
    }
    
    return out;
  }
}



