package com.example.mybatisplus.service.impl;

import com.example.mybatisplus.model.domain.Member;
import com.example.mybatisplus.mapper.MemberMapper;
import com.example.mybatisplus.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
