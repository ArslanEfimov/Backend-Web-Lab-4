package ru.arslanefimov.web4.controllers;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.arslanefimov.web4.dto.requests.PointResultRequestDTO;
import ru.arslanefimov.web4.services.ResultTableService;


@RestController
@RequestMapping("api/main")
public class ResultTableController {


    private final ResultTableService resultTableService;



    @Autowired
    public ResultTableController(ResultTableService resultTableService){
        this.resultTableService = resultTableService;
    }

    @PostMapping("addDot")
    public ResponseEntity<?> addPointToTable(@RequestBody PointResultRequestDTO requestDTO, HttpServletRequest servletRequest){
            return resultTableService.addPointToTable(requestDTO, servletRequest);
        }

        @GetMapping("/getDots")
        public ResponseEntity<?> getAllResults(HttpServletRequest servletRequest){
                return resultTableService.getAllResults(servletRequest);
            }

        @DeleteMapping("/deleteDots")
        public ResponseEntity<?> deleteDots(HttpServletRequest servletRequest){
            return resultTableService.deleteDots(servletRequest);
        }

    }


