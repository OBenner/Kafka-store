package com.obenner.kafkastore.model;

import lombok.*;

/**
 * The type Account.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Ac {
    private int id;
    private String firstName;
    private String lastName;
    private int phone;
    private double amount;




    public Ac(int id, String firstName) {
        this.id=id;
        this.firstName=firstName;
    }


}
