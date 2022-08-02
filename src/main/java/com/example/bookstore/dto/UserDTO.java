package com.example.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO{
    @NotNull(message = "UserName cannot be null")
    @Pattern(regexp = "^[a-zA-Z\\s]{3,}$", message = "User Name Invalid")
   public String userName;

   @NotNull(message = "Password cannot be empty")
   @Pattern(regexp = "^(?=.*[A-z])(?=.*[0-9])(?=.*[@#$%^&*()-+=])([a-zA-Z0-9@._-]).{8,}$")
   public String password;

   @NotNull(message = "Email Cannot be empty")
   @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
   public String email;

   @NotNull(message = "Address Cannot Be Empty")
   @Pattern(regexp = "^[0-9\\\\\\/# ,a-zA-Z]+[ ,]+[0-9\\\\\\/#, a-zA-Z]{1,}", message = "Address Is Invalid")
   public String address;

}
