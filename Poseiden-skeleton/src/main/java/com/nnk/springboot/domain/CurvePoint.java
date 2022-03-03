package com.nnk.springboot.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Objects;


@Entity
@Table(name = "curvepoint")
public class CurvePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id ;
    @NotNull(message = "must not be null")
    private Integer curveId ;
    private Timestamp asOfDate ;
    @Digits(message = "Should be a number", integer = 7, fraction = 2)
    private Double term ;
    @Digits(message = "Should be a number", integer = 7, fraction = 2)
    private Double value ;
    private Timestamp creationDate ;

    public CurvePoint(Timestamp asOfDate, Double term, Double value, Timestamp creationDate) {

        this.asOfDate = asOfDate;
        this.term = term;
        this.value = value;
        this.creationDate = creationDate;
    }
    public CurvePoint(Integer curveId, Double term, Double value) {

        this.curveId = curveId;
        this.term = term;
        this.value = value;
    }


    public CurvePoint() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCurveId() {
        return curveId;
    }

    public void setCurveId(Integer curveId) {
        this.curveId = curveId;
    }

    public Timestamp getAsOfDate() {
        return asOfDate;
    }

    public void setAsOfDate(Timestamp asOfDate) {
        this.asOfDate = asOfDate;
    }

    public Double getTerm() {
        return term;
    }

    public void setTerm(Double term) {
        this.term = term;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurvePoint that = (CurvePoint) o;
        return Objects.equals(id, that.id) && Objects.equals(curveId, that.curveId) && Objects.equals(asOfDate, that.asOfDate) && Objects.equals(term, that.term) && Objects.equals(value, that.value) && Objects.equals(creationDate, that.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, curveId, asOfDate, term, value, creationDate);
    }

    @Override
    public String toString() {
        return "CurvePoint{" +
                "id=" + id +
                ", curveId=" + curveId +
                ", asOfDate=" + asOfDate +
                ", term=" + term +
                ", value=" + value +
                ", creationDate=" + creationDate +
                '}';
    }
}
