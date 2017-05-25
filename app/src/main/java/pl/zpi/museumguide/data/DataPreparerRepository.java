package pl.zpi.museumguide.data;

import java.util.ArrayList;
import java.util.Collections;
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
    public static final String beacon1UUID = "4810b7186752df3a";
    // UUID Mati 1 "9d52d31fa9e0f214"
    // UUID Mario 1 "4810b7186752df3a"
    // UUID Bed "04fa06a3e84db44d"


    /**
     * UUID Mario 2
     */
    public static final String beacon2UUID = "3804f0fbfdafcc37";
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

        Author a1 = new Author();
        String descrPieterBrueghel = "Pieter Brueghel starszy urodził się w 1525 roku nieopodal Bredy. Malował przede wszystkim pejzaże, zapełnione postaciami chłopskimi (stąd przydomek). Czerpał motywy z codziennego życia ludu (wesela, uczty, biesiady, kiermasze), z Biblii, ilustrował przysłowia. Często wskazywany jako pierwszy malarz zachodni malujący pejzaże dla nich samych, a nie tylko jako tło dla alegorii religijnych. Malował stylem prostym, nie poddając się dominującej wówczas modzie włoskiej. Często nawiązywał do stylu Boscha.";
        a1.setFirstname("Pieter");
        a1.setLastname("Brueghel");
        a1.setAlias("Młodszy");
        a1.setDescription(descrPieterBrueghel);
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
        w1.setDescription("Obraz przedstawia mieszkańców wioski ślizgających się i spacerujących po zamarzniętej rzece. Wokół panuje zimowa aura, drzewa są nagie, a na pierwszym planie widoczne są czarne wrony siedzące na gałęziach. Na małej przestrzeni malarz przedstawił gros szczegółów z zimowego pejzażu, a kolory i kształty");
        w1.addMaterial(m1);
        w1.addMaterial(m2);
        w1.setType(WorkType.PAINTING);
        w1.setBeacon(b1);
        w1.setAuthor(a1);
        w1.setIdDrawable(R.drawable.zimowy);

        b1.addWork(w1);

        Work w2 = new Work();
        w2.setTitle("Głowa starej wieśniaczki");
        w2.addContext(c1);
        w2.addContext(c3);
        w2.setDescription("Jest to jedyny portret przypisywany bezspornie Brueglowi. Malarz nie wykonywał portretów na zamówienie. Jego twórczość opierała się o obserwacje sił natury i powiązanego z nią życia prostych ludzi. Malowanie scen dydaktycznych było dla niego celem pierwszorzędnym i unikał przedstawiania ludzi jako jednostki. Na wielu swoich dziełach stara się wręcz ukryć twarze. Przykładem tego jest np. grafika Lato (1568) czy obraz Złodziej gniazd. Głowa wieśniaczki pokazuje iż posiadał talent do portretowania i potrafił ukazać zindywidualizowane rysy kobiety.");
        w2.addMaterial(m1);
        w2.addMaterial(m2);
        w2.setType(WorkType.PAINTING);
        w2.setBeacon(b2);
        w2.setAuthor(a1);
        w2.setIdDrawable(R.drawable.glowa);

        b2.addWork(w2);

        Work w3 = new Work();
        w3.setTitle("Przysłowia niderlandzkie");
        w3.setDescription("Jest to obraz olejny na desce o wymiarach 117 × 163 cm, przedstawiający wiejski krajobraz, w który wpisane są liczne grupy postaci i przedmioty, tworzące oddzielne wątki rodzajowe. Na pierwszym planie widoczne jest rozległe podwórze, przy którym stoi wielka chałupa, pręgierz i stara szopa. Drugi plan tworzy ceglana wieża z przybudówkami i drewniana stajnia. Pomiędzy nimi płynie rzeka tworząca dolinę, zaś w oddali widać ujście rzeki do morza, na którym płynie rybacki statek z żaglem. Kompozycja obrazu oparta jest na osi, która prowadzi z przodu po lewej stronie do tyłu po prawej ku morzu. ");
        w3.setAuthor(a1);
        w3.addMaterial(m1);
        w3.addMaterial(m2);
        w3.setType(WorkType.PAINTING);
        w3.setIdDrawable(R.drawable.przyslowia);

        Work w4 = new Work();
        w4.setTitle("Triumf śmierci");
        w4.setDescription("Obraz przedstawia bardzo ponurą scenerię, która budzi skojarzenia z trwającą bitwą. Jednak owa bitwa nie rozgrywa się między samymi ludźmi a między armią umarłych, która nieuchronnie odbiera życie wszystkim ludziom. Malarz położył duży nacisk na ukazanie bezsilności wobec śmierci, na płótnie zaprezentował ludzi z różnych środowisk społecznych – od chłopów i żołnierzy do szlachty, a nawet króla i kardynała - karanych przez śmierć bez wyjątku, którzy w obliczu śmierci stają na równi z innymi.");
        w4.setAuthor(a1);
        w4.addMaterial(m1);
        w4.addMaterial(m2);
        w4.setType(WorkType.PAINTING);
        w4.setIdDrawable(R.drawable.smierc);

        Collections.addAll(a1.getWorks(), w1, w2, w3, w4);
        Collections.addAll(works, w1, w2, w3, w4);

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
