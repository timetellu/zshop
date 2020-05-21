package cn.timetell.zshop.backend.controller;

import cn.timetell.zshop.common.constant.PaginationConstant;
import cn.timetell.zshop.common.constant.ResponseStatusConstant;
import cn.timetell.zshop.common.exception.ProductTypeExistException;
import cn.timetell.zshop.common.util.ResponseResult;
import cn.timetell.zshop.pojo.ProductType;
import cn.timetell.zshop.service.ProductTypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Timetellu on 2020/4/27.
 */
@Controller
@RequestMapping("/backend/productType")
public class ProductTypeController {

    @Autowired
    private ProductTypeService productTypeService;

    @RequestMapping("/findAll")
    public String findAll(Integer pageNum,Model model){
        if(ObjectUtils.isEmpty(pageNum)){
            pageNum = PaginationConstant.PAGE_NUM;
        }
        PageHelper.startPage(pageNum,PaginationConstant.PAGE_SIZE);
        //查找所有商品类型
        List<ProductType> productTypes = productTypeService.findAll();
        //将查询到的结果传给PageInfo
        PageInfo<ProductType> pageInfo = new PageInfo<ProductType>(productTypes);
//        pageInfo.getPages();
//        pageInfo.getList();
        model.addAttribute("pageInfo",pageInfo);
        return "productTypeManager";
    }

    @RequestMapping("/add")
    @ResponseBody
    public ResponseResult add(String name){
        ResponseResult result = new ResponseResult();
        try {
            productTypeService.add(name);
            result.setStatus(ResponseStatusConstant.RESPONSE_STATUS_SUCCESS);
            result.setMessage("添加成功");
        } catch (ProductTypeExistException e) {
            result.setStatus(ResponseStatusConstant.RESPONSE_STATUS_FAIL);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @RequestMapping("/findById")
    @ResponseBody
    public ResponseResult findById(Integer id){
        ProductType productType = productTypeService.findById(id);
        System.out.println(productType);
        return ResponseResult.success(productType);
    }

    @RequestMapping("/modifyName")
    @ResponseBody
    public ResponseResult modifyName(Integer id,String name){
        try {
            productTypeService.modifyName(id,name);
            return ResponseResult.success("修改商品类型成功");
        } catch (ProductTypeExistException e) {
            return ResponseResult.fail(e.getMessage());
        }
    }

    @RequestMapping("/removeById")
    @ResponseBody
    public ResponseResult removeById(Integer id){
        productTypeService.removeById(id);
        return ResponseResult.success();
    }


    @RequestMapping("/modifyStatus")
    @ResponseBody
    public ResponseResult modifyStatus(Integer id){
        productTypeService.modifyStatus(id);
        return ResponseResult.success();
    }


}
