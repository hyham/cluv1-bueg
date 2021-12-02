package com.shop.service;

import com.shop.entity.Member;
import com.shop.entity.OAuth2Member;
import com.shop.repository.MemberRepository;
import com.shop.repository.OAuth2MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    private final OAuth2MemberRepository oAuth2MemberRepository;

    public Member saveMember(Member member) {
        validateDuplicateMember(member);

        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByEmail(member.getEmail());

        if(findMember != null) {
            OAuth2Member findSocialMember = oAuth2MemberRepository.findByMember(findMember);

            if(findSocialMember != null) {
                throw new IllegalStateException("해당 이메일은 소셜 로그인으로 등록된 이메일입니다, 소셜 로그인을 이용해 주세요.");
            }

            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);

        if(member == null) {
            throw new UsernameNotFoundException(email);
        }

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }

}