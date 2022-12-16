package springsecurity.example.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import springsecurity.example.domain.User;
import springsecurity.example.dto.FindUserResponse;
import springsecurity.example.dto.SignupUserRequest;
import springsecurity.example.dto.UserResponse;
import springsecurity.example.security.auth.PrincipalDetails;
import springsecurity.example.service.UserService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class UserController {

    private final UserService userService;
    private final MessageSource messageSource;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/signup")
    @ApiOperation(value = "회원 가입", notes = "회원 가입 정보를 입력하여 회원가입을 합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT 토큰", required = false),
            @ApiImplicitParam(name = "signupUserRequest", value = "회원 가입 정보", required = true)
    })
    public UserResponse signUp(@RequestBody @Validated SignupUserRequest signupUserRequest, BindingResult bindingResult) throws Exception {

        if(bindingResult.hasErrors()){
            return new UserResponse("fail", messageSource.getMessage("user.signup.fail",null,null));
        }

        User user = User.builder()
                        .userId(signupUserRequest.getUserId())
                        .password(bCryptPasswordEncoder.encode(signupUserRequest.getPassword()))
                        .name(signupUserRequest.getName())
                        .build();

        userService.signUp(user);

        return new UserResponse("success",messageSource.getMessage("user.signup.success",null,null));
    }

    @GetMapping("/find")
    @ApiOperation(value = "회원 조회", notes = "JWT 토큰 인증으로 회원 정보를 조회합니다.")
    @ApiImplicitParam(name = "Authorization", value = "JWT 토큰", required = true)
    public FindUserResponse findUser(@ApiIgnore @AuthenticationPrincipal PrincipalDetails principalDetails) {

        log.info("PrincipalDetails {} ",principalDetails);

        String authUserId = principalDetails.getUser().getUserId();
        User user = userService.findUser(authUserId);

        return new FindUserResponse(user.getUserId(), user.getName());
    }
}
