package com.example.myspring.controllers;

import com.example.myspring.models.BulkResultModel;
import com.example.myspring.models.ExtendedResultModel;
import com.example.myspring.models.ResultModel;
import com.example.myspring.repositories.ParamsRepository;
import com.example.myspring.repositories.ParamsServiceRepository;
import com.example.myspring.service.*;
import com.example.myspring.models.ParamsModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class ParamsController {
    private final Logger logger = LoggerFactory.getLogger(ParamsController.class);
    @Autowired
    private AccessCounterService counter;
    @Autowired
    private ParamsService operationsService;
    @Autowired
    private ExtendedResultService extendedResultService;
    @Autowired
    private ParamsServiceRepository paramsServiceRepository;
    @Autowired
    private AsyncParamsService asyncParamsService;

    @GetMapping("/nums")
    public ResultModel greeting(@ModelAttribute ParamsModel params) {
        counter.add();
        return operationsService.getResult(params);
    }

    @GetMapping("/extendedResult")
    public ExtendedResultModel extendedResult(@ModelAttribute ParamsModel params)
    {
        counter.add();
        return extendedResultService.getExtendedResult(params);
    }

    @PostMapping("/bulk")
    public BulkResultModel processBulkParams(@RequestBody List<ParamsModel> paramsList) {
        counter.add();
        return  operationsService.processBulkParams(paramsList);
    }

    @RequestMapping(value = {"/params/{id}", "/async/result/{id}"})
    public ParamsModel getParamsFromDataBase(@PathVariable Integer id) {
        counter.add();
        return paramsServiceRepository.read(id);
    }

    @GetMapping("/getAllRecords")
    public List<ParamsModel> getAllRecordsFromDataBase() {
        counter.add();
        return paramsServiceRepository.getAll();
    }

    @DeleteMapping("/deleteRecord/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        counter.add();
        paramsServiceRepository.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/async/result")
    public ResponseEntity<String> getResultAsync(@RequestBody ParamsModel params) {
        CompletableFuture<ResultModel> future = asyncParamsService.getResultAsync(params);
        // Возвращаем HTTP статус 202 Accepted и заголовок с URL для проверки статуса
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(params.getId())
                .toUri());
        return new ResponseEntity<>(headers, HttpStatus.ACCEPTED);
    }

}
