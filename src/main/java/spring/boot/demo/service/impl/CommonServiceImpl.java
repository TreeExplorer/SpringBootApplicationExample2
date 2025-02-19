package spring.boot.demo.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import spring.boot.demo.service.CommonService;
import spring.boot.demo.util.FileUtil;

/**
 * @author Tree
 * @version V1.0
 * @Title:
 * @Description:
 * @date 2022/3/16 22:31
 */
@Service
public class CommonServiceImpl implements CommonService {

    @Override
    public String saveFiles(MultipartFile... files) {
        String fileUrl = FileUtil.file2Url(files);
        fileUrl = StringUtils.isEmpty(fileUrl) ? "" : fileUrl;
        return fileUrl;
    }
}
