package com.party.admin.biz.asynexport;

import com.google.common.util.concurrent.*;
import com.party.admin.biz.crowdfund.RepresentBizService;
import com.party.admin.utils.excel.ExportExcel;
import com.party.admin.web.dto.output.crowdfund.ProjectForActivityOutput;
import com.party.common.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * Created by wei.li
 *
 * @date 2017/7/18 0018
 * @time 14:40
 */

@Service
public class AsynExportBizService {

    ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(500));

    private static Logger logger = LoggerFactory.getLogger(AsynExportBizService.class);

    public void export(String title, Class<?> clazz, List<?> list, String realPath, IAsynExportService asynExportService){
        ListenableFuture future = service.submit(new Callable() {
            @Override
            public Boolean call() throws Exception {
                //导出
                FileUtils.createDirectory(realPath);
                new ExportExcel(title, clazz).setDataList(list).writeFile(realPath + title).dispose();
                return true;
            }
        });

        //回调
        Futures.addCallback(future, new FutureCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean b) {
                asynExportService.callBack(b);
            }

            @Override
            public void onFailure(Throwable throwable) {
                asynExportService.callBack(false);
                logger.error("异步导出异常", throwable);
            }
        });
    }
}
