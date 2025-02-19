package spring.boot.demo.service;

import spring.boot.demo.domain.SysUser;

import java.util.List;

/**
 * @author Tree
 * @version V1.0
 * @Title:
 * @Description:
 * @date 2022/3/29 16:41
 */
public interface SysUserService {

    /**
     * 查询
     * @param sysUser
     * @return
     */
    List<SysUser> selectFilterSysUser(SysUser sysUser);

    /**
     * 删除
     * @param id
     * @return
     */
    Integer deleteByPrimaryKey(String id);

    /**
     * 插入
     * @param sysUser
     * @return
     */
    Integer insert(SysUser sysUser);

    /**
     * 注册
     * @param sysUser
     * @return
     */
    Integer insertRegister(SysUser sysUser);

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    SysUser selectByPrimaryKey(String id);

    /**
     * 根据账号查询
     * @param account
     * @return
     */
    List<SysUser> selectByAccount(String account);

    /**
     * 更新
     * @param sysUser
     * @return
     */
    Integer updateByPrimaryKeySelective(SysUser sysUser);

    /**
     * 登录验证
     * @param account
     * @param password
     * @param userRole
     * @return
     */
    SysUser login(String account, String password, Integer userRole);
}
