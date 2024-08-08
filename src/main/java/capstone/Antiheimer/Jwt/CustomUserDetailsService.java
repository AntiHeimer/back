package capstone.Antiheimer.Jwt;

import capstone.Antiheimer.domain.Member;
import capstone.Antiheimer.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public CustomUserDetails loadUserById(String memberId) throws UsernameNotFoundException {

        List<Member> memberList = memberRepository.findById(memberId);
        if (memberList.isEmpty()) {
            throw new UsernameNotFoundException("해당하는 회원을 찾을 수 없습니다");
        }

        Member member = memberList.get(0);
        return createUserDetails(member);
    }

    private CustomUserDetails createUserDetails(Member member) {

        return new CustomUserDetails(
                member.getName(),
                member.getPw(),
                member.getUuid());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
