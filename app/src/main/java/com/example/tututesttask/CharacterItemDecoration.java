package com.example.tututesttask;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

// setting the position of the tiles RecyclerView
public class CharacterItemDecoration extends RecyclerView.ItemDecoration {
    private final int offset;
    private final float radius;
    private final RectF defaultRectToClip;
    public CharacterItemDecoration(int offset,float radius ){
        this.offset = offset;
        this.radius = radius;
        defaultRectToClip = new RectF(Float.MAX_VALUE, Float.MAX_VALUE, 0, 0);
    }
    @Override
    public void getItemOffsets(Rect outRect, View view , RecyclerView parent, RecyclerView.State state){
        GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) view.getLayoutParams();

        if(layoutParams.getSpanIndex() % 2 == 0){
            outRect.top = offset;
            outRect.left = offset;
            outRect.right = offset / 2;
        } else {
            outRect.top = offset;
            outRect.right = offset;
            outRect.left = offset / 2;
        }
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        final RectF rectToClip = getRectToClip(parent);

        // has no items with ViewType == `R.layout.item_image`
        if (rectToClip.equals(defaultRectToClip)) {
            return;
        }

        final Path path = new Path();
        path.addRoundRect(rectToClip, radius, radius, Path.Direction.CW);
        canvas.clipPath(path);
    }

    private RectF getRectToClip(RecyclerView parent) {
        final RectF rectToClip = new RectF(defaultRectToClip);
        final Rect childRect = new Rect();
        for (int i = 0; i < parent.getChildCount(); i++) {
            if (!isImage(parent, i)) {
                continue;
            }

            final View child = parent.getChildAt(i);
            parent.getDecoratedBoundsWithMargins(child, childRect);

            rectToClip.left = Math.min(rectToClip.left, childRect.left);
            rectToClip.top = Math.min(rectToClip.top, childRect.top);
            rectToClip.right = Math.max(rectToClip.right, childRect.right);
            rectToClip.bottom = Math.max(rectToClip.bottom, childRect.bottom);
        }
        return rectToClip;
    }

    private boolean isImage(RecyclerView parent, int viewPosition) {
        final RecyclerView.Adapter adapter = parent.getAdapter();
        final int viewType = adapter.getItemViewType(viewPosition);
        return viewType == R.drawable.rounded_item;
    }
}
