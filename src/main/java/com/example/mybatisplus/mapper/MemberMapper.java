package com.example.mybatisplus.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.model.domain.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatisplus.model.dto.SortDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 会员 Mapper 接口
 * </p>
 *
 * @author harry
 * @since 2024-01-06
 */
@Repository
public interface MemberMapper extends BaseMapper<Member> {

    Page<Member> selectMemberPage(Page<Member> page, @Param("member") Member member, @Param("sortDTO") SortDTO sortDTO);
    List<Member> selectMemberPage(@Param("member") Member member,@Param("sortDTO") SortDTO sortDTO);
}
