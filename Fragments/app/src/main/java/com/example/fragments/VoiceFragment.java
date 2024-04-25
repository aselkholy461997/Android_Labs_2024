package com.example.fragments;

import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;

import android.speech.RecognitionListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VoiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VoiceFragment extends Fragment implements RecognitionListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public VoiceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VoiceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VoiceFragment newInstance(String param1, String param2) {
        VoiceFragment fragment = new VoiceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private ActivityResultLauncher<Void> speechRecognitionLauncher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_voice, container, false);

        // Initialize the ActivityResultLauncher
        speechRecognitionLauncher = registerForActivityResult(new VoiceResultContract(), result -> {
            if (result != null) {
                ((TextView) rootView.findViewById(R.id.txt_speech)).setText(result);
            }
        });

        ImageButton btnSpeak = rootView.findViewById(R.id.speak_btn);
        btnSpeak.setOnClickListener(v -> {
            // Launch the speech recognition activity
            speechRecognitionLauncher.launch(null);
        });

        return rootView;
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
        // This method is called when the speech recognition engine is ready to receive speech input
    }

    @Override
    public void onBeginningOfSpeech() {
        // This method is called when the user starts speaking
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        // This method provides the root mean square (RMS) value of the audio input
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        // This method is called when the audio input buffer is received
    }

    @Override
    public void onEndOfSpeech() {
        // This method is called when the user stops speaking
    }

    @Override
    public void onError(int error) {
        // This method is called when an error occurs during speech recognition
    }

    @Override
    public void onResults(Bundle results) {
        // This method is called when the speech recognition engine returns results
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        // This method is called when partial recognition results are available
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
        // This method is called when a speech recognition event occurs
    }
}