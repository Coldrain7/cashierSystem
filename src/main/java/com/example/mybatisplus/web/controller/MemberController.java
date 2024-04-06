package com.example.mybatisplus.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.model.dto.PageDTO;
import com.example.mybatisplus.model.dto.SortDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.service.MemberService;
import com.example.mybatisplus.model.domain.Member;

import javax.servlet.http.HttpServletResponse;


/**
 *
 *  前端控制器
 *
 *
 * @author harry
 * @since 2024-01-06
 * @version v1.0
 */
@Controller
@RequestMapping("/api/member")
public class MemberController {

    private final Logger logger = LoggerFactory.getLogger( MemberController.class );

    @Autowired
    private MemberService memberService;

    /**
    * 描述：根据Id 查询
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id)throws Exception {
        Member  member =  memberService.getById(id);
        return JsonResponse.success(member);
    }

    /**
     * 根据id删除会员
     * @param id 被删除的会员id
     * @return 删除成功返回true,否则返回false
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id")Long id){
        return JsonResponse.success(memberService.removeById(id));
    }

    /**
     * 根据id更新会员信息
     * @param member 会员信息
     * @return 修改成功status返回true,否则返回false并包含错误message信息
     */
    @ResponseBody
    @PostMapping("/updateMember")
    public JsonResponse updateMember(@RequestBody Member  member) {
        if(StringUtils.isBlank(member.getName())){
            return JsonResponse.failure("姓名不能为空");
        }
        if(member.getPhone() == null ){
            return JsonResponse.failure("电话号码不能为空");
        }
        if(member.getPoint() < 0){
            member.setPoint(0.0);
        }
        if(member.getSupId() == null || member.getSupId() < 0){
            return JsonResponse.failure("店铺id信息错误");
        }
        boolean res = memberService.updateById(member);
        if(res){
            return JsonResponse.success( true,"保存成功");
        }else {
            return JsonResponse.failure("保存失败");
        }
    }

    /**
     * 创建新会员
     * @param member name,supId与phone不能为空
     * @return 创建成功返回新会员id，否则返回错误信息
     */
    @ResponseBody
    @PostMapping("/createMember")
    public JsonResponse create(@RequestBody Member  member){
        if(member.getId() != null){
            member.setId(null);
        }
        if(StringUtils.isBlank(member.getName())){
            return JsonResponse.failure("姓名不能为空");
        }
        if(member.getPhone() == null ){
            return JsonResponse.failure("电话号码不能为空");
        }
        if(member.getSupId() == null || member.getSupId() < 0){
            return JsonResponse.failure("店铺id信息错误 ");
        }
        if(member.getPoint() == null || member.getPoint() < 0){
            member.setPoint(0.0);
        }
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("sup_id", member.getSupId()).eq("phone", member.getPhone());
        Member m = memberService.getOne(wrapper);
        if(m == null){
            int id = memberService.inesrt(member);
            return JsonResponse.success(id, "创建成功");
        }else{
            return JsonResponse.failure("号码已存在");
        }
    }

    /**
     * 查询会员功能，关键字需要用name字段存储
     * @param pageDTO 分页数据
     * @param member 需要包含supId与name字段
     * @param sortDTO 存储排序字段
     * @return 以Page形式返回数据
     */
    @ResponseBody
    @GetMapping("/memberPage")
    public JsonResponse memberPage(PageDTO pageDTO, Member member, SortDTO sortDTO){
        Page<Member> page = new Page<>(pageDTO.getPageNo(), pageDTO.getPageSize());
        memberService.memberPage(page, member, sortDTO);
        return JsonResponse.success(page);
    }

    @ResponseBody
    @PostMapping("/exportMembers")
    public void exportMembers(HttpServletResponse httpServletResponse, @RequestBody Member member){
        memberService.exportMembers(httpServletResponse, member);
    }
}

