package Pokemons;

import base.CONSTANTS;
import managers.GameManager;

import java.util.ArrayList;
import java.util.Optional;

public class Ligue {

    private final ArrayList<LigueDresser> ligueDresserList;
    private boolean initialized;
    public Ligue() {
        ligueDresserList = new ArrayList<>();
        initialized = false;
    }

    public void initialize() {
        if (initialized) return;
        for (int i = 0; i < CONSTANTS.LIGUE_NBR; i++) {
            if (i == CONSTANTS.LIGUE_NBR - 1) ligueDresserList.add(new LigueDresser(GameManager.GetInstance().getPokedex().getRandomTeam(CONSTANTS.LIGUE_POK_NBR + 1), i+1));
            else ligueDresserList.add(new LigueDresser(GameManager.GetInstance().getPokedex().getRandomTeam(CONSTANTS.LIGUE_POK_NBR), i+1));
        }
        initialized = true;
    }

    public ArrayList<LigueDresser> getLigueDresserList() {
        return ligueDresserList;
    }

    public LigueDresser findFirstPlayable() {
        Optional<LigueDresser> first = ligueDresserList.stream().filter(ligueDresser -> !ligueDresser.isDefeated()).findFirst();
        return first.orElse(null);
    }

    public boolean isDefeated() {
        return ligueDresserList.stream().filter(ligueDresser -> !ligueDresser.isDefeated()).count() < 1;
    }
}
