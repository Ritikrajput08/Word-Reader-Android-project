package com.example.word_reader;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextView wordTextView;
    private TextView translationTextView;
    private Button nextButton;
    private Button speakButton;
    private List<String> vocabularyList;
    private Random random;
    private int currentWordIndex;
    private TextToSpeech textToSpeech;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wordTextView = findViewById(R.id.wordTextView);
        translationTextView = findViewById(R.id.translationTextView);
        nextButton = findViewById(R.id.nextButton);
        speakButton = findViewById(R.id.speakButton);
        vocabularyList = new ArrayList<>();
        vocabularyList.add("Hello Ritik");
        vocabularyList.add("Goodbye Ritik");
        vocabularyList.add("Thank you Ritik");
        vocabularyList.add("Please Ritik");
        random = new Random();
        currentWordIndex = random.nextInt(vocabularyList.size());
        displayCurrentWord();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentWordIndex = random.nextInt(vocabularyList.size());
                displayCurrentWord();
            }
        });

        speakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakWord();
            }
        });

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.CHINESE);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(MainActivity.this, "Language not supported", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "TextToSpeech initialization failed", Toast.LENGTH_SHORT).show();
                }
            }
        }
        );
    }

    private void displayCurrentWord() {
        String word = vocabularyList.get(currentWordIndex);
        String translation = word;
        wordTextView.setText(word);
        translationTextView.setText(translation);
    }
    private void speakWord() {
        String word = vocabularyList.get(currentWordIndex);
        textToSpeech.speak(word, TextToSpeech.QUEUE_FLUSH, null, null);
    }
}