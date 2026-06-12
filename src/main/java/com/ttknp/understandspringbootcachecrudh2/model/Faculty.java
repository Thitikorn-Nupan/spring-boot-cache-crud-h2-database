package com.ttknp.understandspringbootcachecrudh2.model;

import jakarta.persistence.*;

// if you use the tool database in intellij it'll make sure to mapping  @Table(name = "<name>") to table
// ctrl + (click to <name>)
@Entity
@Table(name = "faculties")
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer bachelorAmount; // ** it will mapped bachelor_amount column

    public Faculty(Long id, String name, Integer bachelorAmount) {
        this.id = id;
        this.name = name;
        this.bachelorAmount = bachelorAmount;
    }

    public Faculty() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBachelorAmount() {
        return bachelorAmount;
    }

    public void setBachelorAmount(Integer bachelorAmount) {
        this.bachelorAmount = bachelorAmount;
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", bachelorAmount='" + bachelorAmount + '\'' +
                '}';
    }
}
