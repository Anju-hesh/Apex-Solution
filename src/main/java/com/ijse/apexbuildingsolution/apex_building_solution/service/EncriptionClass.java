package com.ijse.apexbuildingsolution.apex_building_solution.service;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class EncriptionClass {

    private static final char[] keys = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '!', '\"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/'
    };

    private String password;
    private static final char[] encryptedKeys = { 'R', 'o', 'P', '0', '2', '-', 'l', 'b', '3', '+', '-', 'R', 'f', 'B', '6', 'D', '2', '5', 'H', ',', 'k', 'C', '0', 'j', 'P', '2', ')', '.', 'i', 'w', 'w', 'G', 'a', 'U', 'W', '4', 'L', '-', 'e', 'C', 'n', 'F', '+', 's', 'f', 'r', 'S', '1', '#', 'd', 'j', 'o', 'c', 'I', 'p', '5', 'p', '4', '*', 'n', 'p', 'L', 's', 'C', '6', '-', 'Q', '$', ')', 'M', 'U', 'g', 's', '!', 'D', 'P', 'I' };

    private String encryptedPassword ="";

    public void setPassword(String password) {
        this.password = password;
        this.encryptedPassword = encryptPassword(password);
    }

    private String encryptPassword(String password) {
        for (int i = 0; i < password.length(); i++) {
            for (int j = 0; j < keys.length; j++) {
                if (keys[j]==password.charAt(i)) {
                    encryptedPassword+=encryptedKeys[j];
                }
            }
        }
        return encryptedPassword+"S@34%";
    }

    public boolean checkPassword(String password) {
        String encryptedInput = encryptPassword(password);
        return encryptedInput.equals(encryptedPassword);
    }
}
