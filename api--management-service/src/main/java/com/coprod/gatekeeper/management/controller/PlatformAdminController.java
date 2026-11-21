package com.coprod.gatekeeper.management.controller;


import com.coprod.gatekeeper.management.dto.request.OrganizationRequest;
import com.coprod.gatekeeper.management.dto.response.OrganizationResponse;
import com.coprod.gatekeeper.management.service.OrganisationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organization")
public class PlatformAdminController {



    private OrganisationService organisationService;

    public PlatformAdminController(OrganisationService organisationService) {
        this.organisationService = organisationService;
    }

    @PostMapping
    public ResponseEntity<OrganizationResponse> create( @RequestBody OrganizationRequest organizationRequest){
        return  ResponseEntity.status(HttpStatus.CREATED).
                body(organisationService.createOrganisation(organizationRequest));
    }

    @GetMapping("/{id}")

    public ResponseEntity<OrganizationResponse> get(@PathVariable Long id){
        OrganizationResponse response= organisationService.findOrg(id);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("/all")

    public ResponseEntity<List<OrganizationResponse>> getAll(){
        List<OrganizationResponse> responseList= organisationService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }

    @PutMapping("/{id}")

    public ResponseEntity<OrganizationResponse> update(@PathVariable Long id, @RequestBody OrganizationRequest request){
        return  ResponseEntity.status(HttpStatus.OK).body(organisationService.update(id, request));


    }

    @DeleteMapping("{id}")

    public ResponseEntity<Void> delete (@PathVariable Long id){
        organisationService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
