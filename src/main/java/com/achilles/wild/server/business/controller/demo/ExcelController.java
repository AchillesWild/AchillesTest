package com.achilles.wild.server.business.controller.demo;

import com.achilles.wild.server.common.listener.UploadExcelListener;
import com.achilles.wild.server.common.aop.log.annotation.IgnoreParams;
import com.achilles.wild.server.model.response.common.LogFilterInfoVO;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/excel",produces = {"application/json;charset=UTF-8"})
public class ExcelController {

    private final static Logger LOG = LoggerFactory.getLogger(ExcelController.class);


    @PostMapping("/upload2")
    @IgnoreParams
    public String upload2(){

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        MultipartResolver multipartResolver = new StandardServletMultipartResolver();
        MultipartHttpServletRequest multipartHttpServletRequest = multipartResolver.resolveMultipart(request);
        Map<String, MultipartFile> fileMap = multipartHttpServletRequest.getFileMap();
        MultipartFile file = fileMap.get("file");

        UploadExcelListener listener = new UploadExcelListener();
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            EasyExcel.read(inputStream,  LogFilterInfoVO.class,listener).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<LogFilterInfoVO> list = listener.getList();
        LOG.info("list==========="+ JSON.toJSONString(list));
        return "success";
    }

    @PostMapping("/upload")
    @IgnoreParams
    public String upload(MultipartFile file){

        UploadExcelListener listener = null;
        InputStream inputStream = null;
        try {
            //String key = DigestUtils.md5Hex(file.getInputStream());
            LOG.info("========file.getName()="+ file.getName()+",file.getSize()="+file.getSize()+",file.getContentType()="+file.getContentType());
            LOG.info("========key=====111111===="+ DigestUtils.md5Hex(file.getName()+file.getSize()+file.getContentType()));
            LOG.info("========key=====222222===="+ DigestUtils.md5Hex(file.getInputStream()));

            inputStream = file.getInputStream();

            listener = new UploadExcelListener();
            EasyExcel.read(inputStream,  LogFilterInfoVO.class,listener).sheet().doRead();

        } catch (Exception e) {
            e.printStackTrace();
        }
        List<LogFilterInfoVO> list = listener.getList();
        LOG.info("list==========="+ JSON.toJSONString(list));
        return "success";
    }


    @GetMapping("/download")
    @IgnoreParams
    public void download(HttpServletResponse response) throws Exception {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("test", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename="+fileName+".xlsx");
        EasyExcel.write(response.getOutputStream(), LogFilterInfoVO.class).sheet("第一个sheet").doWrite(data());;
    }


    protected List<?> data() {
        List<LogFilterInfoVO> rowList = new ArrayList<>();
        LogFilterInfoVO vo = new LogFilterInfoVO();
        vo.setUri("/demo/id/34");
        vo.setTime(12);
        rowList.add(vo);
        LogFilterInfoVO vo2 = new LogFilterInfoVO();
        vo2.setUri("/account/id/34");
        vo2.setTime(12);
        rowList.add(vo2);
        return rowList;

    }
}
