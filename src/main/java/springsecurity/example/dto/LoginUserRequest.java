package springsecurity.example.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class LoginUserRequest {
    @NotBlank
    @NotNull
    private final String userId;

    @NotBlank
    @NotNull
    private final String password;
}
