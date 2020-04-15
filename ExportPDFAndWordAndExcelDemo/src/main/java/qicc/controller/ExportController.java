package qicc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import qicc.service.ExportService;

import javax.servlet.http.HttpServletResponse;

@RestController
public class ExportController {

    @Autowired
    private ExportService exportService;

    @GetMapping("/download/{type}")
    public void export(@PathVariable(value = "type", required = true) Integer type, HttpServletResponse response){
    // type 1：pdf，2：word，3，excel
        exportService.export(type,response);
    }
}
