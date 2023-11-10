package tn.esprit.rh.achat;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.rh.achat.entities.Reglement;
import tn.esprit.rh.achat.repositories.ReglementRepository;
import tn.esprit.rh.achat.services.ProduitServiceImpl;
import tn.esprit.rh.achat.services.ReglementServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ReglementTest {

    @MockBean
    ReglementRepository rep;
    @InjectMocks
    @Autowired
    ReglementServiceImpl service;


    @Test
    @Order(1)
    void ajoutReglemntTest(){

        Reglement r = new Reglement() ;
        r.setDateReglement(new Date());
        r.setMontantRestant(7);
        given(this.rep.save(r)).willReturn(r);
        Reglement r2 = service.addReglement(r);
         assertTrue(r.getMontantRestant() == r2.getMontantRestant());
    }

    @Test
    @Order(2)
    void retrieveReglementTest(){
        Reglement r = new Reglement() ;
        r.setIdReglement(1L);
        r.setDateReglement(new Date());
        r.setMontantRestant(7);
        given(this.rep.findById(1L)).willReturn(Optional.of(r));
        Reglement r2 = service.retrieveReglement(1L);
        assertTrue(r.getMontantRestant() == r2.getMontantRestant());
    }


    @Test
    @Order(3)
    void retrieveAllReglementsTest(){
        Reglement r = new Reglement() ;
        r.setDateReglement(new Date());
        r.setMontantRestant(7);

        Reglement r2 = new Reglement() ;
        r2.setDateReglement(new Date());
        r2.setMontantRestant(6);

        List<Reglement> list = new ArrayList<>();
        list.add(r);
        list.add(r2);

        given(this.rep.findAll()).willReturn(list);

        List<Reglement> list2  = service.retrieveAllReglements();
        assertTrue(list2.size() == list.size());
    }



}
