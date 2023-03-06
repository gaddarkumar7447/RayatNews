package com.ftg2021.rayatnews.Scroll;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.ftg2021.rayatnews.MainActivity;

public class SnapHelperOneByOne extends LinearSnapHelper {

    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {

        if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
            return RecyclerView.NO_POSITION;
        }

        final View currentView = findSnapView(layoutManager);

        if (currentView == null)
            return RecyclerView.NO_POSITION;

        LinearLayoutManager myLayoutManager = (LinearLayoutManager) layoutManager;
        int position1 = myLayoutManager.findFirstVisibleItemPosition();
        int position2 = myLayoutManager.findLastVisibleItemPosition();
        int currentPosition = layoutManager.getPosition(currentView);

//        velocityY *= 100;

        if (velocityY > 100)
            currentPosition = position2;
        else if (velocityY < 100)
            currentPosition = position1;
//        if (velocityY > 10000)
//            currentPosition = position2;
//        else
//            currentPosition = position1;

        if (currentPosition == RecyclerView.NO_POSITION)
            return RecyclerView.NO_POSITION;

        if (MainActivity.appBar.getVisibility() == View.VISIBLE) {
            MainActivity.appBar.setVisibility(View.GONE);
            MainActivity.bottomNavigationView.setVisibility(View.GONE);
            MainActivity.blackBoxBehindBottomNav.setVisibility(View.GONE);
            MainActivity.visibility = false;
        }

        return currentPosition;
    }
}