package com.dev.swagger.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName : parameter  //类名
 * @Description : 参数实例  //描述
 *
 * @Date: 2020-11-18 13:42  //时间
 */
@Data
@Schema(allowableValues = "参数实例")
public class Parameter implements Serializable {

    @Schema(name = "用户的当前工作目录")
    private String userDir;

    @Schema(title = "用户的当前工作目录")
    private String userName;

    @Schema(allowableValues = "用户的当前工作目录")
    private String userHome;

    @Schema(allowableValues = "用户的当前工作目录")
    private String javaHome;

    @Schema(allowableValues = "用户的当前工作目录")
    private String javaVersion;

    @Schema(allowableValues = "用户的当前工作目录")
    private String osName;

    @Schema(allowableValues = "用户的当前工作目录")
    private String osVersion;
}
