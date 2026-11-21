package com.coprod.gatekeeper.management.service;

import com.coprod.gatekeeper.management.dto.request.OrganizationRequest;
import com.coprod.gatekeeper.management.dto.response.OrganizationResponse;
import com.coprod.gatekeeper.management.entity.OrgStatus;
import com.coprod.gatekeeper.management.entity.Organization;
import com.coprod.gatekeeper.management.exception.ApiException;
import com.coprod.gatekeeper.management.repository.OrganizationRespository;
import jakarta.transaction.Transactional;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrganisationService {

    private OrganizationRespository organizationRespository;

    public OrganisationService(OrganizationRespository organizationRespository) {
        this.organizationRespository = organizationRespository;
    }

    @Transactional
    public OrganizationResponse createOrganisation(OrganizationRequest request){
        //we need to set all the values manually

        Organization organization= new Organization();

        organization.setName(request.getName());
        organization.setSlug(request.getSlug());
        organization.getStatus(OrgStatus.PENDING);
        //at first it will alwyas be pending
        organization.setCreated_at(LocalDateTime.now());
        organization.setUpdated_at(LocalDateTime.now());

        organizationRespository.save(organization);

        return OrganizationResponse.from(organization);
    }



    @Transactional
    public OrganizationResponse findOrg(Long id){
        return OrganizationResponse.from(getOrganization(id));

    }

    //finding all

    //this special one is beacusee wee want our soft deleted data nor to be exposed when alll feetched other wise when we
    //want all the data from dattabase we use find all
    @Transactional
    public List<OrganizationResponse> findAll(){
        List<OrganizationResponse> responses= new ArrayList<OrganizationResponse>();

        for(Organization organization:organizationRespository.findByAllAndStatusNot(OrgStatus.PENDING))){

            responses.add(OrganizationResponse.from(organization));
        }
        return responses;

    }

    @Transactional
    public OrganizationResponse update(Long id ,OrganizationRequest request){
        Organization organization=getOrganization(id);

        organization.setName(request.getName());
        organization.setSlug(request.getSlug());
        organization.setUpdated_at(LocalDateTime.now());


        return OrganizationResponse.from(organization);
    }

    @Transactional
    public void delete(Long id){

        //we just have to set the status to deleted it is soft delete
        Organization organization=getOrganization(id);
        organization.setStatus(OrgStatus.DELETED);
        organization.setUpdated_at(LocalDateTime.now());
        organization.setDeleted_at(LocalDateTime.now());


    }





    public Organization getOrganization(Long id){
        return organizationRespository.findBIdAndStatusNot(id,OrgStatus.DELETED) //this check if org is deleted or it really exists
                .orElseThrow(()->new ApiException(HttpStatus.NOT_FOUND,"Organisation does not exists"));
    }
}
