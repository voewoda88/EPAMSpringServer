package com.example.myspring;

import com.example.myspring.cache.CacheModel;
import com.example.myspring.controllers.ParamsController;
import com.example.myspring.models.BulkResultModel;
import com.example.myspring.models.ParamsModel;
import com.example.myspring.models.ResultModel;
import com.example.myspring.service.AccessCounterService;
import com.example.myspring.service.ExceptionService;
import com.example.myspring.service.ParamsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.InvalidParameterException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class MySpringApplicationTests {

    @InjectMocks
    private ParamsController paramsController;

    @Mock
    private AccessCounterService accessCounterService;

    @Mock
    private ParamsService paramsService;

    @Mock
    private Map<String, String> mockCache;

    @Mock
    private ParamsModel paramsMock;

    @InjectMocks
    private ExceptionService exceptionService;

    @Test
    public void testGreeting() {
        ParamsModel params = new ParamsModel();
        params.setNum1(1);
        params.setNum2(2);
        params.setNum3(3);
        params.setNum4(4);

        ResultModel expectedResult = new ResultModel();
        expectedResult.setAverageValue(2.5f);
        expectedResult.setMedianValue(2.5f);

        when(paramsService.getResult(params)).thenReturn(expectedResult);

        ResultModel result = paramsController.greeting(params);

        assertEquals(expectedResult, result);
        verify(accessCounterService).add();
        verify(paramsService).getResult(params);
    }

//    @Test
//    public void testProcessBulkParams() {
//        ParamsModel params1 = new ParamsModel();
//        params1.setNum1(1);
//        params1.setNum2(2);
//        params1.setNum3(3);
//        params1.setNum4(4);
//
//        ParamsModel params2 = new ParamsModel();
//        params2.setNum1(5);
//        params2.setNum2(6);
//        params2.setNum3(7);
//        params2.setNum4(8);
//
//        List<ParamsModel> paramsList = Arrays.asList(params1, params2);
//
//        BulkResultModel expectedBulkResult = new BulkResultModel();
//        ResultModel expectedResult1 = new ResultModel();
//        expectedResult1.setAverageValue(2.5f);
//        expectedResult1.setMedianValue(2.5f);
//        ResultModel expectedResult2 = new ResultModel();
//        expectedResult2.setAverageValue(6.5f);
//        expectedResult2.setMedianValue(6.5f);
//        List<ResultModel> expectedResults = Arrays.asList(expectedResult1, expectedResult2);
//        expectedBulkResult.setResultList(expectedResults);
//
//        when(paramsService.processBulkParams(paramsList)).thenReturn(expectedBulkResult);
//
//        BulkResultModel result = paramsController.processBulkParams(paramsList);
//
//        assertEquals(expectedBulkResult, result);
//        verify(accessCounterService).add();
//        verify(paramsService).processBulkParams(paramsList);
//    }
    @Test
    public void testCorrectNumbers() throws InvalidParameterException, IllegalArgumentException {
        ParamsModel params = new ParamsModel();
        params.setNum1(1);
        params.setNum2(2);
        params.setNum3(3);
        params.setNum4(4);

        exceptionService.checkExceptions(params);
        assertDoesNotThrow(() -> exceptionService.checkExceptions(params));
    }

    @Test
    public void testIncorrectNumbers() {
        float randomValue = new Random().nextFloat() * -100;
        int num = new Random().nextInt(4) + 1;
        switch(num)
        {
            case 1:
                when(paramsMock.getNum1()).thenReturn(randomValue);
                break;
            case 2:
                when(paramsMock.getNum2()).thenReturn(randomValue);
                break;
            case 3:
                when(paramsMock.getNum3()).thenReturn(randomValue);
                break;
            case 4:
                when(paramsMock.getNum4()).thenReturn(randomValue);
                break;
        }

        assertThrows(InvalidParameterException.class, () -> exceptionService.checkExceptions(paramsMock));
    }
}


