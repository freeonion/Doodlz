package com.example.android.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by 92324 on 2017/8/29.
 */

public class ColorSelectionDialogFragment extends AppCompatDialogFragment {
    private Button mButtonColor;
    private SeekBar mSeekBarAlpha;
    private SeekBar mSeekBarRed;
    private SeekBar mSeekBarGreen;
    private SeekBar mSeekBarBlue;
    private TextView mTextView;
    private int mColor  = Color.BLACK;
    private static final String FRAGMENT_COLOR_SELECTION_KEY = "ColorSelection";
    private ColorSelectionDialogListener mListener;

    public interface ColorSelectionDialogListener{
        void onDialogColorSetClick(AppCompatDialogFragment dialog);
    }

    public static ColorSelectionDialogFragment newInstance(int color)
    {
        ColorSelectionDialogFragment fragment = new ColorSelectionDialogFragment();
        Bundle args = new Bundle();
        args.putInt(FRAGMENT_COLOR_SELECTION_KEY,color);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_color_selection,null);
        mButtonColor = view.findViewById(R.id.buttonColorSel);
        mSeekBarAlpha = view.findViewById(R.id.seekBarAlpha);
        mSeekBarBlue = view.findViewById(R.id.seekBarBlue);
        mSeekBarGreen = view.findViewById(R.id.seekBarGreen);
        mSeekBarRed = view.findViewById(R.id.seekBarRed);
        mTextView = view.findViewById(R.id.textViewColor);
        mColor = getArguments().getInt(FRAGMENT_COLOR_SELECTION_KEY);
        int A = (mColor >> 24) & 0xff;
        int R = (mColor >> 16) & 0xff;
        int G = (mColor >>  8) & 0xff;
        int B = (mColor      ) & 0xff;
        mSeekBarAlpha.setProgress(A);
        mSeekBarRed.setProgress(R);
        mSeekBarGreen.setProgress(G);
        mSeekBarBlue.setProgress(B);
        mTextView.setBackgroundColor(mColor);

        mSeekBarRed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mColor = (mSeekBarAlpha.getProgress() & 0xff) << 24 | (mSeekBarRed.getProgress() & 0xff) << 16 | (mSeekBarGreen.getProgress() & 0xff) << 8 | (mSeekBarBlue.getProgress() & 0xff);
                mTextView.setBackgroundColor(mColor);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mSeekBarGreen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mColor = (mSeekBarAlpha.getProgress() & 0xff) << 24 | (mSeekBarRed.getProgress() & 0xff) << 16 | (mSeekBarGreen.getProgress() & 0xff) << 8 | (mSeekBarBlue.getProgress() & 0xff);
                mTextView.setBackgroundColor(mColor);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mSeekBarBlue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mColor = (mSeekBarAlpha.getProgress() & 0xff) << 24 | (mSeekBarRed.getProgress() & 0xff) << 16 | (mSeekBarGreen.getProgress() & 0xff) << 8 | (mSeekBarBlue.getProgress() & 0xff);
                mTextView.setBackgroundColor(mColor);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mSeekBarAlpha.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mColor = (mSeekBarAlpha.getProgress() & 0xff) << 24 | (mSeekBarRed.getProgress() & 0xff) << 16 | (mSeekBarGreen.getProgress() & 0xff) << 8 | (mSeekBarBlue.getProgress() & 0xff);
                mTextView.setBackgroundColor(mColor);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mButtonColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onDialogColorSetClick(ColorSelectionDialogFragment.this);
                dismiss();
            }
        });
        builder.setView(view);
        return builder.create();
    }

    public int getColor() {
        return mColor;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the LineWidthDialogListener so we can send events to the host
            mListener = (ColorSelectionDialogFragment.ColorSelectionDialogListener)context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement LineWidthDialogListener");
        }
    }
}
