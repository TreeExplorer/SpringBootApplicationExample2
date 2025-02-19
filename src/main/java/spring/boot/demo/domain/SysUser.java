package spring.boot.demo.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author Tree
 * @version V1.0
 * @Title:
 * @Description:
 * @date 2022/3/29 16:41
 */
@ApiModel(value="spring.boot.demo.domain.SysUser系统用户表")
@Data
public class SysUser implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(value="主键")
    private String id;

    /**
     * 账号
     */
    @ApiModelProperty(value="账号")
    private String account;

    /**
     * 密码
     */
    @ApiModelProperty(value="密码")
    private String password;

    /**
     * 用户名
     */
    @ApiModelProperty(value="用户名")
    private String userName;

    /**
     * 性别
     */
    @ApiModelProperty(value="性别")
    private Byte sex;

    /**
     * 生日
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value="生日")
    private Date birthday;

    /**
     * 头像地址
     */
    @ApiModelProperty(value="头像地址")
    private String avatarAddress;

    /**
     * 简介
     */
    @ApiModelProperty(value="简介")
    private String introduce;

    /**
     * 用户角色
     */
    @ApiModelProperty(value="用户角色")
    private Integer userRole;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value="更新时间")
    private Date updateTime;

    /**
     * 删除标志
     */
    @ApiModelProperty(value="删除标志")
    private Byte delStatus;

    private static final long serialVersionUID = 1L;
}
