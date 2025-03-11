/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package videoplayer;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

/**
 *
 * @author HP
 */
public class VideoPlayer extends Application {
    private MediaPlayer mediaPlayer;
    private MediaView mediaView;
    private Button playPauseButton;
    private List<String> videoFiles;
    private int currentIndex = 0;
    
    @Override
    public void start(Stage primaryStage) throws MalformedURLException {

      videoFiles = new ArrayList<>();
        videoFiles.add("C:/Users/HP/Downloads/test.mp4");
        videoFiles.add("C:/Users/HP/Downloads/test2.mp4");
        videoFiles.add("C:/Users/HP/Downloads/test3.mp4");

        mediaView = new MediaView();
        playPauseButton = new Button("Play");

        // Load the first video
        playVideo(currentIndex);

        playPauseButton.setOnAction(e -> {
            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.pause();
                playPauseButton.setText("Play");
            } else {
                mediaPlayer.play();
                playPauseButton.setText("Pause");
            }
        });
        // Next & Previous Buttons

        Button nextButton = new Button("Next");
        nextButton.setOnAction(e -> playNext());
        
         // Next & Previous Buttons
        Button prevButton = new Button("Previous");
        prevButton.setOnAction(e -> playPrevious());
        
        
        VBox root =new VBox(10, mediaView, playPauseButton, prevButton, nextButton);
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
      private void playVideo(int index) {
        if (index < 0 || index >= videoFiles.size()) return; // Out of bounds check

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose(); // Clean up previous media player
        }

        File file = new File(videoFiles.get(index));
        if (!file.exists()) {
            System.out.println("File not found: " + file.getAbsolutePath());
            return;
        }

        Media media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.setOnEndOfMedia(this::playNext); // Auto-play next video when finished
        mediaPlayer.play();
        playPauseButton.setText("Pause");
    }

    private void playNext() {
        if (currentIndex < videoFiles.size() - 1) {
            currentIndex++;
            playVideo(currentIndex);
        }
    }

    private void playPrevious() {
        if (currentIndex > 0) {
            currentIndex--;
            playVideo(currentIndex);
        }
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
