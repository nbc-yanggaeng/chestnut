package org.spring.chestnut.member.repository;

import java.util.Optional;
import org.spring.chestnut.member.dto.request.SignupDto;
import org.spring.chestnut.member.dto.response.MemberResponseDto;
import org.spring.chestnut.member.entity.MemberEntity;

public interface MemberRepository {

  boolean checkEmail(String email);

  MemberResponseDto signup(SignupDto dto);

  Optional<MemberEntity> findByEmail(String email);

  Optional<MemberEntity> findByMemberId(Long memberId);
}
