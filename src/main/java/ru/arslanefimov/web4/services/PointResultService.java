package ru.arslanefimov.web4.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.arslanefimov.web4.exceptions.AppError;
import ru.arslanefimov.web4.model.PointEntity;
import ru.arslanefimov.web4.repositories.PointResultRepository;

import java.text.DecimalFormat;
import java.util.List;

@Service
public class PointResultService {

    private final PointResultRepository pointResultRepository;


    @Autowired
    public PointResultService(PointResultRepository pointResultRepository){
        this.pointResultRepository = pointResultRepository;
    }


    @Transactional
    public List<PointEntity> getResults(Long userId){
        return pointResultRepository.getAllByUserId(userId);
    }

    @Transactional
    public void addPointsResultToDB(PointEntity results, Long userId){
        results.setUserId(userId);
        pointResultRepository.save(results);
    }

    @Transactional
    public int deleteResults(Long userId){
        int countDelete = pointResultRepository.deleteAllByUserId(userId);
        if(countDelete>0){
            return countDelete;
        }

        return new AppError(500, "Warn, failed to delete results").getStatus();
    }

}
