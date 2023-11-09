package tn.esprit.rh.achat;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import tn.esprit.rh.achat.entities.Facture;
import tn.esprit.rh.achat.entities.Fournisseur;
import tn.esprit.rh.achat.entities.Operateur;
import tn.esprit.rh.achat.repositories.FactureRepository;
import tn.esprit.rh.achat.repositories.FournisseurRepository;
import tn.esprit.rh.achat.repositories.OperateurRepository;
import tn.esprit.rh.achat.services.FactureServiceImpl;
import tn.esprit.rh.achat.services.ReglementServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FactureServiceImplTest {

    @InjectMocks
    private FactureServiceImpl factureService;

    @Mock
    private FactureRepository factureRepository;

    @Mock
    private OperateurRepository operateurRepository;

    @Mock
    private FournisseurRepository fournisseurRepository;

    @Mock
    private ReglementServiceImpl reglementService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRetrieveAllFactures() {
        // Arrange
        List<Facture> factureList = new ArrayList<>();
        factureList.add(new Facture());
        factureList.add(new Facture());
        when(factureRepository.findAll()).thenReturn(factureList);

        // Act
        List<Facture> result = factureService.retrieveAllFactures();

        // Assert
        assertEquals(2, result.size());
        verify(factureRepository).findAll();
    }

    @Test
    public void testAddFacture() {
        // Arrange
        Facture facture = new Facture();

        when(factureRepository.save(any(Facture.class))).thenReturn(facture);

        // Act
        Facture result = factureService.addFacture(facture);

        // Assert
        assertEquals(facture, result);
        verify(factureRepository).save(facture);
    }


    @Test
    public void testCancelFacture() {
        // Arrange
        Long factureId = 1L;
        Facture facture = new Facture();
        when(factureRepository.findById(factureId)).thenReturn(java.util.Optional.of(facture));

        // Act
        factureService.cancelFacture(factureId);

        // Assert
        verify(factureRepository).findById(factureId);
        verify(factureRepository).save(facture);
    }

//    @Test
//    public void testAssignOperateurToFacture() {
//        // Create a sample Facture and Operateur
//        Facture facture = new Facture();
//        facture.setIdFacture(1L);
//
//        Operateur operateur = new Operateur();
//        operateur.setIdOperateur(2L);
//        operateur.setFactures(new Set<Facture>);
//
//        // Stub the repository methods
//        Mockito.when(factureRepository.findById(1L)).thenReturn(java.util.Optional.of(facture));
//        Mockito.when(operateurRepository.findById(2L)).thenReturn(java.util.Optional.of(operateur));
//
//        // Call the method under test
//        factureService.assignOperateurToFacture(2L, 1L);
//
//        // Verify that the repositories were called as expected
//        Mockito.verify(factureRepository).findById(1L);
//        Mockito.verify(operateurRepository).findById(2L);
//        Mockito.verify(operateurRepository).save(operateur);
//
//        // Verify that the Facture was added to the Operateur's Factures
//        assertEquals(1, operateur.getFactures().size());
//        assertTrue(operateur.getFactures().contains(facture));
//    }
    @Test
    public void testRetrieveFacture() {
        // Arrange
        Long factureId = 1L;
        Facture facture = new Facture();
        when(factureRepository.findById(factureId)).thenReturn(java.util.Optional.of(facture));

        // Act
        Facture result = factureService.retrieveFacture(factureId);

        // Assert
        assertEquals(facture, result);
        verify(factureRepository).findById(factureId);
    }

    @Test
    public void testPourcentageRecouvrement() {
        // Arrange
        Date startDate = new Date();
        Date endDate = new Date();
        when(factureRepository.getTotalFacturesEntreDeuxDates(startDate, endDate)).thenReturn(100.0f);
        when(reglementService.getChiffreAffaireEntreDeuxDate(startDate, endDate)).thenReturn(50.0f);

        // Act
        float pourcentage = factureService.pourcentageRecouvrement(startDate, endDate);

        // Assert
        assertEquals(50.0f, pourcentage);
    }
}