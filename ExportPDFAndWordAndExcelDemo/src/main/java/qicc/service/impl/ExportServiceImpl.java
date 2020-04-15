package qicc.service.impl;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.util.IOUtils;
import org.springframework.stereotype.Service;
import qicc.model.ResultModel;
import qicc.service.ExportService;
import qicc.util.EasyPoiUtil;
import qicc.util.PDFTemplateUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

@Service
@Slf4j
public class ExportServiceImpl implements ExportService {

    //public static final Version DEFAULT_INCOMPATIBLE_IMPROVEMENTS = Configuration.VERSION_2_3_0;

    @Override
    public void export(Integer type, HttpServletResponse response) {

        List<ResultModel> list = moni();
        // type 1：pdf，2：word，3，excel
        if(type == 1){
            exportPdf(list,response);
        }else if(type == 2){
            exportWord(list,response);
        }else if(type == 3){
            exportExcel(list,response);
        }
    }

    // 导出excel
    private void exportExcel(List<ResultModel> list,HttpServletResponse response){
        EasyPoiUtil.exportExcel(list,"Title","sheetName",ResultModel.class,"fileName"+".xls",true,response);
    }

    // html模板导出pdf
    private void exportPdf(List<ResultModel> list,HttpServletResponse response){
        Map<String,Object>  data = new HashMap<>();
        data.put("replyList",list);
        ByteArrayOutputStream baos  = null;
        String fileName = null;
        try {
            // 设置响应消息头，告诉浏览器当前响应是一个下载文件
            response.setContentType( "application/x-msdownload");
            // 告诉浏览器，当前响应数据要求用户干预保存到文件中，以及文件名是什么 如果文件名有中文，必须URL编码
            fileName = URLEncoder.encode("处置记录.pdf", "UTF-8");
            response.setHeader( "Content-Disposition", "attachment;filename=" + fileName);
            baos = PDFTemplateUtil.createPDF(data,"replyModel.html");
            baos.writeTo(response.getOutputStream());
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ftl模板导出word
    private void exportWord(List<ResultModel> list,HttpServletResponse response){
        Map<String,Object>  data = new HashMap<>();
        data.put("replyList",list);

        Configuration configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        configuration.setDefaultEncoding("utf-8");
        configuration.setClassForTemplateLoading(this.getClass(), "/temp");
        File file = null;
        InputStream input = null;
        String fileName = null;
        try {
            response.setContentType( "application/x-msdownload");
            fileName = URLEncoder.encode("处置记录.doc", "UTF-8");
            response.setHeader( "Content-Disposition", "attachment;filename=" + fileName);

            file = new File("111.doc");
            Writer w = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
            Template freemarkerTemplate = configuration.getTemplate("replyModel.ftl");
            freemarkerTemplate.process(data,w);
            input = new FileInputStream(file);
            IOUtils.copy(input,response.getOutputStream());
            input.close();
            w.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(file.exists()){
                log.info("删除临时文件:"+file.getAbsolutePath());
                file.delete();
            }
        }
    }


    // 模拟数据
    private List<ResultModel> moni(){
        List<ResultModel> list = new ArrayList<>();
        ResultModel r1 = new ResultModel("XXX公安局警务指挥部指挥调度中心","警员6336",new Date(),"警情流水号：20200408T01704703的指令任务超时。");
        ResultModel r2 = new ResultModel("XXX公安局XX分局","警员1234",new Date(),"警员1234签收了一条指令任务。");
        ResultModel r3 = new ResultModel("XXX公安局XX分局","警员1234",new Date(),"【XXX公安局XXX分局-警员1234】收到新建的指令！");
        ResultModel r4 = new ResultModel("XXX公安局XX分局","警员6336",new Date(),"【XXX公安局XXX分局-警员1234】收到新建的指令！");
        ResultModel r5 = new ResultModel("XXX公安局警务指挥部指挥调度中心","警员6336",new Date(),"XXX公安局警务指挥部指挥调度中心给XXX公安局XXX分局发送了一条指令。");
        list.add(r1);
        list.add(r2);
        list.add(r3);
        list.add(r4);
        list.add(r5);
        return list;
    }
}
