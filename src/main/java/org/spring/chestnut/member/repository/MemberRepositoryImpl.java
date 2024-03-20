package org.spring.chestnut.member.repository;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.spring.chestnut.member.dto.request.SignupDto;
import org.spring.chestnut.member.dto.response.MemberResponseDto;
import org.spring.chestnut.member.entity.MemberEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean checkEmail(String email) {
        return memberJpaRepository.findByEmail(email).isPresent();
    }

    @Override
    public MemberResponseDto signup(SignupDto dto) {
        MemberEntity member = memberJpaRepository.save(
            MemberEntity.of(dto.getEmail(), passwordEncoder.encode(dto.getPassword())));

        return new MemberResponseDto(member.getEmail());
    }

    @Override
    public Optional<MemberEntity> findByEmail(String email) {
        return memberJpaRepository.findByEmail(email);
    }

    @Override
    public Optional<MemberEntity> findByMemberId(Long memberId) {
        return memberJpaRepository.findById(memberId);
    }

    @Override
    public void deleteMember(Long memberId) {
        MemberEntity member = memberJpaRepository.findById(memberId).orElseThrow(
            () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다.")
        );

        memberJpaRepository.deleteCollaborator(memberId);
        memberJpaRepository.deleteWorkers(memberId);

        memberJpaRepository.delete(member);
    }
}
