package spring.boot.demo.controller;

import spring.boot.demo.domain.SysNotice;
import spring.boot.demo.response.ResponseEnum;
import spring.boot.demo.response.ResponseResult;
import spring.boot.demo.service.SysNoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Tree
 * @version V1.0
 * @Title:
 * @Description:
 * @date 2022/03/27 21:35
 */
@RestController
@Api(tags = "系统公告接口")
@RequestMapping("/sysNotice")
public class SysNoticeController {

    @Autowired
    private SysNoticeService sysNoticeService;

    @ApiOperation(value = "查询所有系统公告", notes = "")
    @ApiImplicitParams({})
    @PostMapping("/all")
    public ResponseResult<List<SysNotice>> all(SysNotice sysNotice){
        List<SysNotice> sysNotices = sysNoticeService.selectAll(sysNotice);
        return new ResponseResult<>(ResponseEnum.SUCCESS, sysNotices);
    }

    @ApiOperation(value = "查询最近一条系统公告", notes = "")
    @ApiImplicitParams({})
    @PostMapping("/freshNotice")
    public ResponseResult<SysNotice> freshNotice(){
        SysNotice sysNotices = sysNoticeService.freshNotice();
        if (sysNotices == null){
            return new ResponseResult<>(ResponseEnum.NOT_HAS_SYS_NOTICE);
        }
        return new ResponseResult<>(ResponseEnum.SUCCESS, sysNotices);
    }

    @ApiOperation(value = "删除数据", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "String", paramType = "body"),
    })
    @PostMapping("/deleteByPK")
    public ResponseResult<Integer> deleteByPrimaryKey(String id){
        Integer row = sysNoticeService.deleteByPrimaryKey(id);
        if (row == 1){
            return new ResponseResult<>(ResponseEnum.SUCCESS);
        }
        return new ResponseResult<>(ResponseEnum.DELETE_DATA_FAIL);
    }

    @ApiOperation(value = "添加数据", notes = "")
    @ApiImplicitParams({})
    @PostMapping("/insert")
    public ResponseResult<Integer> insertSelective(SysNotice sysNotice){
        Integer row = sysNoticeService.insert(sysNotice);
        if (row == 1){
            return new ResponseResult<>(ResponseEnum.SUCCESS);
        } else if (row == -1){
            return new ResponseResult<>(ResponseEnum.HAS_SENSITIVE);
        }
        return new ResponseResult<>(ResponseEnum.INSERT_DATA_FAIL);
    }

    @ApiOperation(value = "删除数据", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "String", paramType = "body"),
    })
    @PostMapping("/selectByPK")
    public ResponseResult<SysNotice> selectByPrimaryKey(String id){
        return new ResponseResult<>(ResponseEnum.SUCCESS, sysNoticeService.selectByPrimaryKey(id));
    }

    @ApiOperation(value = "更新数据", notes = "")
    @ApiImplicitParams({})
    @PostMapping("/update")
    public ResponseResult<Integer> updateByPrimaryKeySelective(SysNotice sysNotice){
        Integer row = sysNoticeService.update(sysNotice);
        if (row == 1){
            return new ResponseResult<>(ResponseEnum.SUCCESS);
        }else if (row == -1){
            return new ResponseResult<>(ResponseEnum.HAS_SENSITIVE);
        }
        return new ResponseResult<>(ResponseEnum.UPDATE_DATA_FAIL);
    }

}
