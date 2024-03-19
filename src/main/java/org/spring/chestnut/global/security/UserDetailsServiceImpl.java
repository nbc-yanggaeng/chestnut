package org.spring.chestnut.global.security;

import lombok.RequiredArgsConstructor;
import org.spring.chestnut.member.entity.MemberEntity;
import org.spring.chestnut.member.repository.MemberJpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final MemberJpaRepository memberRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    MemberEntity member = memberRepository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("Not Found " + username));

    return new UserDetailsImpl(member);
  }
}
