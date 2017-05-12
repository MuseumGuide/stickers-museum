package pl.zpi.museumguide.data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import pl.zpi.museumguide.R;
import pl.zpi.museumguide.data.domain.Author;
import pl.zpi.museumguide.data.domain.Beacon;
import pl.zpi.museumguide.data.domain.Context;
import pl.zpi.museumguide.data.domain.ContextType;
import pl.zpi.museumguide.data.domain.Material;
import pl.zpi.museumguide.data.domain.MaterialType;
import pl.zpi.museumguide.data.domain.Period;
import pl.zpi.museumguide.data.domain.Work;
import pl.zpi.museumguide.data.domain.WorkType;

/**
 * Created by Jakub Licznerski on 19.03.2017.
 */

public class DataPreparerRepository implements DataRepository {

    List<Work> works;
    List<Beacon> beacons;

     /**
     * UUID Mario 1
     */
    public static final String beacon1UUID = "9d52d31fa9e0f214";
    // UUID Mati 1 "9d52d31fa9e0f214"
    // UUID Mario 1 "4810b7186752df3a"
    // UUID Bed "04fa06a3e84db44d"


    /**
     * UUID Mario 2
     */
    public static final String beacon2UUID = "c0e0ce88435105aa";
    // UUID Mati 2 "c0e0ce88435105aa"
    // UUID Mario 2 "3804f0fbfdafcc37"
    // UUID Door_blue "3b3101cd591facae"

    public DataPreparerRepository() {
        works = new ArrayList<>();
        beacons = new ArrayList<>();
        prepareData();
    }

    private void prepareData() {
        Period p1 = new Period();
        p1.setCentury(17);

        Author a1;
        a1 = new Author();
        a1.setAlias("młodszy");
        a1.setFirstname("Pieter");
        a1.setLastname("Brueghel");
        a1.setIdDrawable(R.drawable.pieterbrueghel);

        Context c1 = new Context();
        c1.setName("Barok");
        c1.setPeriod(p1);
        c1.setType(ContextType.HISTORIC);
        c1.setText("Główny kierunek w kulturze europejskiej, którego trwanie datuje się od końca XVI wieku do XVIII wieku[1]. Nieoficjalny styl Kościoła katolickiego czasów potrydenckich, stąd pojawiające się jeszcze w połowie XX wieku zamienne określenia: „sztuka jezuicka” czy „sztuka kontrreformacyjna”[2]. W odróżnieniu od humanizmu antropocentrycznego doby renesansu, barok reprezentował teocentryczny mistycyzm. W znaczeniu węższym, barok to jeden z nurtów literackich XVII wieku, koegzystujący z klasycyzmem i manieryzmem; od niego XX-wieczni badacze wyprowadzili jednak nazwę dla całej epoki. Barok obejmował wszystkie przejawy działalności literackiej i artystycznej. U jego podstaw leżało twórcze przekształcenie renesansowego klasycyzmu w dążeniu do uzyskania maksymalnego oddziaływania na odbiorcę[1]. Barok jest pojęciem bogatszym od manieryzmu, przede wszystkim dlatego, że konotuje nie tylko sam styl, ale jak dowodzą niektórzy badacze również procesy historyczne, spory filozoficzne i teologiczne oraz nastroje społeczne[3]. Bogaty w zdobnictwo, pomysłowe rozwiązania i symbolikę styl architektoniczny, malarski i literacki baroku z założenia opierał się na ignacjańskiej zasadzie applicatio sensuum, polegającej na wykorzystaniu ludzkiej zmysłowości i erotyki do przekazania treści religijnych (stąd figury świętych w ekstazie czy wyrazy oblubieńczych uczuć, skierowanych do Chrystusa).");

        Context c2 = new Context();
        c2.setName("Zimowy pejzaż z łyżwiarzami i pułapką na ptaki");
        c2.setText("Obraz przedstawia mieszkańców wioski ślizgających się i spacerujących po zamarzniętej rzece. Wokół panuje zimowa aura, drzewa są nagie, a na pierwszym planie widoczne są czarne wrony siedzące na gałęziach. Na małej przestrzeni malarz przedstawił gros szczegółów z zimowego pejzażu, a kolory i kształty");
        c2.setType(ContextType.ARTISTIC);
        c2.setPeriod(p1);

        Context c3 = new Context();
        c3.setName("Głowa starej wieśniaczki");
        c3.setText("Jest to jedyny portret przypisywany bezspornie Brueglowi. Malarz nie wykonywał portretów na zamówienie. Jego twórczość opierała się o obserwacje sił natury i powiązanego z nią życia prostych ludzi. Malowanie scen dydaktycznych było dla niego celem pierwszorzędnym i unikał przedstawiania ludzi jako jednostki. Na wielu swoich dziełach stara się wręcz ukryć twarze. Przykładem tego jest np. grafika Lato (1568) czy obraz Złodziej gniazd. Głowa wieśniaczki pokazuje iż posiadał talent do portretowania i potrafił ukazać zindywidualizowane rysy kobiety.");
        c3.setPeriod(p1);
        c3.setType(ContextType.ARTISTIC);

        Material m1 = new Material();
        m1.setName("deska");
        m1.setType(MaterialType.CANVAS);

        Material m2 = new Material();
        m2.setName("farba olejna");
        m2.setType(MaterialType.PAINT);

        Beacon b1 = new Beacon();
        b1.setUuid(beacon1UUID);

        Beacon b2 = new Beacon();
        b2.setUuid(beacon2UUID);


        Work w1 = new Work();
        w1.setTitle("Zimowy pejzaż z łyżwiarzami i pułapką na ptaki");
        w1.addContext(c1);
        w1.addContext(c2);
        w1.addMaterial(m1);
        w1.addMaterial(m2);
        w1.setType(WorkType.PAINTING);
        w1.setBeacon(b1);
        w1.addAuthor(a1);
        w1.setIdDrawable(R.drawable.zimowy);

        b1.addWork(w1);
        a1.addWork(w1);

        Work w2 = new Work();
        w2.setTitle("Głowa starej wieśniaczki");
        w2.addContext(c1);
        w2.addContext(c3);
        w2.addMaterial(m1);
        w2.addMaterial(m2);
        w2.setType(WorkType.PAINTING);
        w2.setBeacon(b2);
        w2.addAuthor(a1);
        w2.setIdDrawable(R.drawable.glowa);

        b2.addWork(w2);
        a1.addWork(w2);


        works.add(w1);
        works.add(w2);

        beacons.add(b1);
        beacons.add(b2);
    }

    @Override
    public List<Work> getAllWorks() {
        return works;
    }

    @Override
    public List<Work> getBeaconWork(String uuid) {
        List<Work> result = new LinkedList<>();
        //todo think about returning copy (if fetched from API no sense to copy)
        for (Work w :
                works) {
            if (w.getBeacon().getUuid().equals(uuid))
                result.add(w);
        }
        return result;
    }

    @Override
    public Beacon getBeacon(String uuid) {
        for (Beacon b :
                beacons) {
            if (b.getUuid().equals(uuid))
                return b;
        }

        return null;
    }
}
