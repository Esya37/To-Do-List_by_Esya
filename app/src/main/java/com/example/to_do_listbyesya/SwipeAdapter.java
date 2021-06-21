package com.example.to_do_listbyesya;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_SWIPE;


public class SwipeAdapter extends ItemTouchHelper.Callback {

    private boolean swipeBack=false;

    enum ButtonsState {NOT_VISIBLE, LEFT_VISIBLE, RIGHT_VISIBLE}

    ;
    private ButtonsState buttonsState = ButtonsState.NOT_VISIBLE;
    private static final float buttonWidth = 300;
    private RectF buttonInstance = null;
    private Paint p = new Paint();
    private RecyclerView.ViewHolder currentItemViewHolder = null;
    private SwipeAdapterActions buttonsActions = null;


    public SwipeAdapter(SwipeAdapterActions buttonsActions) {
        this.buttonsActions = buttonsActions;
    }
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        if (swipeBack == true) {
            //swipeBack = false;
            if(buttonsState != ButtonsState.NOT_VISIBLE) {
                swipeBack = true;
            }
            else{
                swipeBack = false;
            }
            return 0;
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        drawButtons(c, viewHolder);
        if (actionState == ACTION_STATE_SWIPE) {
            if (buttonsState != ButtonsState.NOT_VISIBLE) {
                if (buttonsState == ButtonsState.LEFT_VISIBLE) {
                    dX = Math.max(dX, buttonWidth);
                }
                if (buttonsState == ButtonsState.RIGHT_VISIBLE) {
                    dX = Math.min(dX, -buttonWidth);
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
            else {
                setTouchListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }
        if (buttonsState == ButtonsState.NOT_VISIBLE) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
        currentItemViewHolder = viewHolder;
    }

    private void setTouchListener(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP) {
                    swipeBack = true;
                } else {
                    swipeBack = false;
                }
                if (swipeBack == true) {
                    if (dX < -buttonWidth) {
                        buttonsState = ButtonsState.RIGHT_VISIBLE;
                    } else if (dX > buttonWidth) {
                        buttonsState = ButtonsState.LEFT_VISIBLE;
                    }
                    if (buttonsState != ButtonsState.NOT_VISIBLE) {
                        setTouchDownListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                        setItemsClickable(recyclerView, false);
                    }
                }
                return false;
            }
        });
    }

    private void setTouchDownListener(final Canvas c, final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder, final float dX, final float dY, final int actionState, final boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    setTouchUpListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
                return false;
            }
        });
    }

    private void setTouchUpListener(final Canvas c, final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder, final float dX, final float dY, final int actionState, final boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    SwipeAdapter.super.onChildDraw(c, recyclerView, viewHolder, 0F, dY, actionState, isCurrentlyActive);
                    recyclerView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return false;
                        }
                    });
                    setItemsClickable(recyclerView, true);
                    swipeBack = false;

                    if (buttonsActions != null && buttonInstance != null && buttonInstance.contains(event.getX(), event.getY())) {
                        if (buttonsState == ButtonsState.LEFT_VISIBLE) {
                            buttonsActions.onEditClick(viewHolder.getAdapterPosition());
                        }
                        else if (buttonsState == ButtonsState.RIGHT_VISIBLE) {
                            buttonsActions.onDeleteClick(viewHolder.getAdapterPosition());
                        }
                    }
                    buttonsState = ButtonsState.NOT_VISIBLE;
                    currentItemViewHolder = null;

                    //buttonsState = ButtonsState.NOT_VISIBLE;
                }
                return false;
            }
        });
    }

    private void setItemsClickable(RecyclerView recyclerView, boolean isClickable) {
        for (int i = 0; i < recyclerView.getChildCount(); i++) {
            recyclerView.getChildAt(i).setClickable(isClickable);
        }
    }

    private void drawButtons(Canvas c, RecyclerView.ViewHolder viewHolder) {
        float buttonWidthWithoutPadding = buttonWidth - 20;
        float corners = 16;

        View itemView = viewHolder.itemView;


        RectF leftButton = new RectF(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + buttonWidthWithoutPadding, itemView.getBottom());
        p.setColor(Color.BLUE);
        c.drawRoundRect(leftButton, corners, corners, p);
        drawText("Изменить", c, leftButton);

        RectF rightButton = new RectF(itemView.getRight() - buttonWidthWithoutPadding, itemView.getTop(), itemView.getRight(), itemView.getBottom());
        p.setColor(Color.RED);
        c.drawRoundRect(rightButton, corners, corners, p);
        drawText("Удалить", c, rightButton);

        buttonInstance = null;
        if (buttonsState == ButtonsState.LEFT_VISIBLE) {
            buttonInstance = leftButton;
        }
        else if (buttonsState == ButtonsState.RIGHT_VISIBLE) {
            buttonInstance = rightButton;
        }
        leftButton=null;
        rightButton=null;
    }

    private void drawText(String text, Canvas c, RectF button) {
        float textSize = 50;
        p.setColor(Color.WHITE);
        p.setAntiAlias(true);
        p.setTextSize(textSize);

        float textWidth = p.measureText(text);
        c.drawText(text, button.centerX()-(textWidth/2), button.centerY()+(textSize/2), p);
    }

    public void onDraw(Canvas c) {
        if (currentItemViewHolder != null) {
            drawButtons(c, currentItemViewHolder);
        }
    }
}
