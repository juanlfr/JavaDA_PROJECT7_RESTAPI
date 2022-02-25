package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "rating")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id ;
    private String moodysRating ;
    private String sandPRating ;
    private String fitchRating ;
    private Integer orderNumber ;

    public Rating(String moodysRating, String sandPRating, String fitchRating, Integer orderNumber) {
        this.moodysRating = moodysRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }

    public Rating() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMoodysRating() {
        return moodysRating;
    }

    public void setMoodysRating(String moodysRating) {
        this.moodysRating = moodysRating;
    }

    public String getSandPRating() {
        return sandPRating;
    }

    public void setSandPRating(String sandPRating) {
        this.sandPRating = sandPRating;
    }

    public String getFitchRating() {
        return fitchRating;
    }

    public void setFitchRating(String fitchRating) {
        this.fitchRating = fitchRating;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rating rating = (Rating) o;
        return Objects.equals(id, rating.id) && Objects.equals(moodysRating, rating.moodysRating) && Objects.equals(sandPRating, rating.sandPRating) && Objects.equals(fitchRating, rating.fitchRating) && Objects.equals(orderNumber, rating.orderNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, moodysRating, sandPRating, fitchRating, orderNumber);
    }

    @Override
    public String toString() {
        return "Rating{" +
                "id=" + id +
                ", moodysRating='" + moodysRating + '\'' +
                ", sandPRating='" + sandPRating + '\'' +
                ", fitchRating='" + fitchRating + '\'' +
                ", orderNumber=" + orderNumber +
                '}';
    }
}
