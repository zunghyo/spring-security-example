package springsecurity.example.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class SignupUserRequest {

    @NotBlank
    @NotNull
    @ApiModelProperty(example = "회원 아이디")
    private final String userId;

    @NotBlank
    @NotEmpty
    @ApiModelProperty(example = "회원 비밀번호")
    private final String password;

    @NotBlank
    @NotEmpty
    @ApiModelProperty(example = "회원 이름")
    private final String name;

}
