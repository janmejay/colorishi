package com.github.colorishi;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.speech.tts.TextToSpeech;
import android.view.*;

public class ColorChangingView extends SurfaceView implements SurfaceHolder.Callback {

    public static final class C {
        final int color;
        final String name;

        public C(int color, String name) {
            this.color = color;
            this.name = name;
        }
    }

    private static C[] COLORS = new C[]{
            new C(Color.RED, "Red"),
            new C(Color.DKGRAY, "Dark grey"),
            new C(Color.GREEN, "Green"),
            new C(Color.CYAN, "Cyan"),
            new C(Color.YELLOW, "Yellow"),
            new C(Color.BLUE, "Blue"),
            new C(Color.MAGENTA, "Magenta"),
            new C(Color.GRAY, "Grey"),
            new C(Color.WHITE, "White")
    };
    private int currentColorIdx;
    private TextToSpeech mTts;

    public ColorChangingView(Context context) {
        super(context);
        currentColorIdx = 0;
        getHolder().addCallback(this);
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        refresh(surfaceHolder);
    }

    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    private void refresh(SurfaceHolder surfaceHolder) {
        Canvas canvas = surfaceHolder.lockCanvas();
        C c = COLORS[currentColorIdx];
        canvas.drawColor(c.color);
        surfaceHolder.unlockCanvasAndPost(canvas);
        if (mTts != null) {
            mTts.speak(c.name, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        currentColorIdx++;
        currentColorIdx %= COLORS.length;
        refresh(getHolder());
        return true;
    }

    public void setTts(TextToSpeech mTts) {

        this.mTts = mTts;
    }
}
