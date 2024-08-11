package com.quang.tttn.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.quang.tttn.model.entity.DistributorSeller;
import com.quang.tttn.model.entity.Product;
import com.quang.tttn.model.entity.ProductDistributor;
import com.quang.tttn.model.entity.SellerWarehouse;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class QRCodeGenerator {
    public static void supplierSend(ProductDistributor transaction) throws WriterException, IOException {
        String qrCodePath = "D:\\Java\\JavaSpringBootDataJPA\\QRCode\\Supplier\\";
        String qrCodeName = qrCodePath+"transaction"+transaction.getId()+"-QRCODE.png";
        var qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(
                "" + "http://localhost:8089/api/distributors/supplier/"+transaction.getId(),
                BarcodeFormat.QR_CODE, 400, 400);
        Path path = FileSystems.getDefault().getPath(qrCodeName);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    public static void distributorSend(DistributorSeller transaction) throws WriterException, IOException {
        String qrCodePath = "D:\\Java\\JavaSpringBootDataJPA\\QRCode\\Distributor\\";
        String qrCodeName = qrCodePath+"transaction"+transaction.getId()+"-QRCODE.png";
        var qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(
                "" + transaction.getId(),
                BarcodeFormat.QR_CODE, 400, 400);
        Path path = FileSystems.getDefault().getPath(qrCodeName);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    public static void productInfo(Product product) throws WriterException, IOException {
        String qrCodePath = "D:\\Java\\JavaSpringBootDataJPA\\QRCode\\Product\\";
        String qrCodeName = qrCodePath+"product"+product.getProductId()+"-QRCODE.png";
        var qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(
                "http://localhost:8089/api/products/" + product.getProductId(),
                BarcodeFormat.QR_CODE, 400, 400);
        Path path = FileSystems.getDefault().getPath(qrCodeName);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    public static void sellerReceived(SellerWarehouse sellerWarehouse) throws WriterException, IOException {
        String qrCodePath = "D:\\Java\\JavaSpringBootDataJPA\\QRCode\\Seller\\";
        String qrCodeName = qrCodePath+"warehouse"+sellerWarehouse.getWarehouseId()+"-QRCODE.png";
        var qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(
                "http://localhost:8089/api/transactions/" + sellerWarehouse.getWarehouseId(),
                BarcodeFormat.QR_CODE, 400, 400);
        Path path = FileSystems.getDefault().getPath(qrCodeName);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }
}
