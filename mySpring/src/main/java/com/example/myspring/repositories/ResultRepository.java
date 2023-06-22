package com.example.myspring.repositories;

import com.example.myspring.models.ParamsModel;
import com.example.myspring.models.ResultModel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ResultRepository extends JpaRepository<ResultModel, Integer> {
}
