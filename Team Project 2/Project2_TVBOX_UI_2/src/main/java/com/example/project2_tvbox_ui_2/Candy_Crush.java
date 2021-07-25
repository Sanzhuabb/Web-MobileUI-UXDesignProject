package com.example.project2_tvbox_ui_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.widget.GridLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Candy_Crush extends AppCompatActivity {

    int[] candy= {R.drawable.fruit1, R.drawable.fruit14,R.drawable.fruit18,R.drawable.fruit19,R.drawable.fruit21};

    int withblock,noblock2= 8, screen;
    ArrayList<ImageView> canddy= new ArrayList<>();
    int candyDrag,candyReplace;
    int noCandy=R.drawable.cancel2;
    Handler mhandler;
    int interval=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candy__crush);

        DisplayMetrics displayMetrics= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screen= displayMetrics.widthPixels;
        int h = displayMetrics.heightPixels;
        withblock= screen/noblock2;
        Board();

        for(final ImageView imageView:canddy)
        {
            imageView.setOnTouchListener(new Swiper(this)
            {


                @Override
                void onSwipeLeft() {
                    super.onSwipeLeft();
                    candyDrag= imageView.getId();
                    candyReplace=candyDrag-1;
                    candyInterchange();
                }

                @Override
                void onSwipeRight() {
                    super.onSwipeRight();
                    candyDrag= imageView.getId();
                    candyReplace=candyDrag+1;
                    candyInterchange();
                }

                @Override
                void onSwipeTop() {
                    super.onSwipeTop();
                    candyDrag= imageView.getId();
                    candyReplace=candyDrag-noblock2;
                    candyInterchange();
                }

                @Override
                void onSwipeBottom() {
                    super.onSwipeBottom();
                    candyDrag= imageView.getId();
                    candyReplace=candyDrag+noblock2;
                    candyInterchange();
                }
            });
        }
        mhandler= new Handler();
        startR();
    }
    private void check()
    {
        for(int i=0;i<62;i++)
        {
            int chosedCandy= (int)canddy.get(i).getTag();
            boolean isBlank= (int)canddy.get(i).getTag()==noCandy;
            Integer [] noValid= {100,110,120,130,140,150,160,170,180,190,200,300,400,500};
            List<Integer> list= Arrays.asList(noValid);
            if(!list.contains(i))
            {
                int x= i;
                if ((int)canddy.get(x++).getTag()==chosedCandy&&!isBlank &&
                        (int)canddy.get(x++).getTag()==chosedCandy&&
                        (int) canddy.get(x).getTag()==chosedCandy)
                {
                    canddy.get(x).setImageResource(noCandy);
                    canddy.get(x).setTag(noCandy);
                    x--;

                    canddy.get(x).setImageResource(noCandy);
                    canddy.get(x).setTag(noCandy);
                    x--;

                    canddy.get(x).setImageResource(noCandy);
                    canddy.get(x).setTag(noCandy);

                }
            }
        }
    }
    Runnable checkerR= new Runnable() {
        @Override
        public void run() {
            try{
                check();
            }
            finally{
                mhandler.postDelayed(checkerR,interval);
            }
        }
    };
    void startR()
    {
        checkerR.run();
    }

    private void candyInterchange()
    {
        int bg= (int)canddy.get(candyReplace).getTag();
        int bg1= (int)canddy.get(candyDrag).getTag();
        canddy.get(candyDrag).setImageResource(bg);
        canddy.get(candyReplace).setImageResource(bg1);
        canddy.get(candyDrag).setTag(bg);
        canddy.get(candyReplace).setTag(bg1);

    }
    private void Board(){
        GridLayout gridLayout= findViewById(R.id.board);
        gridLayout.setRowCount(noblock2);
        gridLayout.setColumnCount(noblock2);


        gridLayout.getLayoutParams().width=screen;
        gridLayout.getLayoutParams().height=screen;

        for(int i=0; i<noblock2*noblock2;i++) {
            ImageView imageView = new ImageView(this);
            imageView.setId(i);
            imageView.setLayoutParams(new android.view.ViewGroup.LayoutParams(withblock, withblock));
            imageView.setMaxHeight(withblock);
            imageView.setMaxWidth(withblock);
            int ranCandy = (int) Math.floor(Math.random() * candy.length);

            imageView.setImageResource(candy[ranCandy]);
            imageView.setTag(candy[ranCandy]);
            canddy.add(imageView);
            gridLayout.addView(imageView);
        }
    }
}