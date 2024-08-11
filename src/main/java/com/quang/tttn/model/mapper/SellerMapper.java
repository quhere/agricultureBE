package com.quang.tttn.model.mapper;

import com.quang.tttn.model.Response.SellerResponse;
import com.quang.tttn.model.entity.Seller;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SellerMapper {
    public SellerResponse toSellerResponse(Seller seller) {
        SellerResponse sellerResponse = new SellerResponse();
        sellerResponse.setSellerId(seller.getSellerId());
        sellerResponse.setName(seller.getName());
        sellerResponse.setEmail(seller.getEmail());
        sellerResponse.setPhoneNumber(seller.getPhoneNumber());
        sellerResponse.setAddress(seller.getAddress());
        sellerResponse.setFax(seller.getFax());
        sellerResponse.setAvtUrl(seller.getAvtUrl());
        sellerResponse.setStatus(seller.getStatus());
        sellerResponse.setRole(seller.getRole());
        sellerResponse.setTaxCode(seller.getTaxCode());
        sellerResponse.setEstablishment(seller.getEstablishment());
        sellerResponse.setManager(seller.getManager());
        sellerResponse.setActivated(seller.getActivated());
        sellerResponse.setDescription(seller.getDescription());
        return sellerResponse;
    }

    public List<SellerResponse> toSellerResponses(List<Seller> sellers) {
        List<SellerResponse> sellerResponses = new ArrayList<>();
        for (Seller seller: sellers) {
            sellerResponses.add(toSellerResponse(seller));
        }
        return sellerResponses;
    }
}
