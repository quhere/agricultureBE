package com.quang.tttn.model.mapper;

import com.quang.tttn.model.Response.DistributorResponse;
import com.quang.tttn.model.entity.Distributor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DistributorMapper {
    public DistributorResponse toDistributorResponse(Distributor distributor) {
        DistributorResponse distributorResponse = new DistributorResponse();
        distributorResponse.setDistributorId(distributor.getDistributorId());
        distributorResponse.setName(distributor.getName());
        distributorResponse.setEmail(distributor.getEmail());
        distributorResponse.setAddress(distributor.getAddress());
        distributorResponse.setPhoneNumber(distributor.getPhoneNumber());
        distributorResponse.setFax(distributor.getFax());
        return distributorResponse;
    }

    public List<DistributorResponse> toDistributorResponses(List<Distributor> distributors) {
        List<DistributorResponse> distributorResponses = new ArrayList<>();
        for (Distributor distributor : distributors) {
            distributorResponses.add(toDistributorResponse(distributor));
        }
        return distributorResponses;
    }
}
