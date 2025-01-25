package com.example.stockmarketsdk;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

public class CustomMarkerView extends MarkerView {

    private final TextView textView;

    public CustomMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        textView = findViewById(R.id.marker_text); // טקסט להצגת הערכים
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        // עדכון הטקסט בערכים של הנקודה שנבחרה
        textView.setText("X: " + e.getX() + ", Y: " + e.getY());
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        // מיקום ה-Marker ביחס לנקודה
        return new MPPointF(-(getWidth() / 2f), -getHeight());
    }
}
