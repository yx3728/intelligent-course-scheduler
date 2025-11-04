package com.ics.common.dto;

import java.util.List;

public record ScheduleResult(
    List<Section> sections,
    double score,
    List<String> explanations
) {}





