package com.example.myspring.service;

import com.example.myspring.cache.CacheModel;
import com.example.myspring.models.ParamsModel;
import com.example.myspring.models.ResultModel;
import com.example.myspring.repositories.ParamsServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@Service
@Scope("singleton")
public class AsyncParamsService {
    @Autowired
    private ExceptionService exception;
    @Autowired
    private ParamsServiceRepository paramsServiceRepository;
    @Autowired
    private CacheModel<ParamsModel, ResultModel> cache;

    public AsyncParamsService(){};
    public CompletableFuture<ResultModel> getResultAsync(ParamsModel params) {
        CompletableFuture<ResultModel> future = new CompletableFuture<>();

        exception.checkExceptions(params);

        paramsServiceRepository.save(params);

        var cacheValue = cache.Get(params);
        if (cacheValue != null) {
            future.complete(cacheValue);
            return future;
        }

        CompletableFuture.supplyAsync(() -> {
            ResultModel result = new ResultModel();
            double[] arrayOfValues = new double[]{params.getNum1(), params.getNum2(), params.getNum3(), params.getNum4()};
            result.setAverageValue((params.getNum1() + params.getNum2() + params.getNum3() + params.getNum4()) / 4);
            result.setMedianValue((float) Arrays.stream(arrayOfValues).min().getAsDouble() + (float) Arrays.stream(arrayOfValues).max().getAsDouble() / 2);

            cache.push(params, result);

            return result;
        }).exceptionally(ex -> {
            future.completeExceptionally(ex);
            return null;
        }).thenAcceptAsync(future::complete);

        return future;
    }
}
