package com.vodafone.imdbdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created By : rezaee_r
 * Date : 3/1/2021
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TitlePrincipalsModel {


    private String nconst;
    private String tconst;
    private String ordering;
    private String category;
    private String job;
    private String characters;

}