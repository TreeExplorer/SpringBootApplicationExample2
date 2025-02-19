package spring.boot.demo.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Calendar;

/**
 * @author Tree
 * @version V1.0
 * @Title:
 * @Description:
 * @date 2022/3/2 20:08
 */
public class FileUtil {

    public static String file2Url(MultipartFile[] files){
        String fileUrl = "";
        if (files == null) {
            return null;
        }
        for (MultipartFile file : files) {
            fileUrl += springUploadFile(file) + ",";
        }
        return fileUrl;
    }

    public static String springUploadFile(MultipartFile file) {
        String fileUrl = "";
        try {
            // 文件后缀过滤，只允许部分后缀
            // 文件的完整名称,如spring.jpeg
            String filename = file.getOriginalFilename();
            // 文件名,如spring
            String name = filename.substring(0, filename.indexOf("."));
            // 文件后缀,如.jpeg
            String suffix = filename.substring(filename.lastIndexOf("."));

            // 创建文件目录
            // 创建年月文件夹
            Calendar date = Calendar.getInstance();
            int year = date.get(Calendar.YEAR);
            int month = date.get(Calendar.MONTH) + 1;
            int day = date.get(Calendar.DATE);
            File dateDirs = new File(year
                    + File.separator + month + File.separator + day);

            // 目标文件
            File descFile = new File(VarsUtil.rootPath + File.separator + dateDirs + File.separator + filename);

            int i = 1;
            // 若文件存在重命名
            String newFilename = filename;
            while (descFile.exists()) {
                newFilename = name + "(" + i + ")" + suffix;
                String parentPath = descFile.getParent();
                descFile = new File(parentPath + File.separator + newFilename);
                i++;
            }

            // 判断目标文件所在的目录是否存在
            if (!descFile.getParentFile().exists()) {
                //如果目标文件所在的目录不存在，则创建父目录
                descFile.getParentFile().mkdirs();
            }

            // 存储文件
            file.transferTo(descFile);

            // 完整的url
            fileUrl = "/download/" + year + "/" + month + "/" + day + "/" + newFilename;

        } catch (Exception e) {
            fileUrl = "/download/404.jpg,";
            throw new Exception(e);
        } finally {
            return fileUrl;
        }

    }
}
