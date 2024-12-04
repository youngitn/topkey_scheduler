package com.topkey.scheduler.oa.quartz;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.topkey.scheduler.oa.entity.Ekp_vn_account_record;
import com.topkey.scheduler.oa.repo.VnAccountRecordRepository;

import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class VnAccountRecordJob extends QuartzJobBean {
	
    @Autowired
    private VnAccountRecordRepository srp;
    @Autowired
    private JavaMailSender mailSender;
    
    @Value("${vn.sendto.username}")
	private String sendto;
    
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
        	//StringBuilder tableContent = new StringBuilder();
            // 建立 MimeMessage
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            // 設置郵件內容
            helper.setFrom("oa@topkey.com.tw"); // 寄件人
            helper.setTo(getEmails(sendto));
            helper.setSubject("TKVN帳號權限管理_今日帳號權限到期名單"); // 主題
            
            //產生寄送內容
            StringBuilder  emailContent = generateEmailContent();
            
            // 將文字設置到 helper
            helper.setText(emailContent.toString());
           // helper.setText(emailContent, true);
            
            // 發送郵件
            mailSender.send(mimeMessage);
            log.info("VnAccountRecordJob：Email sent successfully!");                

        } catch (Exception e) {
        	String errMsg = String.format("VnAccountRecordJob：Error while sending email. Details: %s", e.getMessage());
        	log.error(errMsg, e); // 記錄詳細的錯誤堆棧信息
        	throw new JobExecutionException("Failed to send email.", e); // 保留原始異常信息
        }
    }


    private StringBuilder generateEmailContent() {
        StringBuilder tableContent = new StringBuilder();
        try {
            // 獲取當日到期資料
            List<Ekp_vn_account_record> records = srp.findTodayEndDateRecordsNative();

            if (records == null || records.isEmpty()) {
                String noDataMessage = "今日查無符合條件的資料。\n";
                tableContent.append(noDataMessage);
                log.info(noDataMessage);
                return tableContent;
            }

            // 每欄寬度（因中文字佔用的距離較大，因此欄寬設定不同。依據字數調整）
            String[] headers = {"申請單單號", "紀錄單號", "姓名", "工號", "開通時間", "預計註銷時間"};
            int[] headerWidths = {8, 9, 13, 8, 8, 8};	//各欄位名稱寬度設定
            int[] lineWidths = {21, 21, 21, 21, 21, 40};	//分隔線設定
            int[] columnWidths = {15, 15, 13, 12, 15, 13};	//各資料欄寬度設定

            // 建構表格
            appendTableRow(tableContent, headers, headerWidths, "| ");
            appendTableLine(tableContent, lineWidths);
            
            for (Ekp_vn_account_record record : records) {
                appendTableRow(tableContent, new String[]{
                    record.getSFormNo(),
                    record.getSSystemNo(),
                    record.getSName(),
                    record.getSId(),
                    safeSubstring(record.getDStartDate(), 0, 10),
                    safeSubstring(record.getDEndDate(), 0, 10)
                }, columnWidths, "");
            }
            log.info("VnAccountRecordJob 郵件內容生成成功：\n{}", tableContent);
        } catch (Exception e) {
            // 捕捉並記錄詳細錯誤訊息
            log.error("VnAccountRecordJob 在生成郵件內容時發生錯誤：", e);
            tableContent.append("VnAccountRecordJob 生成郵件內容時發生錯誤，請聯絡管理員檢查系統日誌。\n");
        }
        return tableContent;
    }

    /**
     * 產生Table欄位
     * */
    private void appendTableRow(StringBuilder sb, String[] values, int[] widths, String delimiter) {
        for (int i = 0; i < values.length; i++) {
            sb.append(String.format("%-" + widths[i] + "s", values[i])).append(delimiter);
        }
        sb.append("\n");
    }

    /**
     * 分隔線生成
     * */
    private void appendTableLine(StringBuilder sb, int[] widths) {
        for (int width : widths) {
            sb.append("-".repeat(width));
        }
        sb.append("\n");
    }

    /**
     * 防空檢查：新增 safeSubstring 函式，避免處理空值或長度不足的字串時出現異常。
     * */
    private String safeSubstring(String input, int start, int end) {
        if (input == null || input.length() < start) {
            return "";
        }
        return input.substring(start, Math.min(input.length(), end));
    }
    
    public String[] getEmails(String sendto) {
        if (sendto == null || sendto.isEmpty()) {
            return new String[0]; // 或許你想回傳 null 或拋出異常
        }

        String[] names = sendto.split(",");
        String[] emails = new String[names.length];

        for (int i = 0; i < names.length; i++) {
            emails[i] = names[i] + "@topkey.com.tw";
        }

        return emails;
    }


}
