package cn.timetell.zshop.service.impl;

import cn.timetell.zshop.dao.RoleDao;
import cn.timetell.zshop.pojo.Role;
import cn.timetell.zshop.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Timetellu on 2020/4/28.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    @Transactional(propagation =  Propagation.SUPPORTS)
    public List<Role> findAll() {
        return roleDao.selectAll();
    }
}