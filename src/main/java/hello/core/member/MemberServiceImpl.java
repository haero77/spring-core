package hello.core.member;

import hello.core.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    // 기존의 AppConfig 클래스에서는 @Bean을 이용하였으므로 수동으로 의존관계를 설정했다.
    // @Component를 사용하면 수동으로 의존관계를 설정할 수 없기 때문에, @Autowired를 사용한다.
    @Autowired // ac.getBean(MemberRepository.class) 처럼 동작한다고 이해하자.
    public MemberServiceImpl(MemberRepository memberRepository) { // Dependency Injection(의존 관계 주입)
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return this.memberRepository;
    }
}
