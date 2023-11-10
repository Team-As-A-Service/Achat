package tn.esprit.rh.achat;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.rh.achat.entities.Reglement;
import tn.esprit.rh.achat.repositories.ReglementRepository;
import tn.esprit.rh.achat.services.ReglementServiceImpl;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class reglementServiceMock {

    @Mock
    private  ReglementRepository reglementRepository ;

    @InjectMocks
    private ReglementServiceImpl reglementService;

    @Test
    public void testRetrieveAllReglements() {
        // Create a list of reglements
        Reglement reglement1 = new Reglement(1L, 100.0f, 50.0f, false, new Date(), null);
        Reglement reglement2 = new Reglement(2L, 75.0f, 25.0f, true, new Date(), null);
        List<Reglement> reglementList = Arrays.asList(reglement1, reglement2);

        // Define the behavior of the mock repository
        when(reglementRepository.findAll()).thenReturn(reglementList);

        // Call the service method
        List<Reglement> result = reglementService.retrieveAllReglements();

        // Verify the service method and its outcome
        verify(reglementRepository, times(1)).findAll();
        // Verify the result
        assertEquals(2, result.size());
    }
    @Test
    public void testAddReglement() {
        // Create a reglement
        Reglement reglement = new Reglement(3L, 150.0f, 100.0f, false, new Date(), null);

        when(reglementRepository.save(reglement)).thenReturn(reglement);

        // Call the method to test
        Reglement addedReglement = reglementService.addReglement(reglement);

        // Verify if the method was called
        verify(reglementRepository, times(1)).save(reglement);
        // Verify the result
        assertEquals(reglement, addedReglement);
    }

    @Test
    public void testRetrieveReglementById() {
        long reglementId = 1L;
        Reglement reglement = new Reglement(reglementId, 100.0f, 50.0f, false, null, null);

        // Define the behavior of the mock repository when retrieving by ID
        when(reglementRepository.findById(reglementId)).thenReturn(Optional.of(reglement));

        // Call the service method to retrieve the reglement by ID
        Reglement result = reglementService.retrieveReglement(reglementId);

        // Verify the service method and its outcome
        verify(reglementRepository, times(1)).findById(reglementId);
        // Verify the result
        assertEquals(reglement, result);
    }




}