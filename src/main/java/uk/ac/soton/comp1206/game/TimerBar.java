package uk.ac.soton.comp1206.game;

import javafx.scene.control.ProgressBar;

public class TimerBar extends ProgressBar {
    // Modify the constructor to set initial properties
    public TimerBar() {
        setPrefWidth(200); // Set preferred width
        setProgress(1); // Set initial progress to full
        updateStyle(1); // Set initial color
    }

    // Implement a method to update the timer bar based on remaining time
    public void updateTimer(long remainingTime) {
        double maxTime = 10;
        // Calculate progress based on remaining time
        double progress = (double) remainingTime / (double) maxTime;
        setProgress(progress);
        updateStyle(progress); // Update color based on progress
    }

    // Update style based on progress level
    private void updateStyle(double progress) {
        if (progress < 0.5) {
            setStyle("-fx-accent: orange;");
        } else if (progress < 0.2) {
            setStyle("-fx-accent: red;");
        } else {
            setStyle("-fx-accent: green;");
        }
    }
}