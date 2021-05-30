package com.achilles.wild.server.tool.file.excel;

import com.achilles.wild.server.common.listener.UploadExcelListener;
import com.achilles.wild.server.model.response.common.LogFilterInfoVO;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class EasyExcelUtilTest{

    String path = "/Users/achilleswild/Desktop/Achilles.xls";
    //String path = "/Users/achilleswild/Desktop/Achilles.xlsx";

    @Test
    public void simpleRead() {
        UploadExcelListener listener =  new UploadExcelListener();
        EasyExcel.read(path, LogFilterInfoVO.class, listener).sheet().doRead();
        System.out.println(JSON.toJSONString(listener.getList()));
    }


    @Test
    public void simpleWrite(){
        List<LogFilterInfoVO> list = new ArrayList<LogFilterInfoVO>();
        for (int i = 100001; i <= 100002; i++) {
            LogFilterInfoVO vo = new LogFilterInfoVO();
//            vo.setItemId(i*3L);
//            vo.setPublishDate(DateUtil.getTheDayFirstTime(7));
//            vo.setPublishDateEnd(DateUtil.getTheDayFirstTime(9));
//            vo.setItemCount(i+100);
//            vo.setMaxUserCount(i+998);
            list.add(vo);
        }
        EasyExcel.write(path, LogFilterInfoVO.class).sheet("test").doWrite(list);
    }

    @Test
    public void simpleWriteInputStram() throws Exception{
        List<LogFilterInfoVO> list = new ArrayList<LogFilterInfoVO>();
        for (int i = 1; i <= 10; i++) {
            LogFilterInfoVO logFilterInfoVO = new LogFilterInfoVO();
//            logFilterInfoVO.setItemId(i*3L);
//            logFilterInfoVO.setName("������"+i);
//            logFilterInfoVO.setPublishDate(DateUtil.getTheDayFirstTime(7));
//            logFilterInfoVO.setPublishDateEnd(DateUtil.getTheDayFirstTime(9));
//            logFilterInfoVO.setItemCount(i+100);
//            logFilterInfoVO.setMaxUserCount(i+998);
//            logFilterInfoVO.setFire(i);
            list.add(logFilterInfoVO);
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        EasyExcel.write(outputStream, LogFilterInfoVO.class).excelType(ExcelTypeEnum.XLSX).sheet("��Ŀ").doWrite(list);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        //String str = new String(outputStream.toByteArray());
        //BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(outputStream.toByteArray()),"GBK"));
        //String str = br.readLine();
        byte[] data = new byte[4096];
        int count = -1;
        while((count = inputStream.read(data,0,4096)) != -1)
            outputStream.write(data, 0, count);

            System.out.println();
    }


    @Test
    public void simpleWriteTest()  throws Exception {
        List<LogFilterInfoVO> list = new ArrayList<LogFilterInfoVO>();
        for (int i = 100001; i <= 100003; i++) {
            LogFilterInfoVO vo = new LogFilterInfoVO();
//            vo.setItemId(i * 4L);
//            vo.setPublishDate(DateUtil.getTheDayFirstTime(7));
//            vo.setPublishDateEnd(DateUtil.getTheDayFirstTime(9));
//            vo.setItemCount(i + 100);
//            vo.setMaxUserCount(i + 998);
            list.add(vo);
        }

        byte[] array =  exportByteArray("ACHI",list, LogFilterInfoVO.class);
        //String fileContent = new String(array, "GBK");
        ByteArrayInputStream excelFile = new ByteArrayInputStream(array);
        String result = new String(array, "UTF-8");
        System.out.println(array);
    }

    private  byte[] exportByteArray(String sheetName, List<LogFilterInfoVO> dataList,
        Class clazz) throws Exception{
        // ����������Ϣ
        Map<String, List<LogFilterInfoVO>> dataListMap = Maps.newLinkedHashMap();
        // ����������뵽Excel��
        dataListMap.put(sheetName, dataList);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        writeIntoOutputStream(dataListMap, clazz, out);

       // String str = out.toString("utf-8");
       // byte[] lens = out.toByteArray();
       //
       // String result = new String(lens);

       // DataOutputStream dis = new DataOutputStream(out);
        //String newName = dis.readUTF();
        //System.out.println(dis.toString());

        return out.toByteArray();
    }


    private void writeIntoOutputStream(Map<String, List<LogFilterInfoVO>> dataListMap, Class clazz, OutputStream outputStream) {

            // ��������
            int sheetNo = 1;
            // �����ļ���
            ExcelWriter writer = EasyExcelFactory.getWriter(outputStream);
            // ѭ��д��ÿ��������
            for (Entry<String, List<LogFilterInfoVO>> entry : dataListMap.entrySet()) {
                // �õ�����������
                String sheetName = entry.getKey();
                // �õ�����������
                List<LogFilterInfoVO> dataList = entry.getValue();
                // ���ù�������Ϣ
                Sheet sheet1 = new Sheet(sheetNo++, 1, clazz, sheetName, null);
                // ��������Ӧ���
               // sheet1.setAutoWidth(Boolean.TRUE);
                // ��ʼд����
                writer.write(dataList, sheet1);
            }
            // ��ջ���
            writer.finish();

    }


}
