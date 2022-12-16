package springsecurity.example.security.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import springsecurity.example.domain.User;
import springsecurity.example.security.auth.PrincipalDetails;
import springsecurity.example.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
public class JwtTokenInterceptor implements HandlerInterceptor {

    private final UserService userService;
    private final MessageSource messageSource;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws IOException {

        String jwtHeader = request.getHeader("Authorization");
        
        if (jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
            throw new IllegalArgumentException(messageSource.getMessage("user.auth.exception.tokenNotFound",null,null));
        }

        String header = request.getHeader("Authorization").replace("Bearer ", "");

        String jwtToken = JWT
                .require(Algorithm.HMAC256("cors"))
                .build()
                .verify(header)
                .getClaim("id")
                .asString();

        if (jwtToken != null) {
            User userEntity = userService.findUser(jwtToken);

            PrincipalDetails principalDetails = new PrincipalDetails(userEntity);
            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return true;
        }

        return false;
    }
}