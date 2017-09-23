package com.example.android.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 92324 on 2017/9/1.
 */

public class DrawView extends View {

    //paint的初始化设置
    private Paint paintTemp = new Paint();
    //正在进行的path
    private Path pathDrawing = null;
    //已经完成的paths
    private HashMap<Integer,Path> pathMap = new HashMap<>();
    private ArrayList<Path> pathList = new ArrayList<>();
    private ArrayList<Paint> paintList = new ArrayList<>();


    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int actionIndex = event.getActionIndex();
        final int pointCount = event.getPointerCount();
        //GetActionMasked()---Return the masked action being performed, without pointer index information.
        //GetAction()---The action, such as ACTION_DOWN or the combination of ACTION_POINTER_DOWN with a shifted pointer index
        switch(event.getActionMasked())
        {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if(!pathMap.containsKey(event.getPointerId(actionIndex)))
                {
                    Path tempPath = new Path();
                    pathMap.put(event.getPointerId(actionIndex),tempPath);
                    tempPath.moveTo(event.getX(actionIndex),event.getY(actionIndex));
                    pathList.add(tempPath);
                    paintList.add(new Paint(paintTemp));
                }
                invalidate();
                break;
            //尝试过只画actionIndex的图，但发现多指操作时，只有一个可以实时画，其它都是在action_up/action_pointer_up才画的
            case MotionEvent.ACTION_MOVE:
               for(int i = 0; i < pointCount; i++)
                {
                    if(pathMap.containsKey(event.getPointerId(i)))
                    {
                        pathDrawing = pathMap.get(event.getPointerId(i));
                        pathDrawing.lineTo(event.getX(i),event.getY(i));
                    }
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if(pathMap.containsKey(event.getPointerId(actionIndex)))
                {
                    Path path = pathMap.get(event.getPointerId(actionIndex));
                    path.lineTo(event.getX(actionIndex),event.getY(actionIndex));
                    pathMap.remove(event.getPointerId(actionIndex));
                }
                invalidate();
                break;
            default:
                return super.onTouchEvent(event);
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for(int i = 0; i < pathList.size(); i++)
        {
            canvas.drawPath(pathList.get(i),paintList.get(i));
        }
    }

    public void setPaint(int Color, float strokeWidth)
    {
        if(paintTemp != null)
        {
            paintTemp.setColor(Color);
            paintTemp.setStyle(Paint.Style.STROKE);
            paintTemp.setStrokeWidth(strokeWidth);
        }
    }

    public void erasePath()
    {
        pathList.clear();
        paintList.clear();
        pathMap.clear();
        invalidate();
    }

    public boolean saveToPic(File file)
    {
        boolean ret = false;
        if(!pathList.isEmpty())
        {
            Bitmap bitmap = Bitmap.createBitmap(getWidth(),getHeight(),Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            //Manually render this view (and all of its children) to the given Canvas
            draw(canvas);
            try {
                ret = bitmap.compress(Bitmap.CompressFormat.PNG,100,new FileOutputStream(file));
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return ret;
    }
}
