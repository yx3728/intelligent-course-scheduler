-- Expanded seed data for 10,000+ combinations
-- This migration adds substantial data to test large-scale schedule generation

-- Add more buildings (15 total)
INSERT INTO buildings(id, name, campus, lat, lon) VALUES
 ('B3', 'Engineering Hall', 'NYC', 40.7128, -74.0060),
 ('B4', 'Arts Building', 'NYC', 40.7130, -74.0058),
 ('B5', 'Library', 'NYC', 40.7125, -74.0065),
 ('B6', 'Business School', 'NYC', 40.7135, -74.0055),
 ('B7', 'Chemistry Lab', 'NYC', 40.7120, -74.0070),
 ('B8', 'Physics Building', 'NYC', 40.7132, -74.0062),
 ('B9', 'Student Center', 'NYC', 40.7123, -74.0068),
 ('B10', 'Gymnasium', 'NYC', 40.7140, -74.0050),
 ('B11', 'Dormitory A', 'NYC', 40.7115, -74.0075),
 ('B12', 'Dormitory B', 'NYC', 40.7145, -74.0045),
 ('B13', 'Auditorium', 'NYC', 40.7126, -74.0066),
 ('B14', 'Computer Lab', 'NYC', 40.7138, -74.0052),
 ('B15', 'Language Center', 'NYC', 40.7118, -74.0072);

-- Add more courses (20 total courses)
INSERT INTO courses(id, code, title, credits) VALUES
 ('C3', 'CS201', 'Data Structures', 4),
 ('C4', 'CS301', 'Algorithms', 4),
 ('C5', 'CS401', 'Database Systems', 4),
 ('C6', 'MATH101', 'Calculus I', 4),
 ('C7', 'MATH102', 'Calculus II', 4),
 ('C8', 'MATH301', 'Linear Algebra', 3),
 ('C9', 'PHYS101', 'Physics I', 4),
 ('C10', 'PHYS102', 'Physics II', 4),
 ('C11', 'CHEM101', 'Chemistry I', 4),
 ('C12', 'BIO101', 'Biology I', 4),
 ('C13', 'ENG101', 'English Composition', 3),
 ('C14', 'HIST101', 'World History', 3),
 ('C15', 'ECON101', 'Microeconomics', 3),
 ('C16', 'PSYCH101', 'Psychology', 3),
 ('C17', 'PHIL101', 'Philosophy', 3),
 ('C18', 'ART101', 'Art History', 3),
 ('C19', 'MUS101', 'Music Theory', 2),
 ('C20', 'STAT201', 'Statistics', 3);

-- Add building edges (walking times between buildings)
INSERT INTO edges(fromBuildingId, toBuildingId, walkMinutes) VALUES
 ('B1', 'B3', 5), ('B3', 'B1', 5),
 ('B1', 'B4', 7), ('B4', 'B1', 7),
 ('B1', 'B5', 3), ('B5', 'B1', 3),
 ('B2', 'B7', 4), ('B7', 'B2', 4),
 ('B2', 'B8', 6), ('B8', 'B2', 6),
 ('B3', 'B14', 2), ('B14', 'B3', 2),
 ('B4', 'B15', 8), ('B15', 'B4', 8),
 ('B5', 'B9', 4), ('B9', 'B5', 4),
 ('B6', 'B10', 10), ('B10', 'B6', 10),
 ('B7', 'B8', 3), ('B8', 'B7', 3),
 ('B9', 'B11', 6), ('B11', 'B9', 6),
 ('B9', 'B12', 7), ('B12', 'B9', 7),
 ('B13', 'B1', 5), ('B1', 'B13', 5),
 ('B14', 'B5', 8), ('B5', 'B14', 8),
 ('B15', 'B4', 6), ('B4', 'B15', 6);

-- Generate sections for existing courses (C1 and C2) - add 5 more sections each
INSERT INTO sections(id, course_id, building_id, instructor) VALUES
 ('S5', 'C1', 'B3', 'Turing'),
 ('S6', 'C1', 'B14', 'Hopper'),
 ('S7', 'C1', 'B5', 'Knuth'),
 ('S8', 'C1', 'B13', 'Dijkstra'),
 ('S9', 'C1', 'B1', 'Lovelace'),
 ('S10', 'C2', 'B3', 'Euler'),
 ('S11', 'C2', 'B4', 'Gauss'),
 ('S12', 'C2', 'B5', 'Newton'),
 ('S13', 'C2', 'B6', 'Leibniz'),
 ('S14', 'C2', 'B7', 'Pascal');

-- Generate sections for new courses (C3-C20)
-- CS201 Data Structures (8 sections)
INSERT INTO sections(id, course_id, building_id, instructor) VALUES
 ('S15', 'C3', 'B3', 'Thompson'),
 ('S16', 'C3', 'B14', 'Ritchie'),
 ('S17', 'C3', 'B1', 'Stroustrup'),
 ('S18', 'C3', 'B5', 'Gosling'),
 ('S19', 'C3', 'B13', 'Torvalds'),
 ('S20', 'C3', 'B3', 'Berners-Lee'),
 ('S21', 'C3', 'B14', 'Carmack'),
 ('S22', 'C3', 'B1', 'Gates');

-- CS301 Algorithms (7 sections)
INSERT INTO sections(id, course_id, building_id, instructor) VALUES
 ('S23', 'C4', 'B3', 'Cormen'),
 ('S24', 'C4', 'B14', 'Leiserson'),
 ('S25', 'C4', 'B5', 'Rivest'),
 ('S26', 'C4', 'B13', 'Stein'),
 ('S27', 'C4', 'B3', 'Sedgewick'),
 ('S28', 'C4', 'B14', 'Wayne'),
 ('S29', 'C4', 'B1', 'Kleinberg');

-- CS401 Database Systems (6 sections)
INSERT INTO sections(id, course_id, building_id, instructor) VALUES
 ('S30', 'C5', 'B3', 'Codd'),
 ('S31', 'C5', 'B14', 'Date'),
 ('S32', 'C5', 'B5', 'Stonebraker'),
 ('S33', 'C5', 'B13', 'Gray'),
 ('S34', 'C5', 'B3', 'Chamberlin'),
 ('S35', 'C5', 'B14', 'Boyce');

-- MATH101 Calculus I (9 sections)
INSERT INTO sections(id, course_id, building_id, instructor) VALUES
 ('S36', 'C6', 'B2', 'Newton'),
 ('S37', 'C6', 'B8', 'Leibniz'),
 ('S38', 'C6', 'B1', 'Euler'),
 ('S39', 'C6', 'B3', 'Gauss'),
 ('S40', 'C6', 'B5', 'Cauchy'),
 ('S41', 'C6', 'B2', 'Riemann'),
 ('S42', 'C6', 'B8', 'Weierstrass'),
 ('S43', 'C6', 'B1', 'Cantor'),
 ('S44', 'C6', 'B3', 'Hilbert');

-- MATH102 Calculus II (8 sections)
INSERT INTO sections(id, course_id, building_id, instructor) VALUES
 ('S45', 'C7', 'B2', 'Fourier'),
 ('S46', 'C7', 'B8', 'Laplace'),
 ('S47', 'C7', 'B1', 'Taylor'),
 ('S48', 'C7', 'B3', 'Maclaurin'),
 ('S49', 'C7', 'B5', 'Lagrange'),
 ('S50', 'C7', 'B2', 'Bernoulli'),
 ('S51', 'C7', 'B8', 'Euler2'),
 ('S52', 'C7', 'B1', 'Gauss2');

-- MATH301 Linear Algebra (7 sections)
INSERT INTO sections(id, course_id, building_id, instructor) VALUES
 ('S53', 'C8', 'B2', 'Jordan'),
 ('S54', 'C8', 'B8', 'Schmidt'),
 ('S55', 'C8', 'B1', 'Gram'),
 ('S56', 'C8', 'B3', 'Householder'),
 ('S57', 'C8', 'B5', 'Givens'),
 ('S58', 'C8', 'B2', 'QR'),
 ('S59', 'C8', 'B8', 'SVD');

-- PHYS101 Physics I (8 sections)
INSERT INTO sections(id, course_id, building_id, instructor) VALUES
 ('S60', 'C9', 'B7', 'Einstein'),
 ('S61', 'C9', 'B8', 'Newton2'),
 ('S62', 'C9', 'B2', 'Maxwell'),
 ('S63', 'C9', 'B7', 'Planck'),
 ('S64', 'C9', 'B8', 'Bohr'),
 ('S65', 'C9', 'B2', 'Heisenberg'),
 ('S66', 'C9', 'B7', 'Schrodinger'),
 ('S67', 'C9', 'B8', 'Feynman');

-- PHYS102 Physics II (7 sections)
INSERT INTO sections(id, course_id, building_id, instructor) VALUES
 ('S68', 'C10', 'B7', 'Curie'),
 ('S69', 'C10', 'B8', 'Tesla'),
 ('S70', 'C10', 'B2', 'Faraday'),
 ('S71', 'C10', 'B7', 'Ampere'),
 ('S72', 'C10', 'B8', 'Ohm'),
 ('S73', 'C10', 'B2', 'Volta'),
 ('S74', 'C10', 'B7', 'Coulomb');

-- CHEM101 Chemistry I (6 sections)
INSERT INTO sections(id, course_id, building_id, instructor) VALUES
 ('S75', 'C11', 'B7', 'Mendeleev'),
 ('S76', 'C11', 'B2', 'Dalton'),
 ('S77', 'C11', 'B7', 'Avogadro'),
 ('S78', 'C11', 'B2', 'Lavoisier'),
 ('S79', 'C11', 'B7', 'Boyle'),
 ('S80', 'C11', 'B2', 'Gay-Lussac');

-- BIO101 Biology I (8 sections)
INSERT INTO sections(id, course_id, building_id, instructor) VALUES
 ('S81', 'C12', 'B2', 'Darwin'),
 ('S82', 'C12', 'B7', 'Mendel'),
 ('S83', 'C12', 'B2', 'Watson'),
 ('S84', 'C12', 'B7', 'Crick'),
 ('S85', 'C12', 'B2', 'Franklin'),
 ('S86', 'C12', 'B7', 'Pasteur'),
 ('S87', 'C12', 'B2', 'Koch'),
 ('S88', 'C12', 'B7', 'Fleming');

-- ENG101 English Composition (10 sections)
INSERT INTO sections(id, course_id, building_id, instructor) VALUES
 ('S89', 'C13', 'B4', 'Shakespeare'),
 ('S90', 'C13', 'B5', 'Dickens'),
 ('S91', 'C13', 'B4', 'Austen'),
 ('S92', 'C13', 'B5', 'Orwell'),
 ('S93', 'C13', 'B4', 'Hemingway'),
 ('S94', 'C13', 'B5', 'Woolf'),
 ('S95', 'C13', 'B4', 'Fitzgerald'),
 ('S96', 'C13', 'B5', 'Joyce'),
 ('S97', 'C13', 'B4', 'Tolkien'),
 ('S98', 'C13', 'B5', 'Rowling');

-- HIST101 World History (9 sections)
INSERT INTO sections(id, course_id, building_id, instructor) VALUES
 ('S99', 'C14', 'B4', 'Herodotus'),
 ('S100', 'C14', 'B5', 'Thucydides'),
 ('S101', 'C14', 'B4', 'Gibbon'),
 ('S102', 'C14', 'B5', 'Toynbee'),
 ('S103', 'C14', 'B4', 'Braudel'),
 ('S104', 'C14', 'B5', 'Hobsbawm'),
 ('S105', 'C14', 'B4', 'Foucault'),
 ('S106', 'C14', 'B5', 'Spengler'),
 ('S107', 'C14', 'B4', 'Huntington');

-- ECON101 Microeconomics (7 sections)
INSERT INTO sections(id, course_id, building_id, instructor) VALUES
 ('S108', 'C15', 'B6', 'Smith'),
 ('S109', 'C15', 'B1', 'Keynes'),
 ('S110', 'C15', 'B6', 'Friedman'),
 ('S111', 'C15', 'B1', 'Hayek'),
 ('S112', 'C15', 'B6', 'Marx'),
 ('S113', 'C15', 'B1', 'Ricardo'),
 ('S114', 'C15', 'B6', 'Schumpeter');

-- PSYCH101 Psychology (8 sections)
INSERT INTO sections(id, course_id, building_id, instructor) VALUES
 ('S115', 'C16', 'B2', 'Freud'),
 ('S116', 'C16', 'B7', 'Jung'),
 ('S117', 'C16', 'B2', 'Skinner'),
 ('S118', 'C16', 'B7', 'Pavlov'),
 ('S119', 'C16', 'B2', 'Piaget'),
 ('S120', 'C16', 'B7', 'Maslow'),
 ('S121', 'C16', 'B2', 'Rogers'),
 ('S122', 'C16', 'B7', 'Bandura');

-- PHIL101 Philosophy (6 sections)
INSERT INTO sections(id, course_id, building_id, instructor) VALUES
 ('S123', 'C17', 'B4', 'Plato'),
 ('S124', 'C17', 'B5', 'Aristotle'),
 ('S125', 'C17', 'B4', 'Kant'),
 ('S126', 'C17', 'B5', 'Hegel'),
 ('S127', 'C17', 'B4', 'Nietzsche'),
 ('S128', 'C17', 'B5', 'Sartre');

-- ART101 Art History (7 sections)
INSERT INTO sections(id, course_id, building_id, instructor) VALUES
 ('S129', 'C18', 'B4', 'DaVinci'),
 ('S130', 'C18', 'B15', 'Michelangelo'),
 ('S131', 'C18', 'B4', 'Picasso'),
 ('S132', 'C18', 'B15', 'VanGogh'),
 ('S133', 'C18', 'B4', 'Monet'),
 ('S134', 'C18', 'B15', 'Warhol'),
 ('S135', 'C18', 'B4', 'Dali');

-- MUS101 Music Theory (5 sections)
INSERT INTO sections(id, course_id, building_id, instructor) VALUES
 ('S136', 'C19', 'B4', 'Bach'),
 ('S137', 'C19', 'B15', 'Mozart'),
 ('S138', 'C19', 'B4', 'Beethoven'),
 ('S139', 'C19', 'B15', 'Chopin'),
 ('S140', 'C19', 'B4', 'Debussy');

-- STAT201 Statistics (8 sections)
INSERT INTO sections(id, course_id, building_id, instructor) VALUES
 ('S141', 'C20', 'B2', 'Fisher'),
 ('S142', 'C20', 'B8', 'Pearson'),
 ('S143', 'C20', 'B2', 'Gauss3'),
 ('S144', 'C20', 'B8', 'Bayes'),
 ('S145', 'C20', 'B2', 'Laplace2'),
 ('S146', 'C20', 'B8', 'Poisson'),
 ('S147', 'C20', 'B2', 'Bernoulli2'),
 ('S148', 'C20', 'B8', 'Chebyshev');

-- Generate meeting times for all sections
-- Meeting times are distributed across the week to create various combinations
-- Each section has 2-3 meetings per week

-- Sections S1-S14 (CS101 and MATH201 expanded)
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time) VALUES
 ('S5', 'MON', '08:00', '09:15'), ('S5', 'WED', '08:00', '09:15'), ('S5', 'FRI', '08:00', '09:15'),
 ('S6', 'TUE', '14:00', '15:15'), ('S6', 'THU', '14:00', '15:15'),
 ('S7', 'MON', '11:00', '12:15'), ('S7', 'WED', '11:00', '12:15'),
 ('S8', 'TUE', '09:00', '10:15'), ('S8', 'THU', '09:00', '10:15'),
 ('S9', 'MON', '13:00', '14:15'), ('S9', 'WED', '13:00', '14:15'),
 ('S10', 'MON', '15:00', '16:15'), ('S10', 'WED', '15:00', '16:15'),
 ('S11', 'TUE', '10:00', '11:15'), ('S11', 'THU', '10:00', '11:15'),
 ('S12', 'MON', '16:00', '17:15'), ('S12', 'WED', '16:00', '17:15'),
 ('S13', 'TUE', '12:00', '13:15'), ('S13', 'THU', '12:00', '13:15'),
 ('S14', 'FRI', '09:00', '10:15'), ('S14', 'FRI', '10:30', '11:45');

-- CS201 sections (S15-S22)
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time) VALUES
 ('S15', 'MON', '09:00', '10:30'), ('S15', 'WED', '09:00', '10:30'),
 ('S16', 'TUE', '09:00', '10:30'), ('S16', 'THU', '09:00', '10:30'),
 ('S17', 'MON', '11:00', '12:30'), ('S17', 'WED', '11:00', '12:30'),
 ('S18', 'TUE', '11:00', '12:30'), ('S18', 'THU', '11:00', '12:30'),
 ('S19', 'MON', '13:00', '14:30'), ('S19', 'WED', '13:00', '14:30'),
 ('S20', 'TUE', '13:00', '14:30'), ('S20', 'THU', '13:00', '14:30'),
 ('S21', 'MON', '15:00', '16:30'), ('S21', 'WED', '15:00', '16:30'),
 ('S22', 'TUE', '15:00', '16:30'), ('S22', 'THU', '15:00', '16:30');

-- CS301 sections (S23-S29)
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time) VALUES
 ('S23', 'MON', '08:30', '10:00'), ('S23', 'WED', '08:30', '10:00'), ('S23', 'FRI', '08:30', '10:00'),
 ('S24', 'TUE', '08:30', '10:00'), ('S24', 'THU', '08:30', '10:00'),
 ('S25', 'MON', '10:30', '12:00'), ('S25', 'WED', '10:30', '12:00'),
 ('S26', 'TUE', '10:30', '12:00'), ('S26', 'THU', '10:30', '12:00'),
 ('S27', 'MON', '12:30', '14:00'), ('S27', 'WED', '12:30', '14:00'),
 ('S28', 'TUE', '12:30', '14:00'), ('S28', 'THU', '12:30', '14:00'),
 ('S29', 'MON', '14:30', '16:00'), ('S29', 'WED', '14:30', '16:00');

-- CS401 sections (S30-S35)
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time) VALUES
 ('S30', 'MON', '09:00', '10:15'), ('S30', 'WED', '09:00', '10:15'), ('S30', 'FRI', '09:00', '10:15'),
 ('S31', 'TUE', '09:00', '10:15'), ('S31', 'THU', '09:00', '10:15'),
 ('S32', 'MON', '11:00', '12:15'), ('S32', 'WED', '11:00', '12:15'),
 ('S33', 'TUE', '11:00', '12:15'), ('S33', 'THU', '11:00', '12:15'),
 ('S34', 'MON', '13:00', '14:15'), ('S34', 'WED', '13:00', '14:15'),
 ('S35', 'TUE', '13:00', '14:15'), ('S35', 'THU', '13:00', '14:15');

-- MATH101 sections (S36-S44) - 9 sections
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time) VALUES
 ('S36', 'MON', '08:00', '09:15'), ('S36', 'WED', '08:00', '09:15'), ('S36', 'FRI', '08:00', '09:15'),
 ('S37', 'TUE', '08:00', '09:15'), ('S37', 'THU', '08:00', '09:15'),
 ('S38', 'MON', '09:30', '10:45'), ('S38', 'WED', '09:30', '10:45'),
 ('S39', 'TUE', '09:30', '10:45'), ('S39', 'THU', '09:30', '10:45'),
 ('S40', 'MON', '11:00', '12:15'), ('S40', 'WED', '11:00', '12:15'),
 ('S41', 'TUE', '11:00', '12:15'), ('S41', 'THU', '11:00', '12:15'),
 ('S42', 'MON', '12:30', '13:45'), ('S42', 'WED', '12:30', '13:45'),
 ('S43', 'TUE', '12:30', '13:45'), ('S43', 'THU', '12:30', '13:45'),
 ('S44', 'MON', '14:00', '15:15'), ('S44', 'WED', '14:00', '15:15');

-- MATH102 sections (S45-S52) - 8 sections
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time) VALUES
 ('S45', 'MON', '08:00', '09:15'), ('S45', 'WED', '08:00', '09:15'),
 ('S46', 'TUE', '08:00', '09:15'), ('S46', 'THU', '08:00', '09:15'),
 ('S47', 'MON', '09:30', '10:45'), ('S47', 'WED', '09:30', '10:45'),
 ('S48', 'TUE', '09:30', '10:45'), ('S48', 'THU', '09:30', '10:45'),
 ('S49', 'MON', '11:00', '12:15'), ('S49', 'WED', '11:00', '12:15'),
 ('S50', 'TUE', '11:00', '12:15'), ('S50', 'THU', '11:00', '12:15'),
 ('S51', 'MON', '13:00', '14:15'), ('S51', 'WED', '13:00', '14:15'),
 ('S52', 'TUE', '13:00', '14:15'), ('S52', 'THU', '13:00', '14:15');

-- MATH301 sections (S53-S59) - 7 sections
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time) VALUES
 ('S53', 'MON', '10:00', '11:15'), ('S53', 'WED', '10:00', '11:15'), ('S53', 'FRI', '10:00', '11:15'),
 ('S54', 'TUE', '10:00', '11:15'), ('S54', 'THU', '10:00', '11:15'),
 ('S55', 'MON', '12:00', '13:15'), ('S55', 'WED', '12:00', '13:15'),
 ('S56', 'TUE', '12:00', '13:15'), ('S56', 'THU', '12:00', '13:15'),
 ('S57', 'MON', '14:00', '15:15'), ('S57', 'WED', '14:00', '15:15'),
 ('S58', 'TUE', '14:00', '15:15'), ('S58', 'THU', '14:00', '15:15'),
 ('S59', 'MON', '16:00', '17:15'), ('S59', 'WED', '16:00', '17:15');

-- PHYS101 sections (S60-S67) - 8 sections
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time) VALUES
 ('S60', 'MON', '08:00', '09:30'), ('S60', 'WED', '08:00', '09:30'),
 ('S61', 'TUE', '08:00', '09:30'), ('S61', 'THU', '08:00', '09:30'),
 ('S62', 'MON', '10:00', '11:30'), ('S62', 'WED', '10:00', '11:30'),
 ('S63', 'TUE', '10:00', '11:30'), ('S63', 'THU', '10:00', '11:30'),
 ('S64', 'MON', '12:00', '13:30'), ('S64', 'WED', '12:00', '13:30'),
 ('S65', 'TUE', '12:00', '13:30'), ('S65', 'THU', '12:00', '13:30'),
 ('S66', 'MON', '14:00', '15:30'), ('S66', 'WED', '14:00', '15:30'),
 ('S67', 'TUE', '14:00', '15:30'), ('S67', 'THU', '14:00', '15:30');

-- PHYS102 sections (S70-S74) - 7 sections (S68-S69 already used above)
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time) VALUES
 ('S68', 'MON', '09:00', '10:30'), ('S68', 'WED', '09:00', '10:30'),
 ('S69', 'TUE', '09:00', '10:30'), ('S69', 'THU', '09:00', '10:30'),
 ('S70', 'MON', '11:00', '12:30'), ('S70', 'WED', '11:00', '12:30'),
 ('S71', 'TUE', '11:00', '12:30'), ('S71', 'THU', '11:00', '12:30'),
 ('S72', 'MON', '13:00', '14:30'), ('S72', 'WED', '13:00', '14:30'),
 ('S73', 'TUE', '13:00', '14:30'), ('S73', 'THU', '13:00', '14:30'),
 ('S74', 'MON', '15:00', '16:30'), ('S74', 'WED', '15:00', '16:30');

-- CHEM101 sections (S75-S80) - 6 sections
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time) VALUES
 ('S75', 'MON', '08:00', '09:15'), ('S75', 'WED', '08:00', '09:15'), ('S75', 'FRI', '08:00', '09:15'),
 ('S76', 'TUE', '08:00', '09:15'), ('S76', 'THU', '08:00', '09:15'),
 ('S77', 'MON', '10:00', '11:15'), ('S77', 'WED', '10:00', '11:15'),
 ('S78', 'TUE', '10:00', '11:15'), ('S78', 'THU', '10:00', '11:15'),
 ('S79', 'MON', '12:00', '13:15'), ('S79', 'WED', '12:00', '13:15'),
 ('S80', 'TUE', '12:00', '13:15'), ('S80', 'THU', '12:00', '13:15');

-- BIO101 sections (S81-S88) - 8 sections
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time) VALUES
 ('S81', 'MON', '09:00', '10:15'), ('S81', 'WED', '09:00', '10:15'),
 ('S82', 'TUE', '09:00', '10:15'), ('S82', 'THU', '09:00', '10:15'),
 ('S83', 'MON', '11:00', '12:15'), ('S83', 'WED', '11:00', '12:15'),
 ('S84', 'TUE', '11:00', '12:15'), ('S84', 'THU', '11:00', '12:15'),
 ('S85', 'MON', '13:00', '14:15'), ('S85', 'WED', '13:00', '14:15'),
 ('S86', 'TUE', '13:00', '14:15'), ('S86', 'THU', '13:00', '14:15'),
 ('S87', 'MON', '15:00', '16:15'), ('S87', 'WED', '15:00', '16:15'),
 ('S88', 'TUE', '15:00', '16:15'), ('S88', 'THU', '15:00', '16:15');

-- ENG101 sections (S89-S98) - 10 sections
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time) VALUES
 ('S89', 'MON', '08:00', '09:15'), ('S89', 'WED', '08:00', '09:15'),
 ('S90', 'TUE', '08:00', '09:15'), ('S90', 'THU', '08:00', '09:15'),
 ('S91', 'MON', '09:30', '10:45'), ('S91', 'WED', '09:30', '10:45'),
 ('S92', 'TUE', '09:30', '10:45'), ('S92', 'THU', '09:30', '10:45'),
 ('S93', 'MON', '11:00', '12:15'), ('S93', 'WED', '11:00', '12:15'),
 ('S94', 'TUE', '11:00', '12:15'), ('S94', 'THU', '11:00', '12:15'),
 ('S95', 'MON', '12:30', '13:45'), ('S95', 'WED', '12:30', '13:45'),
 ('S96', 'TUE', '12:30', '13:45'), ('S96', 'THU', '12:30', '13:45'),
 ('S97', 'MON', '14:00', '15:15'), ('S97', 'WED', '14:00', '15:15'),
 ('S98', 'TUE', '14:00', '15:15'), ('S98', 'THU', '14:00', '15:15');

-- HIST101 sections (S99-S107) - 9 sections
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time) VALUES
 ('S99', 'MON', '08:00', '09:15'), ('S99', 'WED', '08:00', '09:15'), ('S99', 'FRI', '08:00', '09:15'),
 ('S100', 'TUE', '08:00', '09:15'), ('S100', 'THU', '08:00', '09:15'),
 ('S101', 'MON', '10:00', '11:15'), ('S101', 'WED', '10:00', '11:15'),
 ('S102', 'TUE', '10:00', '11:15'), ('S102', 'THU', '10:00', '11:15'),
 ('S103', 'MON', '12:00', '13:15'), ('S103', 'WED', '12:00', '13:15'),
 ('S104', 'TUE', '12:00', '13:15'), ('S104', 'THU', '12:00', '13:15'),
 ('S105', 'MON', '14:00', '15:15'), ('S105', 'WED', '14:00', '15:15'),
 ('S106', 'TUE', '14:00', '15:15'), ('S106', 'THU', '14:00', '15:15'),
 ('S107', 'MON', '16:00', '17:15'), ('S107', 'WED', '16:00', '17:15');

-- ECON101 sections (S108-S114) - 7 sections
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time) VALUES
 ('S108', 'MON', '09:00', '10:15'), ('S108', 'WED', '09:00', '10:15'),
 ('S109', 'TUE', '09:00', '10:15'), ('S109', 'THU', '09:00', '10:15'),
 ('S110', 'MON', '11:00', '12:15'), ('S110', 'WED', '11:00', '12:15'),
 ('S111', 'TUE', '11:00', '12:15'), ('S111', 'THU', '11:00', '12:15'),
 ('S112', 'MON', '13:00', '14:15'), ('S112', 'WED', '13:00', '14:15'),
 ('S113', 'TUE', '13:00', '14:15'), ('S113', 'THU', '13:00', '14:15'),
 ('S114', 'MON', '15:00', '16:15'), ('S114', 'WED', '15:00', '16:15');

-- PSYCH101 sections (S115-S122) - 8 sections
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time) VALUES
 ('S115', 'MON', '08:00', '09:15'), ('S115', 'WED', '08:00', '09:15'),
 ('S116', 'TUE', '08:00', '09:15'), ('S116', 'THU', '08:00', '09:15'),
 ('S117', 'MON', '10:00', '11:15'), ('S117', 'WED', '10:00', '11:15'),
 ('S118', 'TUE', '10:00', '11:15'), ('S118', 'THU', '10:00', '11:15'),
 ('S119', 'MON', '12:00', '13:15'), ('S119', 'WED', '12:00', '13:15'),
 ('S120', 'TUE', '12:00', '13:15'), ('S120', 'THU', '12:00', '13:15'),
 ('S121', 'MON', '14:00', '15:15'), ('S121', 'WED', '14:00', '15:15'),
 ('S122', 'TUE', '14:00', '15:15'), ('S122', 'THU', '14:00', '15:15');

-- PHIL101 sections (S123-S128) - 6 sections
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time) VALUES
 ('S123', 'MON', '09:00', '10:15'), ('S123', 'WED', '09:00', '10:15'), ('S123', 'FRI', '09:00', '10:15'),
 ('S124', 'TUE', '09:00', '10:15'), ('S124', 'THU', '09:00', '10:15'),
 ('S125', 'MON', '11:00', '12:15'), ('S125', 'WED', '11:00', '12:15'),
 ('S126', 'TUE', '11:00', '12:15'), ('S126', 'THU', '11:00', '12:15'),
 ('S127', 'MON', '13:00', '14:15'), ('S127', 'WED', '13:00', '14:15'),
 ('S128', 'TUE', '13:00', '14:15'), ('S128', 'THU', '13:00', '14:15');

-- ART101 sections (S129-S135) - 7 sections
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time) VALUES
 ('S129', 'MON', '10:00', '11:15'), ('S129', 'WED', '10:00', '11:15'),
 ('S130', 'TUE', '10:00', '11:15'), ('S130', 'THU', '10:00', '11:15'),
 ('S131', 'MON', '12:00', '13:15'), ('S131', 'WED', '12:00', '13:15'),
 ('S132', 'TUE', '12:00', '13:15'), ('S132', 'THU', '12:00', '13:15'),
 ('S133', 'MON', '14:00', '15:15'), ('S133', 'WED', '14:00', '15:15'),
 ('S134', 'TUE', '14:00', '15:15'), ('S134', 'THU', '14:00', '15:15'),
 ('S135', 'MON', '16:00', '17:15'), ('S135', 'WED', '16:00', '17:15');

-- MUS101 sections (S136-S140) - 5 sections
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time) VALUES
 ('S136', 'MON', '09:00', '10:00'), ('S136', 'WED', '09:00', '10:00'), ('S136', 'FRI', '09:00', '10:00'),
 ('S137', 'TUE', '09:00', '10:00'), ('S137', 'THU', '09:00', '10:00'),
 ('S138', 'MON', '11:00', '12:00'), ('S138', 'WED', '11:00', '12:00'),
 ('S139', 'TUE', '11:00', '12:00'), ('S139', 'THU', '11:00', '12:00'),
 ('S140', 'MON', '13:00', '14:00'), ('S140', 'WED', '13:00', '14:00');

-- STAT201 sections (S141-S148) - 8 sections
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time) VALUES
 ('S141', 'MON', '08:00', '09:15'), ('S141', 'WED', '08:00', '09:15'),
 ('S142', 'TUE', '08:00', '09:15'), ('S142', 'THU', '08:00', '09:15'),
 ('S143', 'MON', '10:00', '11:15'), ('S143', 'WED', '10:00', '11:15'),
 ('S144', 'TUE', '10:00', '11:15'), ('S144', 'THU', '10:00', '11:15'),
 ('S145', 'MON', '12:00', '13:15'), ('S145', 'WED', '12:00', '13:15'),
 ('S146', 'TUE', '12:00', '13:15'), ('S146', 'THU', '12:00', '13:15'),
 ('S147', 'MON', '14:00', '15:15'), ('S147', 'WED', '14:00', '15:15'),
 ('S148', 'TUE', '14:00', '15:15'), ('S148', 'THU', '14:00', '15:15');

