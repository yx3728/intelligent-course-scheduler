package com.ics.server.api;

import com.ics.common.dto.*;
import com.ics.server.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {
  private final ScheduleService service;

  public ScheduleController(ScheduleService service) {
    this.service = service;
  }

  @PostMapping("/generate")
  public ResponseEntity<List<ScheduleResult>> generate(@Valid @RequestBody GenerateRequest req) {
    return ResponseEntity.ok(service.generate(req));
  }
}





