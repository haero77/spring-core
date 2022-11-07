package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

class StatefulServiceTest {

    @Test
    @DisplayName("싱글톤 객체가 Stateful할 때 생기는 문제")
    void statefulServiceSingleton() {
        // given
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean("statefulService", StatefulService.class);
        StatefulService statefulService2 = ac.getBean("statefulService", StatefulService.class);

        // when

        // Thread A: A사용자 10000원 주문
        statefulService1.order("userA", 10000); // this.price = 10000;

        // Thread B: B사용자 20000원 주문
        statefulService1.order("userB", 20000); // this.price = 20000; -> 10000원에서 20000원이 됨. (같은 인스턴스)

        // Thread A: 사용자 주문 금액 조회
        int price = statefulService1.getPrice();
        System.out.println("price = " + price);

        // then
        Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);
    }

    @Test
    void stateless_singleton() {
        // given
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatelessService statelessService1 = ac.getBean("statelessService", StatelessService.class);
        StatelessService statelessService2 = ac.getBean("statelessService", StatelessService.class);

        // when

        // "필드 대신에 자바에서 공유되지 않는, 지역변수, 파라미터, ThreadLocal 등을 사용해야 한다."
        // Thread A: A사용자 10000원 주문
        int userAPrice = statelessService1.order("userA", 10000);// return price;

        // Thread B: B사용자 20000원 주문
        int userBPrice = statelessService2.order("userB", 20000);// return price;

        // then
        Assertions.assertThat(userAPrice).isNotEqualTo(userBPrice);
    }

    static class TestConfig {

        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }

        @Bean
        public StatelessService statelessService() {
            return new StatelessService();
        }
    }
}