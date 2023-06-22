package com.example.myspring.service;

import com.example.myspring.cache.CacheModel;
import com.example.myspring.models.BulkResultModel;
import com.example.myspring.models.ExtendedResultModel;
import com.example.myspring.models.ParamsModel;
import com.example.myspring.models.ResultModel;
import com.example.myspring.repositories.ParamsServiceRepository;
import com.example.myspring.repositories.ResultServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Scope("singleton")
public class ParamsService {
    @Autowired
    private CacheModel<ParamsModel, ResultModel> cache;
    @Autowired
    private ExceptionService exception;
    @Autowired
    private ExtendedResultService extendedResultService;
    @Autowired
    private ParamsServiceRepository paramsServiceRepository;
    @Autowired
    private ResultServiceRepository resultServiceRepository;

    public ParamsService(){}

    public ResultModel getResult(ParamsModel params) {
        exception.checkExceptions(params);

        paramsServiceRepository.save(params);

        var cacheValue = cache.Get(params);
        if(cacheValue != null) {
            return cacheValue;
        }

        ResultModel result = new ResultModel();
        double[] arrayOfValues = new double[]{params.getNum1(), params.getNum2(), params.getNum3(), params.getNum4()};
        result.setAverageValue((params.getNum1() + params.getNum2() + params.getNum3() + params.getNum4()) / 4);
        result.setMedianValue((float)Arrays.stream(arrayOfValues).min().getAsDouble() + (float)Arrays.stream(arrayOfValues).max().getAsDouble() / 2);

        cache.push(params, result);

        //resultServiceRepository.saveResult(result);

        return result;
    }

    public BulkResultModel processBulkParams(List<ParamsModel> listOfParams) {
        BulkResultModel bulkResult = new BulkResultModel();
        List<ExtendedResultModel> resultList = new CopyOnWriteArrayList<>();

        listOfParams.parallelStream().forEach(params -> {
            resultList.add(extendedResultService.getExtendedResult(params));
        });

        bulkResult.setResultList(resultList);
        return bulkResult;
    }
}