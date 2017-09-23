package com.example.android.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

/**
 * Created by 92324 on 2017/8/29.
 */

public class LineWidthDialogFragment extends AppCompatDialogFragment {
    private Button mSetButton;
    private LineWidthView mLineWidthView;
    private SeekBar mSeekBarLineWidth;
    private float mLineWidth = 1.0f;
    private static final String FRAGMENT_LINE_WIDTH_KEY = "lineWidth";

    public interface LineWidthDialogListener{
         void onDialogLineSetClick(AppCompatDialogFragment dialog);
    }


    public static LineWidthDialogFragment newInstance(float lineWidth)
    {
        LineWidthDialogFragment fragment = new LineWidthDialogFragment();
        Bundle args = new Bundle();
        args.putFloat(FRAGMENT_LINE_WIDTH_KEY,lineWidth);
        fragment.setArguments(args);
        return fragment;
    }

    private LineWidthDialogListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_line_width,null);
        builder.setView(view);
        mLineWidth = getArguments().getFloat(FRAGMENT_LINE_WIDTH_KEY);
        mSetButton = view.findViewById(R.id.buttonSet);
        mLineWidthView = view.findViewById(R.id.imageViewLine);
        mLineWidthView.setLineText(mLineWidth);
        mSeekBarLineWidth = view.findViewById(R.id.seekBarLineWidth);
        mSeekBarLineWidth.setProgress((int)mLineWidth);
        mSeekBarLineWidth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mLineWidthView.setLineText(i);
                mLineWidth = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onDialogLineSetClick(LineWidthDialogFragment.this);
                dismiss();
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the LineWidthDialogListener so we can send events to the host
            mListener = (LineWidthDialogListener)context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement LineWidthDialogListener");
        }
    }

    public float getLineWidth() {
        return mLineWidth;
    }
}
