package com.vodafone.imdbdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created By : rezaee_r
 * Date : 3/1/2021
 **/

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TPrincipalsId implements Serializable {

    /*
     * alphanumeric unique identifier of the title
     */
    private String tconst;
    /*
     * a number to uniquely identify rows for a given titleId
     */
    private String ordering;

}

