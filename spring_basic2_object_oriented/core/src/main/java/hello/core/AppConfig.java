package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

public class AppConfig {
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    private MemberRepository memberRepository() { // memberRepository는 memory로 쓸것이다 .라는 것을 여기애서 정의.
        return new MemoryMemberRepository();
    }

    public OrderService orderService() { // orderService는 정의되어있는 member, discount 객체를 가져옴 ( orderService가 스스로 정의하는것이 아님)
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    public DiscountPolicy discountPolicy() { // disCount정책에 관련해서는 여기에서 정의
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
