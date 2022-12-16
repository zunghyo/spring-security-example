package springsecurity.example.security.provider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import springsecurity.example.security.auth.PrincipalDetails;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {

        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

        String userId = token.getName();
        String password = (String) token.getCredentials();

        PrincipalDetails principalDetails = (PrincipalDetails) userDetailsService.loadUserByUsername(userId);

        if(principalDetails == null){
           throw new NullPointerException("아이디 또는 비밀번호를 잘 못 입력하셨습니다.");
        }

        if (!bCryptPasswordEncoder.matches(password, principalDetails.getPassword())) {
            throw new NullPointerException("아이디 또는 비밀번호를 잘 못 입력하셨습니다.");
        }

        return new UsernamePasswordAuthenticationToken(principalDetails, password, principalDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
