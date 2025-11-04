package com.ics.common.dto;

import java.util.List;

public record GenerateRequest(
    List<String> courseCodes,
    UserPrefs prefs,
    Integer topK
) {}





