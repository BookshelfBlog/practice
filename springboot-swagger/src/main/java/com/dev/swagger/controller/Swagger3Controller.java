package com.dev.swagger.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName : Swagger3Controller  //类名
 * @Description : 整合swagger3  //描述
 *
 * @Date: 2020-11-18 13:06  //时间
 */
@Tag(name = "我是name",description = "我是description")
@RestController
@RequestMapping({"swagger3"})
public class Swagger3Controller {

    @Operation(tags = "我是tags", method = "我是method", description = "我是description", summary = "我是summary")
    @GetMapping({"getNameAll"})
    public ResponseEntity getNameAll() {
        return ResponseEntity.ok(System.getProperties());
    }

    @Operation(tags = "我是tags", method = "我是method", description = "我是description", summary = "我是summary")
    @PostMapping({"getName"})
    @Parameter(name = "key", required = true, in = ParameterIn.QUERY)
    public ResponseEntity getName(String key) {
        return ResponseEntity.ok(System.getProperty(key));
    }

    @Operation(tags = "我是tags", method = "我是method", description = "我是description", summary = "我是summary")
    @PutMapping({"editNames"})
    @Parameter(name = "key", required = true, in = ParameterIn.QUERY, example = "我是example")
    public ResponseEntity editNames(String key) {
        return ResponseEntity.ok(System.getProperty(key));
    }

    @Operation(tags = "我是tags", method = "我是method", description = "我是description", summary = "我是summary")
    @PutMapping({"editNames"})
    @Parameters({
            @Parameter(name = "userDir", required = true, in = ParameterIn.QUERY, example = "我是example"),
            @Parameter(name = "userName", required = true, in = ParameterIn.QUERY, example = "我是example")
    })
    public ResponseEntity editNames(com.dev.swagger.entity.Parameter key) {
        return ResponseEntity.ok(key);
    }
}
