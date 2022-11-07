package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

class SingletonServiceTest {

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest() {
//        new SingleTonService(); // "컴파일 시에 확인할 수 있는 오류가 가장 좋다."

        // given
        SingletonService singletonService1 = SingletonService.getInstance();

        // when
        SingletonService singletonService2 = SingletonService.getInstance();

        // then
        assertThat(singletonService2).isSameAs(singletonService1);

        /*
        "isSameAs vs isEqualTo"
         isSameAs: same, ==
         isEqualTo: equal, equals()
         */
    }

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void spring_container() {
        // given
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        // when
        MemberService memberService1 = ac.getBean("memberService", MemberService.class);
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);

        // then
        assertThat(memberService1).isSameAs(memberService2);
    }
}