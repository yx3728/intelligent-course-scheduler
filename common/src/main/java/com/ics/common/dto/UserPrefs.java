package com.ics.common.dto;

import java.util.List;

public record UserPrefs(
    Integer minCredits,
    Integer maxCredits,
    List<MeetingTime> bannedWindows,
    Integer maxWalkPerGap
) {}





