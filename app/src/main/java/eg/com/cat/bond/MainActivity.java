package eg.com.cat.bond;


import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnUtteranceCompletedListener {

    String []toSpeak ;

    TextView text;
    Button button;

    int index = 0;

    //TTS object
    private TextToSpeech textToSpeech;
    //status check code
    private int MY_DATA_CHECK_CODE = 0;

    TextToSpeech.OnUtteranceCompletedListener completedListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.text);
        button = findViewById(R.id.button);

        toSpeak = new String[] {getResources().getString(R.string.s1),
                getResources().getString(R.string.s2),
                getResources().getString(R.string.s3),
                getResources().getString(R.string.s4),
                getResources().getString(R.string.s5)
        };


        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int ttsLang = textToSpeech.setLanguage(Locale.US);

                    if (ttsLang == TextToSpeech.LANG_MISSING_DATA
                            || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "The Language is not supported!");
                    } else {
                        Log.i("TTS", "Language Supported.");
                    }
                    Log.i("TTS", "Initialization success.");
                    intro();
                } else {
                    Toast.makeText(getApplicationContext(), "TTS Initialization failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        textToSpeech.setOnUtteranceCompletedListener(completedListener);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void intro() {

        text.setText(toSpeak[index]);
        speakWords(toSpeak[index]);

    }

    private void speakWords(String data) {

        int speechStatus = textToSpeech.speak(data, TextToSpeech.QUEUE_FLUSH, null);

        if (speechStatus == TextToSpeech.ERROR) {
            Log.e("TTS", "Error in converting Text to Speech!");
        }

    }

    public void onUtteranceCompleted(String uttId) {
        if (index < toSpeak.length) {
            ++index;
            intro();
        }
    }
}
