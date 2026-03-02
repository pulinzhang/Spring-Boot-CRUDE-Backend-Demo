package com.example.cruddemo.jasperreport;

import com.example.cruddemo.enums.DevicePlatform;
import com.example.cruddemo.enums.NotificationStatus;
import com.example.cruddemo.fcm.NotificationLog;
import com.example.cruddemo.fcm.NotificationLogRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private NotificationLogRepository notificationLogRepository;

    public byte[] generateNotificationLogReport() throws JRException, IOException {
        // Get demo data (Map with all String values for demo)
        List<Map<String, String>> demoData = getDemoData();

        // Load the JasperReport template
        InputStream templateStream = new ClassPathResource(
            "templates/notificationlog_report.jrxml"
        ).getInputStream();

        JasperReport jasperReport = JasperCompileManager.compileReport(templateStream);

        // Create data source from demo data (all String values)
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(demoData);

        // Parameters for the report (Demo values)
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("reportTitle", "Notification Log Report");
        parameters.put("generatedBy", "System Admin");
        parameters.put("generatedDate", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        parameters.put("totalRecords", demoData.size());

        // Fill the report
        JasperPrint jasperPrint = JasperFillManager.fillReport(
            jasperReport,
            parameters,
            dataSource
        );

        // Export to PDF
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    /**
     * Demo data for testing the report (all values as String)
     */
    private List<Map<String, String>> getDemoData() {
        List<Map<String, String>> demoList = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Demo record 1
        Map<String, String> log1 = new HashMap<>();
        log1.put("userId", "1");
        log1.put("createtime", LocalDateTime.of(2026, 3, 1, 10, 30, 0).format(formatter));
        log1.put("title", "Welcome Notification");
        log1.put("body", "Welcome to our app! Thanks for signing up.");
        log1.put("platform", "ANDROID");
        log1.put("status", "SENT");
        log1.put("creator", "1");
        demoList.add(log1);

        // Demo record 2
        Map<String, String> log2 = new HashMap<>();
        log2.put("userId", "2");
        log2.put("createtime", LocalDateTime.of(2026, 3, 1, 11, 45, 0).format(formatter));
        log2.put("title", "Order Confirmed");
        log2.put("body", "Your order #12345 has been confirmed and is being processed.");
        log2.put("platform", "IOS");
        log2.put("status", "SENT");
        log2.put("creator", "1");
        demoList.add(log2);

        // Demo record 3
        Map<String, String> log3 = new HashMap<>();
        log3.put("userId", "3");
        log3.put("createtime", LocalDateTime.of(2026, 3, 1, 14, 20, 0).format(formatter));
        log3.put("title", "Payment Successful");
        log3.put("body", "Your payment of $99.99 has been successfully processed.");
        log3.put("platform", "ANDROID");
        log3.put("status", "PENDING");
        log3.put("creator", "2");
        demoList.add(log3);

        // Demo record 4
        Map<String, String> log4 = new HashMap<>();
        log4.put("userId", "1");
        log4.put("createtime", LocalDateTime.of(2026, 3, 1, 16, 0, 0).format(formatter));
        log4.put("title", "New Message");
        log4.put("body", "You have a new message from John Doe.");
        log4.put("platform", "WEB");
        log4.put("status", "FAILED");
        log4.put("creator", "1");
        demoList.add(log4);

        // Demo record 5
        Map<String, String> log5 = new HashMap<>();
        log5.put("userId", "5");
        log5.put("createtime", LocalDateTime.of(2026, 3, 1, 18, 30, 0).format(formatter));
        log5.put("title", "Promotion Alert");
        log5.put("body", "Get 50% off on all items this weekend only!");
        log5.put("platform", "IOS");
        log5.put("status", "SENT");
        log5.put("creator", "3");
        demoList.add(log5);

        return demoList;
    }
}
