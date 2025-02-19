package spring.boot.demo.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Tree
 * @version V1.0
 * @Title:
 * @Description:
 * @date 2022/3/16 22:29
 */
public interface CommonService {

    /**
     * 保存文件
     * @param files
     * @return 图片保存路径
     */
    String saveFiles(MultipartFile... files);

}
