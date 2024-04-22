package uk.ac.soton.comp1206.game;

import javafx.scene.control.ProgressBar;

public class TimerBar extends ProgressBar {
    // Modify the constructor to set initial properties
    public TimerBar() {
        setPrefWidth(200); // Set preferred width
        setProgress(1); // Set initial progress to full
        setStyle("-fx-accent: green;"); // Set initial color
    }

    // Implement a method to update the timer bar based on remaining time
    public void updateTimer(long remainingTime) {
        double maxTime = 10;
        // Calculate progress based on remaining time
        double progress = (double) remainingTime / (double) maxTime;
        setProgress(progress);
        // Change color based on progress
        if (progress < 0.5) {
            setStyle("-fx-accent: orange;"); // Change to orange if time is running out
        }
        if (progress < 0.2) {
            setStyle("-fx-accent: red;"); // Change to red if time is almost up
        }
    }
}
