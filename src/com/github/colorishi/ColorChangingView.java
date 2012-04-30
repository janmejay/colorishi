package com.github.colorishi;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.speech.tts.TextToSpeech;
import android.view.*;

public class ColorChangingView extends SurfaceView implements SurfaceHolder.Callback {

    private static final String WELCOME_MESSAGE = "Welcome to Colorishi! Touch the screen to start.";

    public static final class C {
        final int color;
        final String name;

        public C(int color, String name) {
            this.color = color;
            this.name = name;
        }

        public void sayIt(TextToSpeech tts) {
            if (tts != null) {
                tts.speak(String.format("This is %s.", name), TextToSpeech.QUEUE_FLUSH, null);
            }
        }
    }

    private static C[] COLORS = new C[]{
            new C(Color.RED, "Red"),
            new C(Color.GREEN, "Green"),
            new C(Color.BLUE, "Blue"),
            new C(Color.CYAN, "Cyan"),
            new C(Color.MAGENTA, "Magenta"),
            new C(Color.YELLOW, "Yellow"),
            new C(Color.GRAY, "Grey"),
            new C(Color.WHITE, "White"),
            new C(Color.BLACK, "Black"),
            new C(Color.rgb(0xff, 0x9a, 0xcc), "Pink"),
            new C(Color.rgb(0xff, 0x93, 0x00), "Orange"),
            new C(Color.rgb(0xb2, 0x00, 0xcd), "Purple"),
    };

    private int currentColorIdx;
    private volatile TextToSpeech mTts;

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
        c.sayIt(mTts);
        surfaceHolder.unlockCanvasAndPost(canvas);
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
        mTts.speak(WELCOME_MESSAGE, TextToSpeech.QUEUE_FLUSH, null);
    }
}
