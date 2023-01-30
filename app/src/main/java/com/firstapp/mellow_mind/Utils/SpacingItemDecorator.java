package com.firstapp.mellow_mind.Utils;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpacingItemDecorator extends RecyclerView.ItemDecoration {
    private final int verticalSpaceHeight;

    public SpacingItemDecorator(int verticalSpaceHeight1){

        this.verticalSpaceHeight = verticalSpaceHeight1;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.bottom = verticalSpaceHeight;
    }
}
