package com.achilles.wild.server.business.controller.demo;

import com.achilles.wild.server.model.request.BaseRequest;
import com.achilles.wild.server.model.response.BaseResult;
import com.achilles.wild.server.tool.json.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/swagger", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "swagger测试")
public class SwaggerController {

    private final static Logger log = LoggerFactory.getLogger(SwaggerController.class);

    @ApiOperation(value = "增加账户",notes = "返回账户余额")
    @ApiResponses({ @ApiResponse(code = 200, message = "OK", response = BaseResult.class) })
    @PostMapping("/add")
    public BaseResult getName(@RequestBody BaseRequest request){

        log.info("-------------------request:"+ JsonUtil.toJsonString(request));

        return new BaseResult();
    }
}
