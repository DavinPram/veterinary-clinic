package com.enigma.veterinaryclinic.service.impl;

import com.enigma.veterinaryclinic.dto.QueueDTO;
import com.enigma.veterinaryclinic.dto.TransactionDTO;
import com.enigma.veterinaryclinic.dto.TransactionDetailReportDTO;
import com.enigma.veterinaryclinic.dto.TransactionReportDTO;
import com.enigma.veterinaryclinic.entity.Product;
import com.enigma.veterinaryclinic.entity.Transaction;
import com.enigma.veterinaryclinic.entity.TransactionDetail;
import com.enigma.veterinaryclinic.exception.BadRequestException;
import com.enigma.veterinaryclinic.exception.NotFoundException;
import com.enigma.veterinaryclinic.repository.TransactionDetailRepository;
import com.enigma.veterinaryclinic.repository.TransactionRepository;
import com.enigma.veterinaryclinic.service.ProductService;
import com.enigma.veterinaryclinic.service.TransactionDetailService;
import com.enigma.veterinaryclinic.service.TransactionService;
import com.enigma.veterinaryclinic.specification.QueueSpecification;
import com.enigma.veterinaryclinic.specification.TransactionSpecification;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private TransactionDetailService transactionDetailService;

    @Autowired
    private TransactionDetailRepository transactionDetailRepository;

    @Override
    public Transaction transaction(Transaction transaction) {
        scheduleValidation(transaction);

        Transaction save = transactionRepository.save(transaction);
        Integer sumPrice = 0;
        Integer sumDuration = 0;

        for (TransactionDetail transactionDetail : save.getTransactionDetails()) {
            Product product = productService.getProductById(transactionDetail.getProduct().getId());

            if (product.getCategory().getName().equalsIgnoreCase("Product")) {

                if (product.getStock() - transactionDetail.getQuantity() >= 0) {
                    product.setStock(product.getStock() - transactionDetail.getQuantity());
                    productService.update(product);
                } else {
                    throw new BadRequestException("Product out of stock");
                }

            }

            transactionDetail.setTransaction(save);
            transactionDetail.setProduct(product);
            transactionDetail.setProductPrice(product.getPrice());

            sumPrice += (transactionDetail.getProductPrice() * transactionDetail.getQuantity());
            sumDuration += (transactionDetail.getProduct().getDuration() * transactionDetail.getQuantity());

            transactionDetailService.create(transactionDetail);

        }
        save.setTotalDuration(sumDuration);
        save.setTotalPrice(sumPrice);

        return save;
    }

    @Override
    public Transaction getTransactionById(String id) {
        return findByIdOrThrowNotFound(id);
    }

    @Override
    public Transaction update(Transaction transaction) {
        Transaction lastTransaction = getTransactionById(transaction.getId());

        Integer indexTransactionDetail = 0;
        Integer sumPrice = 0;

        for (TransactionDetail transactionDetail : transaction.getTransactionDetails()) {
            Product product = productService.getProductById(transactionDetail.getProduct().getId());

            if (product.getCategory().getName().equalsIgnoreCase("Product")) {

                if (!lastTransaction.getTransactionDetails().get(indexTransactionDetail)
                        .getQuantity().equals(transactionDetail.getQuantity())) {

                    if (product.getStock() - transactionDetail.getQuantity() >= 0) {
                        product.setStock(product.getStock() - (transactionDetail.getQuantity() - lastTransaction
                                .getTransactionDetails().get(indexTransactionDetail).getQuantity()));
                        productService.update(product);
                    } else {
                        throw new BadRequestException("Product out of stock");
                    }

                }
            }
            transactionDetail.setTransaction(transaction);
            transactionDetail.setProduct(product);
            transactionDetail.setProductPrice(product.getPrice());

            sumPrice += (transactionDetail.getProductPrice() * transactionDetail.getQuantity());

            transactionDetailService.create(transactionDetail);
            indexTransactionDetail++;
        }
        transaction.setTotalPrice(sumPrice);

        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction payment(String id) {
        Transaction transaction = findByIdOrThrowNotFound(id);
        transaction.setPayment(true);
        return transactionRepository.save(transaction);
    }

    @Override
    public Page<Transaction> listWithPage(Pageable pageable, TransactionDTO transactionDTO) {
        Specification<Transaction> specification = TransactionSpecification.getSpecification(transactionDTO);
        return transactionRepository.findAll(specification, pageable);
    }

    @Override
    public Page<Transaction> queue(Pageable pageable, QueueDTO queueDTO) {
        Specification<Transaction> specification = QueueSpecification.getSpecification((queueDTO));
        return transactionRepository.findAll(specification, pageable);
    }

    @Override
    public String exportReport(String reportFormat) throws FileNotFoundException, JRException {
        String path = "C:\\Users\\Public\\Downloads";
        List<Transaction> sort = transactionRepository.findAll();

//        TransactionReportDTO satu = new TransactionReportDTO(
//                "customer1",
//                "doctor",
//                10000,
//                new Date(),
//                "namaproduct",
//                999,
//                9999
//        );
//        TransactionReportDTO dua = new TransactionReportDTO(
//                "customer1",
//                "doctor",
//                10000,
//                new Date(),
//                "namaproductsatu",
//                999,
//                9999
//        );
//        transactions.add(satu);
//        transactions.add(dua);
        List<TransactionReportDTO> transactions = new ArrayList<>();
        for (Transaction transaction: sort) {
            List<TransactionDetail> transactionDetails = transaction.getTransactionDetails();
            for (TransactionDetail transactionDetail : transactionDetails) {
                TransactionReportDTO transactionReportDTO = new TransactionReportDTO(
                        transaction.getCustomer().getName(),
                        transaction.getDoctor().getName(),
                        transaction.getTotalPrice(),
                        transaction.getDate(),
                        transactionDetail.getProduct().getName(),
                        transactionDetail.getQuantity(),
                        transactionDetail.getQuantity() * transactionDetail.getProductPrice()
                );
                transactions.add(transactionReportDTO);
            }

//            TransactionReportDTO transactionReportDTO = new TransactionReportDTO(
//                    transaction.getCustomer().getName(),
//                    transaction.getDoctor().getName(),
//                    transaction.getTotalPrice(),
//                    transaction.getDate(),
//                    transaction.getTransactionDetails(),
//                    null,
//                    null
//                    );
//            transactions.add(transactionReportDTO);
        }

        //load file and compile it
        File file = ResourceUtils.getFile("classpath:transactionreport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(transactions);
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("createdBy","Team 5 KUDDASYI");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters,dataSource);
        if (reportFormat.equalsIgnoreCase("html")){
            JasperExportManager.exportReportToHtmlFile(jasperPrint,path+"\\transactionreport.html");
        }
        if (reportFormat.equalsIgnoreCase("pdf")){
            JasperExportManager.exportReportToHtmlFile(jasperPrint,path+"\\transactionreport.pdf");
        }
        return "report generated in path : " + path;
    }

    private Transaction findByIdOrThrowNotFound(String id) {
        Optional<Transaction> transaction = this.transactionRepository.findById(id);
        if (transaction.isPresent()) {
            return transaction.get();
        } else {
            throw new NotFoundException("Transaction not found!");
        }
    }

    private void scheduleValidation(Transaction transaction){
        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        Date trxDate = new Date(transaction.getDate().getTime() - (420 * 60000));

        Integer openTime = 8;
        Integer closeTime = 17;

        cal.setTime(trxDate);
        Integer trxTime = cal.get(Calendar.HOUR_OF_DAY);

        if (trxDate.before(now)) {
            throw new NotFoundException("Please enter the correct datetime!");
        }

        if(trxTime < openTime || trxTime > closeTime){
            throw new NotFoundException("Store is closed!");
        }

        List<Transaction> listTransaction = transactionRepository.findAll();

        for (Transaction list : listTransaction) {
            if (list.getDate() != null && list.getTotalDuration() != null) {
                Date trxDatabaseTimeStart = new Date(list.getDate().getTime() - (420 * 60000));
                Date trxDatabaseTimeEnd = new Date(list.getDate().getTime() - (420 * 60000) + (list.getTotalDuration() * 60000));

                if (trxDatabaseTimeStart.equals(trxDate) && list.getDoctor().getId().equals(transaction.getDoctor().getId())) {
                    throw new NotFoundException("The schedule is already booked!");
                }

                if (trxDate.getTime() > trxDatabaseTimeStart.getTime()
                        && trxDate.getTime() < trxDatabaseTimeEnd.getTime()
                        && list.getDoctor().getId().equals(transaction.getDoctor().getId())) {
                    throw new NotFoundException("The schedule is already booked!");
                }

                long totalDuration = 0;
                for (int i = 0; i < transaction.getTransactionDetails().size(); i++) {
                    totalDuration += (productService.getProductById(transaction
                            .getTransactionDetails().get(i).getProduct().getId()).getDuration()
                            * transaction.getTransactionDetails().get(i).getQuantity());
                }

                long durationHalfHour = 0;
                long timeTransaction = 0;
                for (int i = 0; i < (totalDuration / 30); i++) {
                    timeTransaction = trxDate.getTime() + (durationHalfHour * 60000);

                    if (timeTransaction >= trxDatabaseTimeStart.getTime()
                            && timeTransaction < trxDatabaseTimeEnd.getTime()
                            && list.getDoctor().getId().equals(transaction.getDoctor().getId())) {
                        throw new NotFoundException("Schedule is full!");
                    }
                    durationHalfHour += 30;
                }
            }
        }

    }
}
