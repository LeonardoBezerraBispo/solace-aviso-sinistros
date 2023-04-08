package com.aberturaavisoservice.aberturasinistro.controler;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.RestController;

import com.aberturaavisoservice.aberturasinistro.model.DadosInformados;
import com.aberturaavisoservice.aberturasinistro.view.AberturaAvisoService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class Controle {
    AberturaAvisoService abertura = new AberturaAvisoService();
    @PostMapping(value="/santander/seguros/v1/aviso/sinistros")
    public DadosInformados postMethodName(@Valid @RequestBody DadosInformados entity) {
        JSONObject paylObject = new JSONObject(entity);
        this.abertura.aberturaSin(paylObject.toString());
        return entity;
    }
}
