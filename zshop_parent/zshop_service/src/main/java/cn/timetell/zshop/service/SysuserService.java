package cn.timetell.zshop.service;

import cn.timetell.zshop.common.exception.SysuserNotExistException;
import cn.timetell.zshop.params.SysuserParam;
import cn.timetell.zshop.pojo.Sysuser;
import cn.timetell.zshop.vo.SysuserVo;

import java.util.List;

/**
 * Created by Timetellu on 2020/4/28.
 */
public interface SysuserService {
    public List<Sysuser> findAll();

    public Sysuser findById(int id);

    public void add(SysuserVo sysuserVo);

    public void modify(SysuserVo sysuserVo);

    public void modifyStatus(int id);

    List<Sysuser> findByParams(SysuserParam sysuserParam);

    Sysuser login(String loginName, String password) throws SysuserNotExistException;
}
