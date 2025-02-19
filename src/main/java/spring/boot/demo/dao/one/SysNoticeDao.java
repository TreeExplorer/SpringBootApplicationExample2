package spring.boot.demo.dao.one;

import org.springframework.stereotype.Repository;
import spring.boot.demo.domain.SysNotice;

import java.util.List;
import java.util.Map;

/**
 * @author Tree
 * @version V1.0
 * @Title:
 * @Description:
 * @date 2022/3/15 23:19
 */
@Repository
public interface SysNoticeDao {

    /**
     * 根据条件查询
     * @param sysNotice
     * @return
     */
    List<SysNotice> selectFilterSysNotice(SysNotice sysNotice);

    /**
     * 删除
     * @param id
     * @return
     */
    int deleteByPrimaryKey(String id);

    /**
     * 插入
     * @param sysNotice
     * @return
     */
    int insertSelective(SysNotice sysNotice);

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    SysNotice selectByPrimaryKey(String id);

    List<SysNotice> selectByPrimaryKey1(Map<String, String> id);

    /**
     * 删除
     * @param sysNotice
     * @return
     */
    int updateByPrimaryKeySelective(SysNotice sysNotice);

}
