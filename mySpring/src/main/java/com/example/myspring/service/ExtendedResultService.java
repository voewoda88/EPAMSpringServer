package com.example.myspring.service;

import com.example.myspring.cache.CacheModel;
import com.example.myspring.models.ExtendedResultModel;
import com.example.myspring.models.ParamsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.DoubleStream;

@Service
@Scope("singleton")
public class ExtendedResultService {
    @Autowired
    private ExceptionService exception;
    @Autowired
    private CacheModel<ParamsModel, ExtendedResultModel> extendedCache;

    public ExtendedResultService(){};
    public ExtendedResultModel getExtendedResult(ParamsModel params) {
        exception.checkExceptions(params);

        var cacheValue = extendedCache.Get(params);
        if(cacheValue != null) {
            return cacheValue;
        }

        ExtendedResultModel result = new ExtendedResultModel();

        double[] arrayOfValues = new double[]{params.getNum1(), params.getNum2(), params.getNum3(), params.getNum4()};

        result.setAverageValue((params.getNum1() + params.getNum2() + params.getNum3() + params.getNum4()) / 4);
        result.setMedianValue(((float) Arrays.stream(arrayOfValues).min().getAsDouble() + (float)Arrays.stream(arrayOfValues).max().getAsDouble()) / 2);

        result.setMinValue((float)Arrays.stream(arrayOfValues).min().getAsDouble());
        result.setMaxValue((float)Arrays.stream(arrayOfValues).max().getAsDouble());

        result.setSum((float)Arrays.stream(arrayOfValues).sum());

        extendedCache.push(params, result);

        return result;
    }
}
