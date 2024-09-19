package com.project.demo.logic.entity.category;


import jakarta.persistence.*;


@Table(name = "product")
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;

    // Constructor
    public Category(){}


    public Integer getId(){
        return id;
    }
    public void setId(Integer id){
        this.id = id;
    }


    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getDescription(){
        return description;
    }


}
