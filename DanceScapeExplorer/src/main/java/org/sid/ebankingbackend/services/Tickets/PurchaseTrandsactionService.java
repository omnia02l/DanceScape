package org.sid.ebankingbackend.services.Tickets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.aspectj.lang.annotation.Aspect;
import org.sid.ebankingbackend.entities.PurchaseTransaction;

import org.sid.ebankingbackend.entities.Ticket;
import org.sid.ebankingbackend.repository.Tickets.PurchaseTransactionRepository;
import org.sid.ebankingbackend.repository.Tickets.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class PurchaseTrandsactionService implements IPurchaseTransactionService{

 PurchaseTransactionRepository purchaseTransactionRepository;
    @Autowired
    TicketRepository ticketRepository;

    @Override
    public List<PurchaseTransaction> GetAllPurchaseTransaction() {
        return purchaseTransactionRepository.findAll();
    }

    @Override
    public PurchaseTransaction GetPurchaseTransaction(Long id) {
        return purchaseTransactionRepository.findById(id).get();
    }

    @Override
    public PurchaseTransaction AddPurchaseTransaction(PurchaseTransaction purchaseTransaction) {
        return purchaseTransactionRepository.save(purchaseTransaction);
    }

    @Override
    public PurchaseTransaction ModifyPurchaseTransaction(PurchaseTransaction purchaseTransaction) {
        return purchaseTransactionRepository.save(purchaseTransaction);
    }

    @Override
    public void DeletPurchaseTransaction(Long id) {
        purchaseTransactionRepository.deleteById(id);

    }
    public List<Ticket> processPDF(MultipartFile file) throws Exception {
        List<Ticket> tickets = new ArrayList<>();
        InputStream inputStream = file.getInputStream();
        PDDocument document = PDDocument.load(inputStream);
        PDFRenderer pdfRenderer = new PDFRenderer(document);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            for (int page = 0; page < document.getNumberOfPages(); page++) {
                BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bim)));
                try {
                    Result result = new MultiFormatReader().decode(bitmap);
                    Ticket ticket = objectMapper.readValue(result.getText(), Ticket.class);
                    tickets.add(ticket);
                } catch (NotFoundException e) {
                    log.error("QR Code not found on page " + page + ": " + e.getMessage());
                } catch (JsonProcessingException e) {
                    log.error("Error processing QR Code JSON on page " + page + ": " + e.getMessage());
                }
            }
        } finally {
            document.close();
            inputStream.close();
        }
        return tickets;
    }

/*
    public List<String> processPDF(MultipartFile file) throws Exception {
        List<String> qrDataList = new ArrayList<>();
        InputStream inputStream = file.getInputStream();
        PDDocument document = PDDocument.load(inputStream);
        PDFRenderer pdfRenderer = new PDFRenderer(document);

        try {
            for (int page = 0; page < document.getNumberOfPages(); page++) {
                BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(
                        new BufferedImageLuminanceSource(bim)));
                try {
                    Result result = new MultiFormatReader().decode(bitmap);
                    qrDataList.add(result.getText());
                    // Ne pas inclure de break ici pour s'assurer que toutes les pages sont traitées
                } catch (NotFoundException e) {
                    // Loggez l'exception et continuez à traiter les autres pages
                    log.error("QR Code not found on page " + page + ": " + e.getMessage());
                }
            }
        } finally {
            document.close();
            inputStream.close();
        }
        return qrDataList;
    }

*/
    public boolean checkTicketScanned(String ticketReference) {
        Ticket ticket = ticketRepository.findByRefTicket(ticketReference);
      return ticket.isScanned() ;
    }
}


