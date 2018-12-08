package tk.onlinesilkstore.asynchtasking;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static int Sets;
    private static int Time;
    private EditText et1;
    private TextView tv1,tv2,tv3;
    private static int j=0;
    private static final String TAG = "MainActivity";
    private MediaPlayer mediaPlayer;
    private Button btn1;
    private AsyncTask_Exercise exercise;
    int reset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1=findViewById(R.id.enter_time);
        tv1=findViewById(R.id.display_seconds);
        tv3=findViewById(R.id.display_minute);
        btn1=findViewById(R.id.start);
        //tv3.setVisibility(View.GONE);
        tv2=findViewById(R.id.sets);
       // tv2.setVisibility(View.GONE);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et1.getText().toString().trim().length()==0)
                {
                    Toast.makeText(v.getContext(),"Ichi thu enter time before starting",Toast.LENGTH_LONG);
                    return;
                }
                if(et1.getText().toString().equals("0"))
                {
                    Toast.makeText(v.getContext(),"Zeroleee Start Panna mudiyathu",Toast.LENGTH_LONG);
                    return;
                }

                if(btn1.getText().equals("START")) {
                    reset=0;

                        int k = Integer.parseInt(et1.getText().toString());
                         exercise= new AsyncTask_Exercise();
                        exercise.execute(k * 60);
                        et1.setVisibility(View.GONE);
                        btn1.setText("STOP");
                    }

                else if(btn1.getText().equals("STOP"))
                { exercise.cancel(true);
                if(mediaPlayer!=null) {
                    mediaPlayer.stop();
                }
                    btn1.setText("START");
                }
            }
        });
    }




    public void reset(View view) {
        reset=1;
        if(reset==1) {


            exercise.cancel(true);
            Sets = 0;
            Time = 0;
            j = 0;
            et1.setText("0");
            tv1.setText("0");
            tv2.setText("Sets");
            tv3.setText("0");
            et1.setVisibility(View.VISIBLE);
            btn1.setText("START");
            if(mediaPlayer!=null) {
                mediaPlayer.stop();
            }
        }
    }

    private class AsyncTask_Exercise extends AsyncTask<Integer, Integer, String>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Integer... integers) {

             for (int i = 0; i <= integers[0]; i++) {
                 if(!isCancelled()) {
                 Time = i;
                 onProgressUpdate(Time);
                 try {
                     Thread.sleep(1000);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
             }
         }
             return "Today Exercise Completed";

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Random random= new Random();
            int nextInt_m = random.nextInt(0xffffff + 5);
            String colorCode_m = String.format("#%06x", nextInt_m);
            int nextInt_s = random.nextInt(0xffffff + 10);
            String colorCode_s = String.format("#%06x", nextInt_s);
            int nextInt_sets = random.nextInt(0xffffff + 15);
            String colorCode_sets = String.format("#%06x", nextInt_sets);
            //tv3.setVisibility(View.VISIBLE);
            int min=(values[0]/60);
            tv3.setText(String.valueOf(min));
            if(min!=0) {
                tv3.setTextColor(Color.parseColor(colorCode_m));
            }

            int k=Integer.parseInt(et1.getText().toString());
            int l=values[0];

            Log.d(TAG, "onProgressUpdate: valoe of K is"+k);
            Log.d(TAG, "onProgressUpdate: valoe of l is"+l);
            tv1.setText(String.valueOf(l-(min*60)));
            tv1.setTextColor(Color.parseColor(colorCode_s));
            if((k*60)==l)
            {


                tv1.setText("0");

                mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.okay);

                mediaPlayer.start();
                mediaPlayer.setLooping(true);

                j=j+1;
                tv2.setText(String.valueOf("Set-"+j));
                tv2.setTextColor(Color.parseColor(colorCode_sets));

               //

            }

        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            et1.setVisibility(View.VISIBLE);
        }


    }
}
