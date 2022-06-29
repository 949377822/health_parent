package com.itheima.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckItemDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.itheima.pojo.CheckItem;

import java.util.List;

@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    private CheckItemDao checkItemDao;
    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }
    public PageResult pageQuery(QueryPageBean queryPageBean){
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckItem> page = checkItemDao.selectByCondition(queryString);
        long total = page.getTotal();
        List<CheckItem> result = page.getResult();
        return new PageResult(total,result);
    }

    @Override
    public void deleteById(Integer id) {
        Long countByCheckItemId = checkItemDao.findCountByCheckItemId(id);
        if (countByCheckItemId > 0){
            // 检查项被检查组关联 不允许删除
            throw new RuntimeException("当前检查项被检查组关联 不允许删除");
        }

        checkItemDao.deleteById(id);
    }

    @Override
    public void edit(CheckItem checkItem) {
        checkItemDao.edit(checkItem);
    }

    @Override
    public CheckItem findById(Integer id) {
        return checkItemDao.findById(id);
    }

}
