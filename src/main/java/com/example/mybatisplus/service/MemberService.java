package com.example.mybatisplus.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.model.domain.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mybatisplus.model.dto.SortDTO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 会员 服务类
 * </p>
 *
 * @author harry
 * @since 2024-01-06
 */
public interface MemberService extends IService<Member> {

    Page<Member> memberPage(Page<Member> page, Member member, SortDTO sortDTO);

    int inesrt(Member member);

    void exportMembers(HttpServletResponse httpServletResponse, Member member);

    List<Member> getMembers(Member member);
}
