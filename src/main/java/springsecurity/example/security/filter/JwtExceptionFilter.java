package springsecurity.example.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;
import springsecurity.example.exception.ErrorResult;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request, response);
        }catch (Exception e){
            ObjectMapper objectMapper = new ObjectMapper();
            response.setStatus(404);
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new ErrorResult("404", e.getMessage())));
        }
    }

}
