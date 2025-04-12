package tn.esprit.spring;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tn.esprit.spring.dao.entities.Bloc;
import tn.esprit.spring.dao.repositories.BlocRepository;

@DataJpaTest
public class BlocRepositoryTest {

    @Autowired
    private BlocRepository blocRepository;

    @Test
    public void testFindByNomBloc() {
        // Given : Création et sauvegarde d'un Bloc avec le nom "TestBloc"
        Bloc bloc = new Bloc();
        bloc.setNomBloc("TestBloc");
        blocRepository.save(bloc);

        // When : Récupération via la méthode findByNomBloc
        Bloc found = blocRepository.findByNomBloc("TestBloc");

        // Then : Vérification que l'objet est retrouvé et que son nom correspond
        assertThat(found).isNotNull();
        assertThat(found.getNomBloc()).isEqualTo("TestBloc");
    }

    @Test
    public void testGetByNomBloc() {
        // Given : Création et sauvegarde d'un Bloc avec le nom "GetBloc"
        Bloc bloc = new Bloc();
        bloc.setNomBloc("GetBloc");
        blocRepository.save(bloc);

        // When : Récupération de la liste des Blocs via la méthode getByNomBloc
        List<Bloc> blocs = blocRepository.getByNomBloc("GetBloc");

        // Then : La liste doit contenir au moins un élément et son nom doit
        // correspondre
        assertThat(blocs).isNotEmpty();
        assertThat(blocs.get(0).getNomBloc()).isEqualTo("GetBloc");
    }

    @Test
    public void testSelectByNomBJPQL1() {
        // Given : Création et sauvegarde d'un Bloc avec le nom "JPQLBloc"
        Bloc bloc = new Bloc();
        bloc.setNomBloc("JPQLBloc");
        blocRepository.save(bloc);

        // When : Récupération via la méthode selectByNomBJPQL1
        Bloc found = blocRepository.selectByNomBJPQL1("JPQLBloc");

        // Then : Vérification que l'objet est bien trouvé et que son nom correspond
        assertThat(found).isNotNull();
        assertThat(found.getNomBloc()).isEqualTo("JPQLBloc");
    }


}
