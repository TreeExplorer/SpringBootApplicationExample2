package spring.boot.demo.dao.one;

import org.springframework.stereotype.Repository;
import spring.boot.demo.domain.SysUser;

import java.util.List;

/**
 * @author Tree
 * @version V1.0
 * @Title:
 * @Description:
 * @date 2022/3/15 23:19
 */
@Repository
public interface SysUserDao {

    /**
     * 根据条件查询
     * @param sysUser
     * @return
     */
    List<SysUser> selectFilterSysUser(SysUser sysUser);

    /**
     * 删除
     * @param id
     * @return
     */
    int deleteByPrimaryKey(String id);

    /**
     * 插入
     * @param sysUser
     * @return
     */
    int insertSelective(SysUser sysUser);

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    SysUser selectByPrimaryKey(String id);

    /**
     * 更新
     * @param sysUser
     * @return
     */
    int updateByPrimaryKeySelective(SysUser sysUser);

}
