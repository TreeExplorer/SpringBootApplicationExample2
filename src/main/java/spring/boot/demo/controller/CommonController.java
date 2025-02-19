package spring.boot.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import spring.boot.demo.response.ResponseEnum;
import spring.boot.demo.response.ResponseResult;
import spring.boot.demo.service.CommonService;

/**
 * @author Tree
 * @version V1.0
 * @Title:
 * @Description:
 * @date 2022/3/15 23:19
 */
@RestController
@Api(tags = "公共接口")
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private CommonService commonService;

    @ApiOperation(value = "上传文件并返回文件地址（单个）", notes = "")
    @ApiImplicitParams({})
    @PostMapping("/saveFile")
    public ResponseResult<String> saveFile(@RequestParam(value = "file") MultipartFile file){
        String fileAddress = commonService.saveFiles(file);
        if (StringUtils.isEmpty(fileAddress)){
            return new ResponseResult<>(ResponseEnum.INSERT_FILE_FAIL);
        }
        return new ResponseResult<>(ResponseEnum.SUCCESS, fileAddress);
    }

    @ApiOperation(value = "上传文件并返回文件地址（多个）", notes = "")
    @ApiImplicitParams({})
    @PostMapping("/saveFiles")
    public ResponseResult<String> saveFiles(@RequestParam(value = "files") MultipartFile[] files){
        String fileAddress = commonService.saveFiles(files);
        if (StringUtils.isEmpty(fileAddress)){
            return new ResponseResult<>(ResponseEnum.INSERT_FILE_FAIL);
        }
        return new ResponseResult<>(ResponseEnum.SUCCESS, fileAddress);
    }
}
