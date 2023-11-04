package tn.esprit.rh.achat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import tn.esprit.rh.achat.entities.CategorieFournisseur;
import tn.esprit.rh.achat.entities.DetailFournisseur;
import tn.esprit.rh.achat.entities.Fournisseur;
import tn.esprit.rh.achat.entities.SecteurActivite;
import tn.esprit.rh.achat.repositories.DetailFournisseurRepository;
import tn.esprit.rh.achat.repositories.FournisseurRepository;
import tn.esprit.rh.achat.repositories.ProduitRepository;
import tn.esprit.rh.achat.repositories.SecteurActiviteRepository;
import tn.esprit.rh.achat.services.FournisseurServiceImpl;

public class FournisseurServiceImplTest {

    @InjectMocks
    private FournisseurServiceImpl fournisseurService;

    @Mock
    private FournisseurRepository fournisseurRepository;

    @Mock
    private DetailFournisseurRepository detailFournisseurRepository;

    @Mock
    private ProduitRepository produitRepository;

    @Mock
    private SecteurActiviteRepository secteurActiviteRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testRetrieveAllFournisseurs() {
        // Arrange
        List<Fournisseur> fournisseurs = new ArrayList<>();
        fournisseurs.add(new Fournisseur());
        fournisseurs.add(new Fournisseur());

        when(fournisseurRepository.findAll()).thenReturn(fournisseurs);

        // Act
        List<Fournisseur> result = fournisseurService.retrieveAllFournisseurs();

        // Assert
        verify(fournisseurRepository, times(1)).findAll();
        assertEquals(2, result.size());
    }



    @Test
    public void testUpdateFournisseur() {
        // Arrange
        Fournisseur fournisseur = new Fournisseur();
        DetailFournisseur detailFournisseur = new DetailFournisseur();
        detailFournisseur.setDateDebutCollaboration(new Date());
        fournisseur.setDetailFournisseur(detailFournisseur);

        // Mock the behavior of saveDetailFournisseur
        when(detailFournisseurRepository.save(any(DetailFournisseur.class))).thenReturn(detailFournisseur);

        // Act
        Fournisseur result = fournisseurService.updateFournisseur(fournisseur);

        // Assert
        verify(fournisseurRepository, times(1)).save(fournisseur);
        verify(detailFournisseurRepository, times(1)).save(any(DetailFournisseur.class));
        assertEquals(detailFournisseur, result.getDetailFournisseur());
    }

    @Test
    public void testDeleteFournisseur() {
        // Arrange
        Long fournisseurId = 1L;

        // Act
        fournisseurService.deleteFournisseur(fournisseurId);

        // Assert
        verify(fournisseurRepository, times(1)).deleteById(fournisseurId);
    }


}
