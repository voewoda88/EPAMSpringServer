package com.example.myspring.service;

import com.example.myspring.models.ParamsModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;

@Service
@Scope("singleton")
public class ExceptionService {
    ExceptionService() {}

    public void checkExceptions(ParamsModel params) throws InvalidParameterException, IllegalArgumentException
    {
        if (params.getNum1() < 0) {
            throw new InvalidParameterException("num1 less then zero");
        }

        if (params.getNum2() < 0) {
            throw new InvalidParameterException("num2 less then zero");
        }

        if (params.getNum3() < 0) {
            throw new InvalidParameterException("num3 less then zero");
        }

        if (params.getNum4() < 0){
            throw new InvalidParameterException("num4 less then zero");
        }

        if (Float.isInfinite(params.getNum1()) || Float.isInfinite(params.getNum2()) || Float.isInfinite(params.getNum3()) || Float.isInfinite(params.getNum4()))
        {
            throw new IllegalArgumentException("One of the numbers is Infinity");
        }
    }
}
