package com.ics.common.dto;

import java.util.List;

public record Section(
    String id,
    String courseId,
    String buildingId,
    String instructor,
    List<MeetingTime> meetingTimes
) {}





