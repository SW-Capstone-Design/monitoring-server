package kr.co.monitoringserver.service.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserRequest {

    @NotBlank(message = "ID is required. Please enter your ID")
    @Size(min = 5, max = 15, message = "Please enter your ID in 5-15")
    private String identity;

    @NotBlank(message = "Password is required. Please enter your Password")
    @Size(min = 4, max = 15, message = "Please enter your Password in 4-15")
    private String password;

    @NotBlank(message = "Name is required. Please enter your name")
    private String name;

    @NotBlank(message = "PhoneNumber is required. Please enter your Phone")
    @Size(max = 13, message = "Please enter the phone number in 010-xxxx-xxxx format")
    private String phone;

    private String department;
}
