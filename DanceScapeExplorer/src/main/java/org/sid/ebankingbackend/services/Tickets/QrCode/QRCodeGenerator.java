package org.sid.ebankingbackend.services.Tickets.QrCode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.sid.ebankingbackend.entities.Ticket;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class QRCodeGenerator {

    public static void generateQRCodeImage(String data, String filePath) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 350, 350);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", java.nio.file.Paths.get(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] generateQRCodeImage(Ticket ticket) throws Exception {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();


        String data = "{" +
                "\"idTicket\":\"" + ticket.getIdTicket() + "\"," +
                "\"refTicket\":\"" + ticket.getRefTicket() + "\"," +
                "\"expireDate\":\"" + ticket.getExpireDate() + "\"," +
                "\"typeTicket\":\"" + ticket.getTypeTicket() + "\"," +
                "\"scanned\":\"" + ticket.isScanned() + "\"" +


                "}";

        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 350, 350);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        pngOutputStream.close();

        return pngOutputStream.toByteArray();
    }

}
