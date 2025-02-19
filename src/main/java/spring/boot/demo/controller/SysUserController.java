package spring.boot.demo.controller;

import spring.boot.demo.domain.SysUser;
import spring.boot.demo.response.ResponseEnum;
import spring.boot.demo.response.ResponseResult;
import spring.boot.demo.service.SysUserService;
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
 * @date 2022/02/27 21:35
 */
@RestController
@Api(tags = "用户接口")
@RequestMapping("/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService userService;

    @ApiOperation(value = "查询所有用户", notes = "")
    @ApiImplicitParams({})
    @PostMapping("/all")
    public ResponseResult<List<SysUser>> all(SysUser sysUser){
        return new ResponseResult<>(ResponseEnum.SUCCESS, userService.selectFilterSysUser(sysUser));
    }

    @ApiOperation(value = "删除数据", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "String", paramType = "body"),
    })
    @PostMapping("/deleteByPK")
    public ResponseResult<Integer> deleteByPrimaryKey(String id){
        Integer row = userService.deleteByPrimaryKey(id);
        if (row == 1){
            return new ResponseResult<>(ResponseEnum.SUCCESS);
        }
        return new ResponseResult<>(ResponseEnum.DELETE_DATA_FAIL);
    }

    @ApiOperation(value = "添加数据", notes = "")
    @ApiImplicitParams({})
    @PostMapping("/insert")
    public ResponseResult<String> insertSelective(SysUser sysUser){
        Integer row = userService.insert(sysUser);
        if (row == 1){
            return new ResponseResult<>(ResponseEnum.SUCCESS);
        }
        return new ResponseResult<>(ResponseEnum.INSERT_DATA_FAIL);
    }

    @ApiOperation(value = "用户注册", notes = "")
    @ApiImplicitParams({})
    @PostMapping("/register")
    public ResponseResult<String> register(SysUser sysUser){
        Integer register = userService.insertRegister(sysUser);
        if (register == -1) {
            return new ResponseResult<>(ResponseEnum.ACCOUNT_EXIST);
        }
        if (register == 1) {
            return new ResponseResult<>(ResponseEnum.SUCCESS);
        }
        if (register == 0) {
            return new ResponseResult<>(ResponseEnum.REGISTER_FAIL);
        }
        return new ResponseResult<>(ResponseEnum.ERROR);
    }

    @ApiOperation(value = "根据主键查询", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "String", paramType = "body"),
    })
    @PostMapping("/selectByPK")
    public ResponseResult<SysUser> selectByPrimaryKey(String id){
        return new ResponseResult<>(ResponseEnum.SUCCESS, userService.selectByPrimaryKey(id));
    }

    @ApiOperation(value = "查询账号是否存在", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "账号（手机号）", required = true, dataType = "String", paramType = "body"),
    })
    @PostMapping("/selectByAccount")
    public ResponseResult<SysUser> selectByAccount(String account){
        List<SysUser> users = userService.selectByAccount(account);
        if (users.size() > 0){
            return new ResponseResult<>(ResponseEnum.ACCOUNT_EXIST);
        }
        return new ResponseResult<>(ResponseEnum.SUCCESS);
    }

    @ApiOperation(value = "更新数据", notes = "")
    @ApiImplicitParams({})
    @PostMapping("/update")
    public ResponseResult<Integer> updateByPrimaryKeySelective(SysUser sysUser){
        Integer row = userService.updateByPrimaryKeySelective(sysUser);
        if (row == 1){
            return new ResponseResult<>(ResponseEnum.SUCCESS);
        }
        return new ResponseResult<>(ResponseEnum.UPDATE_DATA_FAIL);
    }

    @ApiOperation(value = "用户登录", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "账号（手机号）", required = true, dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "userRole", value = "用户角色1.普通用户2.商家3.管理员", required = true, dataType = "String", paramType = "body"),
    })
    @PostMapping("/login")
    public ResponseResult<SysUser> login(String account, String password, Integer userRole){
        SysUser login = userService.login(account, password, userRole);
        if (login == null) {
            return new ResponseResult<>(ResponseEnum.NOT_THIS_USER);
        }
        return new ResponseResult<>(ResponseEnum.SUCCESS, login);
    }

}
