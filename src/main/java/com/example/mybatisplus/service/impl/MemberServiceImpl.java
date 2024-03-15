package com.example.mybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.common.utls.ExcelUtils;
import com.example.mybatisplus.model.domain.Member;
import com.example.mybatisplus.mapper.MemberMapper;
import com.example.mybatisplus.model.dto.SortDTO;
import com.example.mybatisplus.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 会员 服务实现类
 * </p>
 *
 * @author harry
 * @since 2024-01-06
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    private MemberMapper memberMapper;
    @Override
    public Page<Member> memberPage(Page<Member> page, Member member, SortDTO sortDTO) {
        sortDTO.isSortable();
        return memberMapper.selectMemberPage(page, member, sortDTO);
    }

    @Override
    public int inesrt(Member member) {
        memberMapper.insert(member);
        return member.getId();
    }

    @Override
    public void exportMembers(HttpServletResponse httpServletResponse, Member member) {
        SortDTO sortDTO = new SortDTO();
        List<Member> members = memberMapper.selectMemberPage(member, sortDTO);
        try {
            ExcelUtils.exportMembers(httpServletResponse, members);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
