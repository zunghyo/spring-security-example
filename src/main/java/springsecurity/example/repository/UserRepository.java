package springsecurity.example.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import springsecurity.example.domain.User;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public void saveUser(User user){
        em.persist(user);
    }

    public User findById(String userId){
        return em.find(User.class, userId);
    }

    public List<User> findAllById(String userId){
        return em.createQuery("select u from User u where u.userId = :userId", User.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<User> findAll() {
        return em.createQuery("select u from User u ", User.class)
                .getResultList();
    }

}
