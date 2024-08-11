package com.quang.tttn.controller;

import com.quang.tttn.model.Response.LoginResponse;
import com.quang.tttn.model.entity.Distributor;
import com.quang.tttn.model.entity.Seller;
import com.quang.tttn.model.entity.Supplier;
import com.quang.tttn.model.request.LoginRequest;
import com.quang.tttn.repository.DistributorRepository;
import com.quang.tttn.repository.SellerRepository;
import com.quang.tttn.service.DistributorService;
import com.quang.tttn.service.SellerService;
import com.quang.tttn.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authentication")
@CrossOrigin(origins = "*")
public class AuthenticationController {
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private DistributorRepository distributorRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest loginRequest
    )
    {
     if ( loginRequest.getRole() == null || loginRequest.getRole().isEmpty()
         || loginRequest.getEmail() == null || loginRequest.getEmail().isEmpty()
         || loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()) {
        return ResponseEntity.badRequest().build();
     }

        LoginResponse loginResponse = new LoginResponse();

        String rawPassword = loginRequest.getPassword();
        System.out.println( passwordEncoder.encode(rawPassword) );

     if ( loginRequest.getRole().equals("supplier") ) {
         Supplier supplier = supplierService.findByEmail(loginRequest.getEmail());
         if ( supplier == null ) {
             return ResponseEntity.notFound().build();
         }
         else {
             if ( passwordEncoder.matches(rawPassword, supplier.getPassword()) ) {
                 loginResponse.setId(supplier.getSupplierId());
                 loginResponse.setRole(supplier.getRole());
                 loginResponse.setName(supplier.getName());
                 loginResponse.setEmail(supplier.getEmail());
                 loginResponse.setPhoneNumber(supplier.getPhoneNumber());
                 loginResponse.setAddress(supplier.getAddress());
                 loginResponse.setFax(supplier.getFax());
                 loginResponse.setStatus(supplier.getStatus());
                 return ResponseEntity.ok(loginResponse);
             }
         }
     }
     else if ( loginRequest.getRole().equals("distributor") ) {
         Distributor distributor = distributorRepository.findByEmail(loginRequest.getEmail());
         if ( distributor == null ) {
             return ResponseEntity.notFound().build();
         }
         else {
             if ( passwordEncoder.matches(rawPassword, distributor.getPassword()) ) {
                 loginResponse.setId(distributor.getDistributorId());
                 loginResponse.setRole(distributor.getRole());
                 loginResponse.setName(distributor.getName());
                 loginResponse.setEmail(distributor.getEmail());
                 loginResponse.setPhoneNumber(distributor.getPhoneNumber());
                 loginResponse.setAddress(distributor.getAddress());
                 loginResponse.setFax(distributor.getFax());
                 loginResponse.setStatus(distributor.getStatus());
                 return ResponseEntity.ok(loginResponse);
             }
         }
     }
     else {
         Seller seller = sellerRepository.findByEmail(loginRequest.getEmail());
         if ( seller == null ) {
             return ResponseEntity.notFound().build();
         }
         else {
             if ( passwordEncoder.matches(rawPassword, seller.getPassword()) ) {
                 loginResponse.setId(seller.getSellerId());
                 loginResponse.setRole(seller.getRole());
                 loginResponse.setName(seller.getName());
                 loginResponse.setEmail(seller.getEmail());
                 loginResponse.setPhoneNumber(seller.getPhoneNumber());
                 loginResponse.setAddress(seller.getAddress());
                 loginResponse.setFax(seller.getFax());
                 loginResponse.setStatus(seller.getStatus());
                 return ResponseEntity.ok(loginResponse);
             }
         }
     }
     return ResponseEntity.notFound().build();
    }

}
