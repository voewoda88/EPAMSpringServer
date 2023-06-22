package com.example.myspring.repositories;

import com.example.myspring.models.ParamsModel;
import com.example.myspring.models.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope("singleton")
public class ResultServiceRepository {
    @Autowired
    private ResultRepository resultRepository;

    ResultServiceRepository(){};

    public void saveResult(ResultModel result) {
        resultRepository.save(result);
    }

}
