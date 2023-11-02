package tn.esprit.rh.achat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.rh.achat.entities.Reglement;
import tn.esprit.rh.achat.services.ProduitServiceImpl;
import tn.esprit.rh.achat.services.ReglementServiceImpl;

import java.util.Date;

import static org.junit.Assert.assertEquals;

@ContextConfiguration(classes = {ReglementServiceImpl.class})
@ExtendWith(SpringExtension.class)
public class ReglementTest {
    @MockBean
    ReglementServiceImpl reglementService;
    Reglement r = new Reglement() ;
    @Test
    void ajoutReglemntTest(){
        r.setDateReglement(new Date());
        r.setMontantRestant(7);

       r= reglementService.addReglement(r);
         assertEquals(null,r);




    }



}
