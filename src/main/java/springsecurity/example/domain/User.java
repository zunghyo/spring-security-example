package springsecurity.example.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Table(name = "user_info")
@NoArgsConstructor
public class User {

    @Id
    private String userId;
    private String password;
    private String name;

    @Builder
    public User(String userId, String password, String name, String regNo) {
        this.userId = userId;
        this.password = password;
        this.name = name;
    }
}
