package spring.boot.demo.service.impl;

import spring.boot.demo.dao.one.SysUserDao;
import spring.boot.demo.domain.SysUser;
import spring.boot.demo.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author Tree
 * @version V1.0
 * @Title:
 * @Description:
 * @date 2022/3/29 16:41
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserDao userDao;

    @Override
    public List<SysUser> selectFilterSysUser(SysUser sysUser) {
        return userDao.selectFilterSysUser(sysUser);
    }

    @Override
    public Integer deleteByPrimaryKey(String id) {
        return userDao.deleteByPrimaryKey(id);
    }

    @Override
    public Integer insert(SysUser sysUser) {
        sysUser.setId(UUID.randomUUID().toString());
        return userDao.insertSelective(sysUser);
    }

    @Override
    public Integer insertRegister(SysUser sysUser) {
        SysUser user = new SysUser();
        user.setAccount(sysUser.getAccount());
        List<SysUser> users = userDao.selectFilterSysUser(user);
        if (users.size() > 0){
            return -1;
        }
        sysUser.setId(UUID.randomUUID().toString());
        return userDao.insertSelective(sysUser);
    }

    @Override
    public SysUser selectByPrimaryKey(String id) {
        return userDao.selectByPrimaryKey(id);
    }

    @Override
    public List<SysUser> selectByAccount(String account) {
        SysUser sysUser = new SysUser();
        sysUser.setAccount(account);
        return userDao.selectFilterSysUser(sysUser);
    }

    @Override
    public Integer updateByPrimaryKeySelective(SysUser sysUser) {
        return userDao.updateByPrimaryKeySelective(sysUser);
    }

    @Override
    public SysUser login(String account, String password, Integer userRole){
        SysUser sysUser = new SysUser();
        sysUser.setAccount(account);
        sysUser.setPassword(password);
        sysUser.setUserRole(userRole);
        List<SysUser> users = userDao.selectFilterSysUser(sysUser);
        if (users.size() == 1) {
            return userDao.selectFilterSysUser(sysUser).get(0);
        }
        return null;
    }
}
