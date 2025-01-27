package com.ll.domain.member.member.controller;

import com.ll.domain.member.member.dto.MemberWithUsernameDto;
import com.ll.domain.member.member.service.MemberService;
import com.ll.standard.page.dto.PageDto;
import com.ll.standard.search.MemberSearchKeywordTypeV1;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/adm/members")
@RequiredArgsConstructor
@Tag(name = "ApiV1MemberController", description = "API 관리자용 회원 컨트롤러")
@SecurityRequirement(name = "bearerAuth")
public class ApiV1AdmMemberController {
    private final MemberService memberService;


    @GetMapping
    @Transactional(readOnly = true)
    @Operation(summary = "회원 다건 조회")
    public PageDto<MemberWithUsernameDto> items(
            @RequestParam(defaultValue = "username") MemberSearchKeywordTypeV1 searchKeywordType,
            @RequestParam(defaultValue = "") String searchKeyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        return new PageDto<>(
                memberService.findByPaged(searchKeywordType, searchKeyword, page, pageSize)
                        .map(MemberWithUsernameDto::new)
        );
    }


    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    @Operation(summary = "회원 단건 조회")
    public MemberWithUsernameDto item(
            @PathVariable long id
    ) {
        return new MemberWithUsernameDto(
                memberService.findById(id).get()
        );
    }
}
