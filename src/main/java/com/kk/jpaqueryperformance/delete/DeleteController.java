package com.kk.jpaqueryperformance.delete;

import com.kk.jpaqueryperformance.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class DeleteController {

    private final MemberRepository memberRepository;

    @RequestMapping("/clear")
    public void clear(){
        memberRepository.deleteAll();;
    }

}
