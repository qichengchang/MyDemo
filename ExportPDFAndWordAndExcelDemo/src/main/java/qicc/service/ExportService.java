package qicc.service;

import javax.servlet.http.HttpServletResponse;

public interface ExportService {
    public void export(Integer type, HttpServletResponse response);
}
