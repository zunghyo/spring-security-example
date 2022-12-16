package springsecurity.example.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springsecurity.example.domain.User;
import springsecurity.example.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final MessageSource messageSource;

    @Transactional
    public String signUp(User user){

        validateDuplicateUser(user);
        userRepository.saveUser(user);

        return user.getUserId();
    }

    private void validateDuplicateUser(User user) {
        List<User> findUsers = userRepository.findAllById(user.getUserId());
        if (!findUsers.isEmpty()) {
            throw new IllegalStateException(messageSource.getMessage("user.signup.exception.duplication",null,null));
        }
    }

    public User findUser(String userId){
        User findUser =userRepository.findById(userId);
        if (findUser == null) {
            throw new IllegalStateException(messageSource.getMessage("user.find.exception.notFound",null,null));
        }
        return findUser;
    }
}
