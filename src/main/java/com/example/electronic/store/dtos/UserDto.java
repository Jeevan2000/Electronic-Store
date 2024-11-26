package com.example.electronic.store.dtos;


import com.example.electronic.store.validate.ImageNameValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserDto {

    private String userid;

    @Size(min =3, max =20, message = "Invalid name!!")
    private String name;


    //@Email(message = "Invalid User Email!!")
    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Invalid User Email!!")
    @NotBlank(message = "Email cannot be blank" )
    private String email;

    @Size(min =4,max=6, message = "Invalid gender!!")
    private String gender;

    @NotBlank(message = "Password cannot be empty")
    private String password;

    @ImageNameValid
    private String imageName;

    @NotBlank(message = "Write something about yourself!!")
    private String about;


    // pattern
    // Custom validator
}
