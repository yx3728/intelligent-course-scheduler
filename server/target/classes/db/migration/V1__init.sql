CREATE TABLE buildings (
  id VARCHAR(64) PRIMARY KEY,
  name VARCHAR(255),
  campus VARCHAR(64),
  lat DOUBLE,
  lon DOUBLE
);

CREATE TABLE courses (
  id VARCHAR(64) PRIMARY KEY,
  code VARCHAR(64) UNIQUE,
  title VARCHAR(255),
  credits INT
);

CREATE TABLE sections (
  id VARCHAR(64) PRIMARY KEY,
  course_id VARCHAR(64),
  building_id VARCHAR(64),
  instructor VARCHAR(255),
  CONSTRAINT fk_course FOREIGN KEY (course_id) REFERENCES courses(id),
  CONSTRAINT fk_building FOREIGN KEY (building_id) REFERENCES buildings(id)
);

CREATE TABLE section_meetings (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  section_id VARCHAR(64),
  day_of_week VARCHAR(8),
  start_time VARCHAR(8),
  end_time VARCHAR(8),
  CONSTRAINT fk_section FOREIGN KEY (section_id) REFERENCES sections(id)
);

CREATE TABLE edges (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  fromBuildingId VARCHAR(64),
  toBuildingId VARCHAR(64),
  walkMinutes INT
);

-- Sample data (2 buildings, 2 courses with 2 sections each)
INSERT INTO buildings(id, name, campus, lat, lon) VALUES
 ('B1', 'Main Hall', 'NYC', 0, 0),
 ('B2', 'Science Ctr', 'NYC', 0, 0);

INSERT INTO courses(id, code, title, credits) VALUES
 ('C1', 'CS101', 'Intro to CS', 4),
 ('C2', 'MATH201', 'Discrete Math', 4);

INSERT INTO sections(id, course_id, building_id, instructor) VALUES
 ('S1', 'C1', 'B1', 'Ada'),
 ('S2', 'C1', 'B2', 'Grace'),
 ('S3', 'C2', 'B1', 'Alonzo'),
 ('S4', 'C2', 'B2', 'Edsger');

INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time) VALUES
 ('S1', 'MON', '09:00', '10:15'),
 ('S1', 'WED', '09:00', '10:15'),
 ('S2', 'TUE', '11:00', '12:15'),
 ('S2', 'THU', '11:00', '12:15'),
 ('S3', 'MON', '10:30', '11:45'),
 ('S3', 'WED', '10:30', '11:45'),
 ('S4', 'TUE', '09:00', '10:15'),
 ('S4', 'THU', '09:00', '10:15');

INSERT INTO edges(fromBuildingId, toBuildingId, walkMinutes) VALUES
 ('B1', 'B2', 8),
 ('B2', 'B1', 8);





