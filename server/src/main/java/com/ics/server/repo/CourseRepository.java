package com.ics.server.repo;

import com.ics.server.entity.CourseEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<CourseEntity, String> {
  List<CourseEntity> findByCodeIn(List<String> codes);
}





