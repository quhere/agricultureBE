package com.quang.tttn.model.mapper;

import com.quang.tttn.model.Response.DisWarehouseResponse2;
import com.quang.tttn.model.entity.DistributorWarehouse;
import com.quang.tttn.model.entity.Product;
import com.quang.tttn.model.entity.ProductDistributor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DisWarehouseResponse2Mapper {

    private final DistributorMapper distributorMapper;
    private final ProductMapper productMapper;
    private final SupplierMapper supplierMapper;

    public DisWarehouseResponse2Mapper(DistributorMapper distributorMapper, ProductMapper productMapper, SupplierMapper supplierMapper) {
        this.distributorMapper = distributorMapper;
        this.productMapper = productMapper;
        this.supplierMapper = supplierMapper;
    }

    public DisWarehouseResponse2 toResponse(DistributorWarehouse warehouse) {
        DisWarehouseResponse2 response = new DisWarehouseResponse2();
        response.setDistributorWarehouseId(warehouse.getWarehouseId());
        if (warehouse.getQuantity()!=null) response.setQuantity(warehouse.getQuantity());
        if (warehouse.getDistributor()!=null) {
            response.setDistributor(
                    distributorMapper.toDistributorResponse(
                            warehouse.getDistributor()
                    )
            );
        }

        ProductDistributor productDistributor = warehouse.getProductDistributor();
        if (productDistributor!=null) {
            response.setProduct(
                    productMapper.toProductResponse(
                            productDistributor.getProduct()
                    )
            );
            response.setSupplier(
                    supplierMapper.toResponse(
                            productDistributor.getProduct().getSupplier()
                    )
            );
        }

        return response;
    }

    public List<DisWarehouseResponse2> toResponseList(List<DistributorWarehouse> listWarehouse) {
        List<DisWarehouseResponse2> list = new ArrayList<>();
        for (DistributorWarehouse distributorWarehouse : listWarehouse) {
            list.add(toResponse(distributorWarehouse));
        }
        return list;
    }
}
