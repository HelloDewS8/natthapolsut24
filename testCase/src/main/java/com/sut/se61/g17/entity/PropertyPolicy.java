package com.sut.se61.g17.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/*@NoArgsConstructor*/
@Data
@Entity
public class PropertyPolicy {
    @Id
    @SequenceGenerator(name = "property_seq",sequenceName = "property_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "property_seq")
    @Column(name = "PROPERTY_ID")
    private @NotNull Long propertyID;

    @Column(unique = true )
    @Pattern(regexp = "^[A-Za-z]\\w+")
    @Size(min = 5,max = 30)
    @NotNull
    private String propertyName;

    @Size(min = 15 , max = 512)
    @NotNull
    private String detail ;


   @Min(value = 100)
   @NotNull
    private double costPolicy ;

    @ManyToOne
    @JoinColumn(name = "classID", nullable = false)
    private ClassProperty classProperty;

    public PropertyPolicy(@NotNull Long propertyID, @NotNull String propertyName, @Size(min = 15, max = 512) @NotNull String detail, @Min(value = 100) @NotNull double costPolicy, ClassProperty classProperty) {
        this.propertyID = propertyID;
        this.propertyName = propertyName;
        this.detail = detail;
        this.costPolicy = costPolicy;
        this.classProperty = classProperty;
    }

    public PropertyPolicy() {

    }


    public Long getPropertyID() {
        return propertyID;
    }

    public void setPropertyID(Long propertyID) {
        this.propertyID = propertyID;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public double getCostPolicy() {
        return costPolicy;
    }

    public void setCostPolicy(double costPolicy) {
        this.costPolicy = costPolicy;
    }

    public ClassProperty getClassProperty() {
        return classProperty;
    }

    public void setClassProperty(ClassProperty classProperty) {
        this.classProperty = classProperty;
    }
}
