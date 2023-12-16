package ru.arslanefimov.web4.services;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.arslanefimov.web4.JWT.JwtTokenUtils;
import ru.arslanefimov.web4.dto.requests.PointResultRequestDTO;
import ru.arslanefimov.web4.dto.responses.PointResultResponseDTO;
import ru.arslanefimov.web4.model.PointEntity;
import ru.arslanefimov.web4.model.UserEntity;
import ru.arslanefimov.web4.util.AreaResultCheck;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResultTableService {

    private final UserService userService;
    private final PointResultService resultService;
    private final JwtTokenUtils jwtTokenUtils;



    @Autowired
    public ResultTableService(PointResultService resultService, UserService userServiceImpl, JwtTokenUtils jwtTokenUtils){
        this.resultService = resultService;
        this.userService = userServiceImpl;
        this.jwtTokenUtils = jwtTokenUtils;
    }

    public ResponseEntity<?> addPointToTable(@RequestBody PointResultRequestDTO requestDTO, HttpServletRequest servletRequest){
        UserEntity user = userService.getUserByLogin(jwtTokenUtils.getUserName(jwtTokenUtils.getToken(servletRequest)));
        PointEntity pointEntity = new PointEntity();
        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        double x = Double.parseDouble(decimalFormat.format(requestDTO.getX()).replace(",", "."));
        double y = 0;
        try {
            y = Double.parseDouble(decimalFormat.format(requestDTO.getY()).replace(",", "."));
        }catch (NumberFormatException ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        double r = requestDTO.getR();
        if(validateDots(x, y, r)) {
            pointEntity.setUserId(user.getId());
            pointEntity.setX(x);
            pointEntity.setY(y);
            pointEntity.setR(r);
            long startExecution = System.nanoTime();
            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd 'Time: 'HH:mm:ss");
            String timeNow = localDateTime.format(formatter);
            pointEntity.setCurrentTime(timeNow);
            long executionEnd = System.nanoTime() - startExecution;
            boolean result = AreaResultCheck.checkResult(requestDTO.getX(), requestDTO.getY(), requestDTO.getR());
            pointEntity.setResult(result);
            pointEntity.setExecutionTime(executionEnd);
            resultService.addPointsResultToDB(pointEntity, user.getId());
            PointResultResponseDTO pointResultResponseDTO = new PointResultResponseDTO(x, y, r,
                    timeNow, executionEnd, result);
            return new ResponseEntity<>(pointResultResponseDTO, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    public ResponseEntity<?> getAllResults(HttpServletRequest servletRequest){
        List<PointEntity> results = resultService.getResults(userService.getUserByLogin(jwtTokenUtils.getUserName(jwtTokenUtils.getToken(servletRequest))).getId());
        List<PointResultResponseDTO> mappedResults = mapToNeedResults(results);
        return ResponseEntity.ok(mappedResults);
    }


    public ResponseEntity<?> deleteDots(HttpServletRequest servletRequest){
        resultService.deleteResults(userService.getUserByLogin(jwtTokenUtils.getUserName(jwtTokenUtils.getToken(servletRequest))).getId());
        return ResponseEntity.ok(HttpStatus.OK);
    }


    private List<PointResultResponseDTO> mapToNeedResults(List<PointEntity> results){
        return results.stream().map(pointEntity -> {
            PointResultResponseDTO dto = new PointResultResponseDTO();
            dto.setX(pointEntity.getX());
            dto.setY(pointEntity.getY());
            dto.setR(pointEntity.getR());
            dto.setCurrentTime(pointEntity.getCurrentTime());
            dto.setExecutionTime(pointEntity.getExecutionTime());
            dto.setResult(pointEntity.isResult());
            return dto;
        }).collect(Collectors.toList());
    }

    private boolean validateDots(double x, double y, double r){
        return (x >= -5 && x <= 3) && (y>=-3 && y <=3) && (r>0);

    }

}
