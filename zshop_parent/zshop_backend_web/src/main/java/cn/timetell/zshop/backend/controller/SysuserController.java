package cn.timetell.zshop.backend.controller;

import cn.timetell.zshop.common.constant.PaginationConstant;
import cn.timetell.zshop.common.exception.SysuserNotExistException;
import cn.timetell.zshop.common.util.ResponseResult;
import cn.timetell.zshop.params.SysuserParam;
import cn.timetell.zshop.pojo.Role;
import cn.timetell.zshop.pojo.Sysuser;
import cn.timetell.zshop.service.RoleService;
import cn.timetell.zshop.service.SysuserService;
import cn.timetell.zshop.vo.SysuserVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Timetellu on 2020/4/27.
 */

@Controller
@RequestMapping("/backend/sysuser")
public class SysuserController {
    @Autowired
    private SysuserService sysuserService;

    @Autowired
    private RoleService roleService;

    @RequestMapping("/login")
    public String login(String loginName,String password,HttpSession session,Model model){
        //实现登录
        try {
            Sysuser sysuser=sysuserService.login(loginName,password);
            session.setAttribute("sysuser",sysuser);
            return "main";
        } catch (SysuserNotExistException e) {
            model.addAttribute("errorMsg",e.getMessage());
            return "login";
            //e.printStackTrace();
        }
    }

    @RequestMapping("/findAll")
    public String findAll(Integer pageNum,Model model){
        if(ObjectUtils.isEmpty(pageNum)){
            pageNum= PaginationConstant.PAGE_NUM;
        }

        PageHelper.startPage(pageNum, PaginationConstant.PAGE_SIZE);
        List<Sysuser> sysusers = sysuserService.findAll();
        PageInfo<Sysuser> pageInfo = new PageInfo<Sysuser>(sysusers);
        model.addAttribute("pageInfo",pageInfo);

        return "sysuserManager";
    }

    @RequestMapping("/findByParams")
    public String findByParams(SysuserParam sysuserParam,Integer pageNum,Model model){
        if(ObjectUtils.isEmpty(pageNum)){
            pageNum= PaginationConstant.PAGE_NUM;
        }

        PageHelper.startPage(pageNum,PaginationConstant.PAGE_SIZE);
        List<Sysuser> sysusers=sysuserService.findByParams(sysuserParam);
        PageInfo<Sysuser> pageInfo = new PageInfo<Sysuser>(sysusers);
        model.addAttribute("pageInfo",pageInfo);
        //实现数据回显
        model.addAttribute("sysuserParam",sysuserParam);

        return "sysuserManager";
    }

    @RequestMapping("/add")
    @ResponseBody
    public ResponseResult add(SysuserVo sysuserVo){
        sysuserService.add(sysuserVo);
        return ResponseResult.success();
    }

    @ModelAttribute("roles")
    public List<Role> loadRoles(){
        return roleService.findAll();
    }

    @RequestMapping("/modifyStatus")
    @ResponseBody
    public ResponseResult modifyStatus(int id){
        sysuserService.modifyStatus(id);
        return ResponseResult.success();
    }

    @RequestMapping("/exit")
    public String exit(){
        return "login";
    }
}
