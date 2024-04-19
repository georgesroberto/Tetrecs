package uk.ac.soton.comp1206;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

/**
 * The Multimedia class handles audio and music playback.
 */
public class Multimedia {

    private final MediaPlayer audioPlayer;
    private final MediaPlayer musicPlayer;

    public Multimedia() {
        // Initialize audio and music players
        audioPlayer = new MediaPlayer(new Media(new File("src/main/resources/music/game.wav").toURI().toString()));
        musicPlayer = new MediaPlayer(new Media(new File("src/main/resources/music/game.wav").toURI().toString()));
    }

    /**
     * Play an audio file.
     */
    public void playAudio() {
        audioPlayer.stop();
        audioPlayer.play();
    }

    /**
     * Play background music.
     */
    public void playMusic() {
        musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        musicPlayer.play();
    }

    /**
     * Stop playing background music.
     */
    public void stopMusic() {
        musicPlayer.stop();
    }
}
