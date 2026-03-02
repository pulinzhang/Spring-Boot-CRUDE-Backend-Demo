package com.example.cruddemo.jasperreport;

import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v3/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    // Export NotificationLog to PDF
    @GetMapping("/notificationlog/pdf")
    public ResponseEntity<byte[]> exportNotificationLogPdf() throws JRException, IOException {
        byte[] pdfBytes = reportService.generateNotificationLogReport();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "notificationlog_report.pdf");
        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }
}

