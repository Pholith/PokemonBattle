package managers;

import javafx.scene.media.AudioClip;

public class SoundManager {

    SoundManager() {
        bip = new AudioClip(getClass().getResource("/resources/sounds/bip.wav").toExternalForm());
        fightMusic = new AudioClip(getClass().getResource("/resources/sounds/PokemonTrainer.wav").toExternalForm());
        victoryMusic = new AudioClip(getClass().getResource("/resources/sounds/VictoryMusic.wav").toExternalForm());
    }
    private final AudioClip bip;

    public AudioClip getFightMusic() {
        return fightMusic;
    }

    private final AudioClip fightMusic;

    public void playBip() {
        bip.play();
    }

    public AudioClip getVictoryMusic() {
        return victoryMusic;
    }

    private final AudioClip victoryMusic;

}
