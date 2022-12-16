package springsecurity.example.security.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import springsecurity.example.domain.User;
import springsecurity.example.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String UserId) throws UsernameNotFoundException {

        User user = userRepository.findById(UserId);
        if (user != null) {
            return new PrincipalDetails(user);
        }
        return null;
    }
}
