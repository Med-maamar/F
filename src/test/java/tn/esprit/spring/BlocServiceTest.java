package tn.esprit.spring;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.dao.entities.Bloc;
import tn.esprit.spring.dao.entities.Chambre;
import tn.esprit.spring.dao.repositories.BlocRepository;
import tn.esprit.spring.dao.repositories.ChambreRepository;
import tn.esprit.spring.dao.repositories.FoyerRepository;
import tn.esprit.spring.services.bloc.BlocService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BlocServiceTest {

    @Mock
    private BlocRepository repo;

    @Mock
    private ChambreRepository chambreRepository;

    // Si nécessaire, vous pouvez vous appuyer sur un second mock du même type
    // si votre code l'exige vraiment.
    @Mock
    private BlocRepository blocRepository;

    @Mock
    private FoyerRepository foyerRepository;

    @InjectMocks
    private BlocService blocService;

    @Test
    public void testAddOrUpdate() {
        // Création d'un bloc avec une chambre
        Bloc bloc = new Bloc();
        bloc.setNomBloc("Bloc A");
        Chambre chambre = new Chambre();
        chambre.setNumeroChambre(101L);
        List<Chambre> chambres = new ArrayList<>();
        chambres.add(chambre);
        bloc.setChambres(chambres);

        // Simuler le comportement du repo pour l'ajout ou la mise à jour
        when(repo.save(any(Bloc.class))).thenReturn(bloc);
        // Pour la sauvegarde des chambres, on renvoie simplement l'objet passé
        when(chambreRepository.save(any(Chambre.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Bloc result = blocService.addOrUpdate(bloc);

        assertNotNull(result, "Le bloc ne doit pas être null");
        assertEquals("Bloc A", result.getNomBloc(), "Le nom du bloc doit être 'Bloc A'");
        // Vérifier que chaque chambre a bien été sauvegardée
        verify(chambreRepository, times(chambres.size())).save(any(Chambre.class));
        verify(repo, times(1)).save(any(Bloc.class));
    }

    @Test
    public void testFindById() {
        // Création d'un bloc simulé pour l'ID 1
        Bloc bloc = new Bloc();
        bloc.setNomBloc("Bloc B");
        when(repo.findById(1L)).thenReturn(Optional.of(bloc));

        Bloc result = blocService.findById(1L);
        assertNotNull(result, "Le bloc doit être trouvé");
        assertEquals("Bloc B", result.getNomBloc(), "Le nom du bloc doit être 'Bloc B'");

        // Vérifier le cas où aucun bloc n'est trouvé (retourne null)
        when(repo.findById(2L)).thenReturn(Optional.empty());
        Bloc result2 = blocService.findById(2L);
        assertNull(result2, "Aucun bloc ne doit être trouvé pour l'ID 2");
    }
}
