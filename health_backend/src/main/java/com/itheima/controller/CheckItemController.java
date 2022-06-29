package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.container.page.PageHandler;
import com.github.pagehelper.PageHelper;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.service.CheckItemService;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import org.springframework.web.bind.annotation.*;
import com.itheima.pojo.CheckItem;

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {
    @Reference //dubbo 查找服务
    CheckItemService checkItemService;
    @PostMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){
        try {
            checkItemService.add(checkItem);
        }catch (Exception e){
            e.printStackTrace();
            //服务调用失败
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.ADD_CHECKITEM_SUCCESS);
    }
    @PostMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = checkItemService.pageQuery(queryPageBean);
        return pageResult;
    }
    @DeleteMapping("/delete")
    public Result delete(Integer id){
        try {
            checkItemService.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }
    @PutMapping("/edit")
    public Result edit(@RequestBody CheckItem checkItem){
        try {
            checkItemService.edit(checkItem);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }
    @PostMapping("/findById")
    public Result findById(Integer id){
        CheckItem checkItem;
        try {
            checkItem = checkItemService.findById(id);
        }catch (Exception e){
            e.printStackTrace();
            //服务调用失败
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
    }
}
