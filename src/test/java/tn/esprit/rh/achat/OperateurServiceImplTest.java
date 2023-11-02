package tn.esprit.rh.achat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import tn.esprit.rh.achat.entities.Operateur;
import tn.esprit.rh.achat.repositories.OperateurRepository;
import tn.esprit.rh.achat.services.OperateurServiceImpl;

public class OperateurServiceImplTest {

    @InjectMocks
    private OperateurServiceImpl operateurService;

    @Mock
    private OperateurRepository operateurRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRetrieveAllOperateurs() {
        // Arrange
        List<Operateur> operateurs = new ArrayList<>();
        operateurs.add(new Operateur());
        operateurs.add(new Operateur());

        when(operateurRepository.findAll()).thenReturn(operateurs);

        // Act
        List<Operateur> result = operateurService.retrieveAllOperateurs();

        // Assert
        verify(operateurRepository, times(1)).findAll();
        assertEquals(2, result.size());
    }

    @Test
    public void testAddOperateur() {
        // Arrange
        Operateur operateur = new Operateur();

        // Act
        operateurService.addOperateur(operateur);

        // Assert
        verify(operateurRepository, times(1)).save(operateur);
    }

    @Test
    public void testDeleteOperateur() {
        // Arrange
        Long operateurId = 1L;

        // Act
        operateurService.deleteOperateur(operateurId);

        // Assert
        verify(operateurRepository, times(1)).deleteById(operateurId);
    }

    @Test
    public void testUpdateOperateur() {
        // Arrange
        Operateur operateur = new Operateur();

        // Act
        operateurService.updateOperateur(operateur);

        // Assert
        verify(operateurRepository, times(1)).save(operateur);
    }

    @Test
    public void testRetrieveOperateur() {
        // Arrange
        Long operateurId = 1L;
        Operateur operateur = new Operateur();
        operateur.setIdOperateur(operateurId);

        when(operateurRepository.findById(operateurId)).thenReturn(Optional.of(operateur));

        // Act
        Operateur result = operateurService.retrieveOperateur(operateurId);

        // Assert
        verify(operateurRepository, times(1)).findById(operateurId);
        assertEquals(operateurId, result.getIdOperateur());
    }
}
