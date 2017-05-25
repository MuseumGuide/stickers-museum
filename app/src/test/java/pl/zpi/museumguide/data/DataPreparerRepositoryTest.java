package pl.zpi.museumguide.data;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


import pl.zpi.museumguide.data.domain.Work;

import static junit.framework.Assert.assertTrue;


/**
 * Created by Jakub Licznerski on 06.04.2017.
 */
public class DataPreparerRepositoryTest {

    //todo test dependency injection here
    DataRepository repository;

    @Ignore
    @Before
    public void init() {
        repository = new DataPreparerRepository();
    }

    @Ignore
    @Test
    public void getAllWorksTest() {
        assertTrue(repository.getAllWorks().size() == 2);
    }

    @Ignore
    @Test
    public void getBeaconWorks() {
        assertTrue(repository.getBeaconWork("9d52d31fa9e0f214").size() == 1);
        assertTrue(repository.getBeaconWork("9d52d31fa9e0f214").get(0).getTitle().equals("Zimowy pejzaż z łyżwiarzami i pułapką na ptaki"));
    }

}