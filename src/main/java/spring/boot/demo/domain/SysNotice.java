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
@ApiModel(value="spring.boot.demo.domain.SysNotice系统公告表")
@Data
public class SysNotice implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(value="主键")
    private String id;

    /**
     * 公告内容
     */
    @ApiModelProperty(value="公告内容")
    private String noticeText;

    /**
     * 公告状态
     */
    @ApiModelProperty(value="公告状态")
    private Byte noticeStatus;

    /**
     * 是否显示
     */
    @ApiModelProperty(value="是否显示")
    private Byte showStatus;

    /**
     * 图片地址
     */
    @ApiModelProperty(value="图片地址")
    private String noticeImages;

    /**
     * 创建时间
     */
    /**
     * @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
     * 上述代码已经在application.yml配置
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
     * 删除标识
     */
    @ApiModelProperty(value="删除标识")
    private Byte delStatus;

    private static final long serialVersionUID = 1L;
}
