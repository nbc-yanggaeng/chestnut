package org.spring.chestnut.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.spring.chestnut.global.entity.Timestamped;

@Getter
@Entity
@Builder
@Table(name = "member")
@SQLDelete(sql = "update member set deleted_at = NOW() where id = ?")
@SQLRestriction(value = "deleted_at is NULL")
@NoArgsConstructor
@AllArgsConstructor
public class MemberEntity extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false)
    private String password;

    public static MemberEntity of(String email, String password) {
        return MemberEntity.builder()
            .email(email)
            .password(password)
            .build();
    }

    public void updatePassword(String password) {
        if (password.equals(this.password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        this.password = password;
    }
}
