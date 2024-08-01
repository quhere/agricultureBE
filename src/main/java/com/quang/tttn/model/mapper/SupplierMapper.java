package com.quang.tttn.model.mapper;

import com.quang.tttn.model.Response.SupplierResponse;
import com.quang.tttn.model.entity.Supplier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SupplierMapper {
    public SupplierResponse toResponse(Supplier supplier) {
        return SupplierResponse.builder()
                .supplierId(supplier.getSupplierId())
                .name(supplier.getName())
                .email(supplier.getEmail())
                .phoneNumber(supplier.getPhoneNumber())
                .address(supplier.getAddress())
                .fax(supplier.getFax())
                .build();
    }

    public List<SupplierResponse> toResponseList(List<Supplier> suppliers) {
        List<SupplierResponse> supplierResponses = new ArrayList<>();
        for (Supplier supplier : suppliers) {
            supplierResponses.add(toResponse(supplier));
        }
        return supplierResponses;
    }
}
