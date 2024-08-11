package com.quang.tttn.model.mapper;

import com.quang.tttn.model.Response.SupplierResponse;
import com.quang.tttn.model.entity.Supplier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SupplierMapper {
    public SupplierResponse toResponse(Supplier supplier) {
        if (supplier == null) {
            return null;
        }

        return new SupplierResponse(
                supplier.getSupplierId(),
                supplier.getRole(),
                supplier.getName(),
                supplier.getEmail(),
                supplier.getPhoneNumber(),
                supplier.getAddress(),
                supplier.getFax(),
                supplier.getStatus(),
                supplier.getAvtUrl(),
                supplier.getTaxCode(),
                supplier.getEstablishment(),
                supplier.getManager(),
                supplier.getActivated(),
                supplier.getDescription()
        );
    }

    public List<SupplierResponse> toResponseList(List<Supplier> suppliers) {
        List<SupplierResponse> supplierResponses = new ArrayList<>();
        for (Supplier supplier : suppliers) {
            supplierResponses.add(toResponse(supplier));
        }
        return supplierResponses;
    }
}
