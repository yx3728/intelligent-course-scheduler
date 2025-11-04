package com.ics.server.repo;

import com.ics.server.entity.SectionEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<SectionEntity, String> {
  List<SectionEntity> findByCourseId(String courseId);
}





