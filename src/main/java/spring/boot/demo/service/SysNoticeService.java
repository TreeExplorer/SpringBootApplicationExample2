package spring.boot.demo.service;

import spring.boot.demo.domain.SysNotice;

import java.util.List;

/**
 * @author Tree
 * @version V1.0
 * @Title:
 * @Description:
 * @date 2022/3/29 16:41
 */
public interface SysNoticeService {

    /**
     * 查询数据
     * @param sysNotice 
     * @return
     */
    List<SysNotice> selectAll(SysNotice sysNotice);

    /**
     * 查询最近一条公告
     * @return 
     */
    SysNotice freshNotice();

    /**
     * 删除
     * @param id 
     * @return
     */
    Integer deleteByPrimaryKey(String id);

    /**
     * 插入
     * @param sysNotice 
     * @return
     */
    Integer insert(SysNotice sysNotice);

    /**
     * 根据主键查询
     * @param id 
     * @return
     */
    SysNotice selectByPrimaryKey(String id);

    /**
     * 更新
     * @param sysNotice 
     * @return
     */
    Integer update(SysNotice sysNotice);

    List<SysNotice> aaa();
}
