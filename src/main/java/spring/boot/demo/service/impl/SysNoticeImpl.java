package spring.boot.demo.service.impl;

import spring.boot.demo.dao.one.SysNoticeDao;
import spring.boot.demo.domain.SysNotice;
import spring.boot.demo.service.SysNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.boot.demo.util.SensitiveFilterUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Tree
 * @version V1.0
 * @Title:
 * @Description:
 * @date 2022/3/29 16:42
 */
@Service
public class SysNoticeImpl implements SysNoticeService {

    @Autowired
    private SysNoticeDao sysNoticeDao;

    @Override
    public List<SysNotice> selectAll(SysNotice sysNotice) {
        return sysNoticeDao.selectFilterSysNotice(sysNotice);
    }

    @Override
    public SysNotice freshNotice() {
        SysNotice sysNotice = new SysNotice();
        sysNotice.setNoticeStatus((byte)1);
        sysNotice.setShowStatus((byte)1);
        List<SysNotice> sysNotices = sysNoticeDao.selectFilterSysNotice(sysNotice);
        if (sysNotices.size() > 0){
            return sysNotices.get(0);
        }
        return null;
    }

    @Override
    public Integer deleteByPrimaryKey(String id) {
        return sysNoticeDao.deleteByPrimaryKey(id);
    }

    @Override
    public Integer insert(SysNotice sysNotice) {
        sysNotice.setId(UUID.randomUUID().toString());
        String noticeText = sysNotice.getNoticeText();
        SensitiveFilterUtil sensitiveFilter = SensitiveFilterUtil.getInstance();
        Boolean sensitiveFlag = sensitiveFilter.hasSensitive(noticeText);
        if (sensitiveFlag){
            return -1;
        }
        return sysNoticeDao.insertSelective(sysNotice);
    }

    @Override
    public SysNotice selectByPrimaryKey(String id) {
        return sysNoticeDao.selectByPrimaryKey(id);
    }

    @Override
    public Integer update(SysNotice sysNotice) {
        String noticeText = sysNotice.getNoticeText();
        SensitiveFilterUtil sensitiveFilter = SensitiveFilterUtil.getInstance();
        Boolean sensitiveFlag = sensitiveFilter.hasSensitive(noticeText);
        if (sensitiveFlag){
            return -1;
        }
        return sysNoticeDao.updateByPrimaryKeySelective(sysNotice);
    }

    @Override
    public List<SysNotice> aaa(){
        Map<String, String> map = new HashMap<>();
        List<SysNotice> sysNotices = sysNoticeDao.selectByPrimaryKey1(map);
        return sysNotices;
    }
}
