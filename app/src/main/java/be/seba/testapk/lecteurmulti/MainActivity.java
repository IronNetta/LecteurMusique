package be.seba.testapk.lecteurmulti;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import be.seba.testapk.lecteurmulti.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST = 100;
    private static final int PICK_MEDIA_REQUEST = 1;

    private MediaPlayer mediaPlayer;
    private boolean isPlayingAudio = false;
    private List<Uri> playlist = new ArrayList<>();
    private int currentSongIndex = 0;
    private boolean isShuffleOn = false;
    private boolean isRepeatOn = false;
    private String songTitle;
    private String artistName;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        checkStoragePermission();
        initializeUI();
    }

    private void initializeUI() {
        binding.icPlay.setOnClickListener(v -> handlePlayButton());
        binding.icForward.setOnClickListener(v -> playNextSong());
        binding.icBackward.setOnClickListener(v -> playPreviousSong());
        binding.icShuffle.setOnClickListener(v -> toggleShuffle());
        binding.icRepeat.setOnClickListener(v -> toggleRepeat());
        binding.progressBar.setMax(0);

        binding.btnPlaylist.setOnClickListener(v -> openPlaylistDialog());
    }

    private void handlePlayButton() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            togglePlayback();
        } else {
            requestPermissions();
        }
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_AUDIO}, PERMISSION_REQUEST);
    }
    private void checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions();
        } else {
            pickMedia();
        }
    }

    private void togglePlayback() {
        if (mediaPlayer == null) {
            Toast.makeText(this, R.string.no_audio_selected, Toast.LENGTH_SHORT).show();
            return;
        }

        if (isPlayingAudio) {
            mediaPlayer.pause();
            binding.icPlay.setImageResource(R.drawable.ic_play);
            isPlayingAudio = false;
        } else {
            mediaPlayer.start();
            binding.icPlay.setImageResource(R.drawable.ic_pause);
            isPlayingAudio = true;
            updateProgressBar();
        }
    }

    private void playNextSong() {
        if (playlist.isEmpty()) {
            Toast.makeText(this, R.string.no_songs_in_playlist, Toast.LENGTH_SHORT).show();
            return;
        }
        currentSongIndex = isShuffleOn ? new Random().nextInt(playlist.size()) : (currentSongIndex + 1) % playlist.size();
        playAudio(playlist.get(currentSongIndex));
    }

    private void playPreviousSong() {
        if (playlist.isEmpty()) {
            Toast.makeText(this, R.string.no_songs_in_playlist, Toast.LENGTH_SHORT).show();
            return;
        }
        currentSongIndex = (currentSongIndex > 0) ? currentSongIndex - 1 : playlist.size() - 1;
        playAudio(playlist.get(currentSongIndex));
    }

    private void playAudio(Uri audioUri) {
        if (audioUri == null) {
            Toast.makeText(this, R.string.invalid_audio_uri, Toast.LENGTH_SHORT).show();
            return; // Ne pas continuer si l'URI est invalide
        }

        if (mediaPlayer != null) {
            mediaPlayer.release();
        }

        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(this, audioUri);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(mp -> {
                mp.start();
                isPlayingAudio = true;
                extractMetadata(audioUri);
                updateUI();
            });
            mediaPlayer.setOnCompletionListener(mp -> {
                if (isRepeatOn) {
                    playAudio(audioUri);
                } else {
                    playNextSong();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, R.string.audio_playback_error, Toast.LENGTH_SHORT).show();
        }
    }

    private void toggleShuffle() {
        isShuffleOn = !isShuffleOn;
        binding.icShuffle.setSelected(isShuffleOn);
    }

    private void toggleRepeat() {
        isRepeatOn = !isRepeatOn;
        binding.icRepeat.setSelected(isRepeatOn);
    }

    private void updateUI() {
        binding.tvSong.setText(songTitle != null ? songTitle : "Titre de la chanson"); // Affichez le titre de la chanson
        binding.tvArtist.setText(artistName != null ? artistName : "Nom de l'artiste"); // Affichez le nom de l'artiste
        binding.progressBar.setMax(mediaPlayer.getDuration());
        binding.icPlay.setImageResource(R.drawable.ic_pause);
        updateProgressBar();
    }
    private void extractMetadata(Uri audioUri) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(this, audioUri);
            songTitle = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            artistName = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        } catch (IllegalArgumentException e) {
            // Cela peut se produire si l'URI est invalide ou le format est non pris en charge
            e.printStackTrace();
            Toast.makeText(this, R.string.metadata_retrieval_error, Toast.LENGTH_SHORT).show();
        } finally {
            try {
                retriever.release();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void updateProgressBar() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            binding.progressBar.setProgress(mediaPlayer.getCurrentPosition());
            new Handler().postDelayed(this::updateProgressBar, 1000);
        }
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }

    private void openPlaylistDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Playlist");

        // Transformer la playlist en une liste de chaînes à afficher
        String[] songTitles = new String[playlist.size()];
        for (int i = 0; i < playlist.size(); i++) {
            songTitles[i] = "Chanson " + (i + 1); // Nom générique pour les fichiers
        }

        builder.setItems(songTitles, (dialog, which) -> {
            currentSongIndex = which; // Mettre à jour l'index actuel
            playAudio(playlist.get(which));
        });

        builder.setNegativeButton("Fermer", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private void pickMedia() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("audio/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, PICK_MEDIA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_MEDIA_REQUEST && resultCode == RESULT_OK) {
            Uri selectedAudioUri = data.getData();
            if (selectedAudioUri != null) {
                playlist.add(selectedAudioUri);
                Toast.makeText(this, "Audio added to playlist", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            pickMedia();
        } else {
            Toast.makeText(this, R.string.permission_denied_message, Toast.LENGTH_SHORT).show();
        }
    }
}