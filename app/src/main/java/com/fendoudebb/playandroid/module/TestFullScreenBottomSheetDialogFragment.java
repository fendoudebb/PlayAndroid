package com.fendoudebb.playandroid.module;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;

import com.fendoudebb.playandroid.R;


public class TestFullScreenBottomSheetDialogFragment extends BottomSheetDialogFragment {
    private BottomSheetBehavior mBehavior;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.test_bottom_sheet_dialog, null);
        dialog.setContentView(view);
        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        mBehavior.setSkipCollapsed(true);

        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);//全屏展开
        }
    }

}
