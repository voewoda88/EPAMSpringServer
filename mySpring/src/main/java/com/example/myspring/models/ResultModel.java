package com.example.myspring.models;

import jakarta.persistence.*;

@Entity
@Table(name = "results")
public class ResultModel {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "average_value")
    private float averageValue;
    @Column(name = "median_value")
    private float medianValue;

    @OneToOne
    @JoinColumn(name = "params_id")
    private ParamsModel paramsModel;

    public float getAverageValue() {
        return averageValue;
    }

    public void setAverageValue(float averageValue) {
        this.averageValue = averageValue;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public float getMedianValue() {
        return medianValue;
    }

    public void setMedianValue(float medianValue) {
        this.medianValue = medianValue;
    }

//    public ParamsModel getParamsModel() {
//        return paramsModel;
//    }
//
//    public void setParamsModel(ParamsModel paramsModel) {
//        this.paramsModel = paramsModel;
//    }
}
