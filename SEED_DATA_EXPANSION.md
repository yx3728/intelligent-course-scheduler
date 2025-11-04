# Seed Data Expansion Summary

## Overview
The seed data has been expanded from 2 courses with 4 sections to **20 courses with 148 sections**, providing **10,000+ combinations** for schedule generation testing.

## Dataset Statistics

### Buildings
- **Total: 15 buildings**
- Original: 2 (Main Hall, Science Ctr)
- Added: 13 new buildings (Engineering Hall, Arts Building, Library, etc.)

### Courses
- **Total: 20 courses**
- Original: 2 courses (CS101, MATH201)
- Added: 18 new courses across multiple disciplines:
  - Computer Science: CS201, CS301, CS401
  - Mathematics: MATH101, MATH102, MATH301, STAT201
  - Sciences: PHYS101, PHYS102, CHEM101, BIO101
  - Humanities: ENG101, HIST101, PHIL101, ART101, MUS101
  - Social Sciences: ECON101, PSYCH101

### Sections
- **Total: 148 sections** distributed across 20 courses
- Sections per course:
  - CS101: 7 sections (S1, S2 from V1; S5-S9 from V2)
  - MATH201: 7 sections (S3, S4 from V1; S10-S14 from V2)
  - CS201: 8 sections
  - CS301: 7 sections
  - CS401: 6 sections
  - MATH101: 9 sections
  - MATH102: 8 sections
  - MATH301: 7 sections
  - PHYS101: 8 sections
  - PHYS102: 7 sections
  - CHEM101: 6 sections
  - BIO101: 8 sections
  - ENG101: 10 sections
  - HIST101: 9 sections
  - ECON101: 7 sections
  - PSYCH101: 8 sections
  - PHIL101: 6 sections
  - ART101: 7 sections
  - MUS101: 5 sections
  - STAT201: 8 sections

### Meeting Times
- Each section has 2-3 meetings per week
- Times distributed across Monday-Friday
- Various time slots: 8:00-17:15
- Mix of MW, TTh, MWF patterns

### Building Edges
- Expanded network of walking paths between buildings
- Walking times: 2-10 minutes

## Combination Calculations

### Example Combinations

**Selecting 4 courses:**
- ENG101 (10) × MATH101 (9) × CS201 (8) × MATH102 (8) = **5,760 combinations**
- CS301 (7) × CS401 (6) × MATH101 (9) × HIST101 (9) = **3,402 combinations**
- ENG101 (10) × HIST101 (9) × PSYCH101 (8) × ECON101 (7) = **5,040 combinations**

**Selecting 5 courses:**
- CS201 (8) × MATH101 (9) × PHYS101 (8) × CHEM101 (6) × BIO101 (8) = **27,648 combinations**
- ENG101 (10) × MATH101 (9) × CS301 (7) × HIST101 (9) × ECON101 (7) = **39,690 combinations**
- CS401 (6) × MATH102 (8) × PHYS102 (7) × STAT201 (8) × PSYCH101 (8) = **21,504 combinations**

**Selecting 6 courses:**
- CS101 (5) × MATH201 (5) × CS201 (8) × CS301 (7) × MATH101 (9) × PHYS101 (8) = **100,800 combinations**
- ENG101 (10) × HIST101 (9) × PSYCH101 (8) × ECON101 (7) × PHIL101 (6) × ART101 (7) = **211,680 combinations**

**Selecting 7 courses:**
- Average of 7 courses × 6 sections each ≈ **279,936 combinations**

## Testing Scenarios

### Performance Testing
With this expanded dataset, you can test:
1. **Small requests (2-3 courses)**: Fast response times
2. **Medium requests (4-5 courses)**: 5,000-40,000 combinations
3. **Large requests (6-7 courses)**: 100,000+ combinations
4. **Stress testing**: 8+ courses with millions of combinations

### Example API Calls

```bash
# Small test (2 courses, ~25 combinations)
curl -X POST http://localhost:8080/api/schedules/generate \
  -H "Content-Type: application/json" \
  -d '{"courseCodes": ["CS101", "MATH201"], "topK": 10}'

# Medium test (4 courses, ~5,000 combinations)
curl -X POST http://localhost:8080/api/schedules/generate \
  -H "Content-Type: application/json" \
  -d '{"courseCodes": ["ENG101", "MATH101", "CS201", "MATH102"], "topK": 20}'

# Large test (6 courses, ~100,000 combinations)
curl -X POST http://localhost:8080/api/schedules/generate \
  -H "Content-Type: application/json" \
  -d '{"courseCodes": ["CS101", "MATH201", "CS201", "CS301", "MATH101", "PHYS101"], "topK": 50}'
```

## Migration File

The expanded data is in:
- `server/src/main/resources/db/migration/V2__expand_seed_data.sql`

This migration will automatically run when the server starts (if Flyway is enabled).

## Notes

- All section IDs are unique (S1-S148)
- Meeting times are distributed to avoid most conflicts
- Some sections may have time overlaps (realistic scenario)
- Buildings are distributed across campus with realistic coordinates
- Instructors are named after famous scientists/historians

## Verification

To verify the data was loaded correctly:

```sql
-- Count courses
SELECT COUNT(*) FROM courses;  -- Should return 20

-- Count sections
SELECT COUNT(*) FROM sections;  -- Should return 148

-- Count sections per course
SELECT c.code, COUNT(s.id) as section_count 
FROM courses c 
LEFT JOIN sections s ON c.id = s.course_id 
GROUP BY c.code 
ORDER BY section_count DESC;

-- Count meeting times
SELECT COUNT(*) FROM section_meetings;  -- Should return ~300+ meeting times
```

