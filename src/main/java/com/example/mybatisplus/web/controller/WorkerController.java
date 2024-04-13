package com.example.mybatisplus.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mybatisplus.common.utls.SessionUtils;
import com.example.mybatisplus.model.dto.MailDTO;
import com.example.mybatisplus.service.MailService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.service.WorkerService;
import com.example.mybatisplus.model.domain.Worker;

import java.util.Map;
import java.util.Random;


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
@RequestMapping("/api/worker")
public class WorkerController {

    private final Logger logger = LoggerFactory.getLogger( WorkerController.class );

    @Autowired
    private WorkerService workerService;
    @Autowired
    private MailService mailService;

    /**
    * 描述：根据Id 查询
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Integer id)throws Exception {
        Worker  worker =  workerService.getById(id);
        return JsonResponse.success(worker);
    }

    /**
     * 根据id删除员工
     * @param id 员工id
     * @return json data returns true if success, else false
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Integer id){
        return JsonResponse.success(workerService.removeById(id));
    }


    /**
     * 根据id跟新Worker
     * @param worker 必须包含所有基本信息
     * @return json data returns true if success, else false
     */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateWorker(Worker  worker){
        return JsonResponse.success(workerService.updateById(worker));
    }

    /**
     * 根据id更新员工姓名
     * @param worker 必须包含id信息
     * @return json data returns true if success, else falseer
     */
    @ResponseBody
    @PostMapping("/updateWorkerById")
    public JsonResponse updateWorkerById(@RequestBody Worker worker){
        if(worker.getId() == null){
            return JsonResponse.success(null);
        }else{
            return JsonResponse.success(workerService.updateWorkerById(worker));
        }
    }

    /**
     * 用户登录
     */
    @ResponseBody
    @PostMapping("/login")
    public JsonResponse login(@RequestBody Worker worker){
        Worker w = workerService.login(worker);
        SessionUtils.saveCurrentWorkerInfo(w);
        return JsonResponse.success(w);
    }

    /**
     * 管理员注册
     * @param worker
     * @return
     */
    @ResponseBody
    @PostMapping("/adminRegister")
    public JsonResponse adminRegister(@RequestBody Worker worker){
        Integer adminId = workerService.insert(worker);
        return JsonResponse.success(adminId.toString());
    }

    /**
     * 发送账号信息
     * @param worker
     * 前端传入worker两个参数
     * @return
     */
    @ResponseBody
    @PostMapping ("/registerMessage")
    public JsonResponse registerMessage(@RequestBody Worker worker) {
        String message =
                "欢迎登录收银管理系统！\n" +
                "您的店铺账号为" + worker.getSupId() + "\n" +
                "您的店铺登录账号为" + worker.getId();
        String[] to = new String[1];
        to[0] = worker.getMail();
        MailDTO mailDTO = new MailDTO();
        mailDTO.setTo(to);
        mailDTO.setText(message);
        mailDTO.setSubject("注册成功");
        mailDTO.setFrom("2057787038@qq.com");
        mailService.sendSimpleMailMessage(mailDTO);
        return JsonResponse.success("已发送");
    }

    /**
     * 发送验证码
     * @param worker
     * @return
     */
    @ResponseBody
    @GetMapping ("/sendCode")
    public JsonResponse sendCode(Worker worker) {
        String[] to = new String[1];
        to[0] = worker.getMail();
        MailDTO mailDTO = new MailDTO();
        mailDTO.setTo(to);
        Random r = new Random();
        String code = new String();
        for(int i=0; i<4; i++)
        {
            code = code + String.valueOf(r.nextInt(9));
        }
        String text = "验证码：" + code + "，三分钟内有效，请尽快验证。";
        mailDTO.setText(text);
        mailDTO.setSubject("验证码");
        mailDTO.setFrom("2057787038@qq.com");
        boolean result = mailService.sendSimpleMailMessage(mailDTO);
        //System.out.println(System.currentTimeMillis());
        SessionUtils.saveSendTime(System.currentTimeMillis());
        SessionUtils.saveCode(code);
        return JsonResponse.success(result);
    }
    @ResponseBody
    @GetMapping("/checkCode")
    public JsonResponse checkCode(String code) {
        long sendTime = SessionUtils.getSendTime();
        //System.out.println(System.currentTimeMillis());
        //System.out.println(sendTime);
        if(System.currentTimeMillis() - sendTime <= 180000)
        {
            //System.out.println(code);
            //System.out.println(SessionUtils.getCode());
            if(code.equals(SessionUtils.getCode()))
            {
                return JsonResponse.success("验证成功");
            }else{
                return JsonResponse.success("验证码错误");
            }
        }
        return JsonResponse.success("验证码超时");
    }

    /**
     * 根据id修改密码，且只修改密码
     * @param worker
     * @return
     */
    @ResponseBody
    @PostMapping("/changePassword")
    public JsonResponse changePassword(@RequestBody Worker worker){
        Worker w = new Worker().setId(worker.getId()).setPassword(worker.getPassword());
        boolean result = workerService.updateById(w);
        return JsonResponse.success(result);
    }

    /**
     * 根据用户id获取超市id
     * @param worker
     * @return
     */
    @ResponseBody
    @GetMapping("/getSupId")
    public JsonResponse getSupIdById(Worker worker){
        Worker w = workerService.getById(worker.getId());
        return JsonResponse.success(w.getSupId());
    }

    /**
     * 根据店铺id获取所有员工
     * @param supId 店铺id
     * @return Worker列表
     */
    @ResponseBody
    @GetMapping("/getSupWorkers/{id}")
    public JsonResponse getSupWorkers(@PathVariable("id") Integer supId){
        if (supId == null) {
            return JsonResponse.success(null);
        }else{
            return JsonResponse.success(workerService.getSupWorkers(supId));
        }
    }

    /**
     * 根据姓名查询员工
     * @param worker 必须包含supId与name
     * @return 返回Worker列表
     */
    @ResponseBody
    @GetMapping("/getWorkersByName")
    public JsonResponse getWorkersByName(Worker worker){
        if(worker.getSupId() == null || worker.getName() == null){
            return JsonResponse.success(null);
        }else{
            return JsonResponse.success(workerService.getWorkersByName(worker));
        }
    }

    /**
     * 创建员工
     * @param worker 必须包含supId, password, type信息
     * @return 创建成功返回员工id,否则返回错误信息
     */
    @ResponseBody
    @PostMapping("/createWorker")
    public JsonResponse createWorker(@RequestBody Worker worker){
        if(worker.getSupId() == null){
            return JsonResponse.failure("创建失败：没有店铺id信息");
        }else if(worker.getPassword() == null){
            return JsonResponse.failure("创建失败：没有密码信息");
        }else if(worker.getType() == null){
            return JsonResponse.failure("创建失败：没有员工类型信息");
        }else {
            return JsonResponse.success(workerService.insert(worker));
        }
    }

}

