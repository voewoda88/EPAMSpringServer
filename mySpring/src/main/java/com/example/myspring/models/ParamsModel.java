package com.example.myspring.models;

import jakarta.persistence.*;
import com.example.myspring.models.ResultModel;

import java.util.Objects;
@Entity
@Table(name = "nums")
public class ParamsModel {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "num1")
    private float num1;
    @Column(name = "num2")
    private float num2;
    @Column(name = "num3")
    private float num3;
    @Column(name = "num4")
    private float num4;

    @OneToOne(mappedBy = "paramsModel")
    private ResultModel resultModel;

    public ParamsModel() {}

    public Integer getId() {
        return id;
    }

    public float getNum1() {
        return num1;
    }

    public void setNum1(float num1) {
        this.num1 = num1;
    }

    public float getNum2() {
        return num2;
    }

    public void setNum2(float num2) {
        this.num2 = num2;
    }

    public float getNum3() {
        return num3;
    }

    public void setNum3(float num3) {
        this.num3 = num3;
    }

    public float getNum4() {
        return num4;
    }

    public void setNum4(float num4) {
        this.num4 = num4;
    }

//    public ResultModel getResultModel() {
//        return resultModel;
//    }
//
//    public void setResultModel(ResultModel resultModel) {
//        this.resultModel = resultModel;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParamsModel)) return false;
        ParamsModel params = (ParamsModel) o;
        return Float.compare(params.num1, num1) == 0 &&
                Float.compare(params.num2, num2) == 0 &&
                Float.compare(params.num3, num3) == 0 &&
                Float.compare(params.num4, num4) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(num1, num2, num3, num4);
    }

}
