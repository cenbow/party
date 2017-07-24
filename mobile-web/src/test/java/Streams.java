import com.google.common.collect.Lists;
import com.party.common.paging.Page;
import com.party.core.model.YesNoStatus;
import com.party.core.model.member.Member;
import com.party.core.service.member.IMemberService;
import com.party.core.service.order.IOrderFormService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


/**
 * party
 * Created by wei.li
 * on 2016/10/18 0018.
 */
public class Streams extends BaseTest {

    @Autowired
    IOrderFormService orderFormService;

    @Autowired
    IMemberService memberService;

    @Test
    public void test(){
        IntStream.of(new int[]{1, 2, 3}).forEach(System.out::println);
    }

    @Test
    public void test2(){
        IntStream.range(1, 3).forEach(System.out::println);
    }

    @Test
    public void test3(){
        IntStream.rangeClosed(1, 3).forEach(System.out::println);
    }

    @Test
    public void test4(){
        Stream<String> stream = Stream.of("a", "b", "c");
        List<String> list = stream.map(String::toUpperCase).collect(Collectors.toList());
        System.out.println(list);
    }

    @Test
    public void test5(){
        Integer[] num = {1, 2, 3, 4, 5, 6, 7, 8};
        List<Integer> list = Stream.of(num).filter(n -> n%2 == 0).collect(Collectors.toList());
        System.out.println(list);
    }

    @Test
    public void test6(){
        Member member = new Member();
        member.setIsOpen(0);
        member.setRealname("vv");
        List<Member> list  = Lists.newArrayList();
        list.add(member);
        list.stream().filter(m -> m.getIsOpen().equals(YesNoStatus.YES.getCode())).forEach(System.out::println);
    }


    @Test
    public void test7(){
        Stream.of("one", "two", "three", "four")
                .filter( e -> e.length()>3)
                .peek(p -> System.out.println(p))
                .map(String::toUpperCase)
                .peek(e -> System.out.println(e))
                .collect(Collectors.toList());


    }

    @Test
    public void test8(){
        int a = Optional.ofNullable("").map(String::length).orElse(0);
        System.out.println(a);
    }

    @Test
    public void test9(){
        String content = Stream.of("a", "b", "c", "d").reduce("e", String::concat);
        System.out.println(content);
    }

    @Test
    public void test10(){
        Integer result = Stream.of(1, 4, -2, -7).reduce(-9, Integer::min);
        System.out.println(result);
    }


    @Test
    public void test11(){
        List<Member> memberList = memberService.listPage(null, new Page(1, 10));
        System.out.println(memberList);
        List<Member> list = memberList.stream().limit(5).skip(3).collect(Collectors.toList());
        System.out.println(list);
    }

    @Test
    public void test12(){

    }


}
