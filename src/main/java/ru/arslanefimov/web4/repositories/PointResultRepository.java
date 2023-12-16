package ru.arslanefimov.web4.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.arslanefimov.web4.model.PointEntity;

import java.util.List;

@Repository
public interface PointResultRepository extends JpaRepository<PointEntity, Long> {

    List<PointEntity> getAllByUserId(Long userId);
    int deleteAllByUserId(Long userId);

}
