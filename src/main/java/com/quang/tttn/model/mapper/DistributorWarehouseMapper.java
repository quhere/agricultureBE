package com.quang.tttn.model.mapper;

import com.quang.tttn.model.Response.DistributorWarehouseResponse;
import com.quang.tttn.model.entity.DistributorWarehouse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DistributorWarehouseMapper {
    private final DistributorMapper distributorMapper;
    private final SupToDisMapper supToDisMapper;

    public DistributorWarehouseMapper(DistributorMapper distributorMapper, SupToDisMapper supToDisMapper) {
        this.distributorMapper = distributorMapper;
        this.supToDisMapper = supToDisMapper;
    }

    public DistributorWarehouseResponse toResponse(DistributorWarehouse distributorWarehouse) {
        DistributorWarehouseResponse response = new DistributorWarehouseResponse();

        response.setWarehouseId(distributorWarehouse.getWarehouseId());

        if (distributorWarehouse.getDistributor() != null) {
            response.setDistributor(distributorMapper.toDistributorResponse(
                    distributorWarehouse.getDistributor()
            ));
        }

        if (distributorWarehouse.getProductDistributor() != null ) {
            response.setSupToDis(supToDisMapper.toSupToDisResponse(
                    distributorWarehouse.getProductDistributor()
            ));
        }
        response.setQuantity(distributorWarehouse.getQuantity());

        return response;
    }

    public List<DistributorWarehouseResponse> toResponseList(List<DistributorWarehouse> distributorWarehouseList) {
        List<DistributorWarehouseResponse> distributorWarehouseResponseList = new ArrayList<>();
        for (DistributorWarehouse distributorWarehouse : distributorWarehouseList) {
            distributorWarehouseResponseList.add(toResponse(distributorWarehouse));
        }
        return distributorWarehouseResponseList;
    }
}
