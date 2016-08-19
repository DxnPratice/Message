package weather.newer.com.message;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final int MSG_UPDATE_UI = 1;
    TextView textview;
    DemoThread  thread;
    //当前线程的消息处理器
    Handler handler=new UiHandler();
    int i=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textview= (TextView) findViewById(R.id.textView);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*   thread=new DemoThread();
        thread.start();*/
    }

    public void todoA(View view) {
    /*   //点击按钮
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        //整个要执行的动作放在消息队列中
       // handler.post(uiupdatecallback) ;
        Message msg=Message.obtain();//创建消息，从消息池中取出的可以重复使用的消息
        //给消息一个标志
        msg.what =MSG_UPDATE_UI;
        msg.arg1=i++;
        msg.arg2=9;
        msg.obj="abc";
        handler.sendMessage(msg);




    }

    public void todoB(View view) {
      /*  try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        //handler.removeCallbacks(uiupdatecallback);
        handler.removeMessages(MSG_UPDATE_UI);
    }

    class DemoThread extends Thread{
      volatile   boolean isRunning =true;
        @Override
        public void run() {
            super.run();
            while(isRunning){
                try {
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //子线程访问ui
              //  textview.setText(String.valueOf(i++));
                //可以在子线程中间接的访问ui
                //给ui线程的消息队列发送消息对象
                //第一种方法
             /*   runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textview.setText(String.valueOf(i++));
                    }
                });*/

                //第二种方法
             /*   textview.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });*/

                //第三种方法，发送要执行的行为和动作（异步执行的任务，不会马上执行）
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });

            }

        }
    }
    Runnable uiupdatecallback=new Runnable() {
        @Override
        public void run() {
            textview.setText(String.valueOf(i++));
            //
            handler.postDelayed(this,1000);

        }
    };
    class  UiHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //获得消息的标志
            int what=msg.what;
            switch(what){
                case MSG_UPDATE_UI:
                    handlerUpdateUi(msg);
                    break;
                case 2:
                    break;
                case 3:
                    break;
            }
        }

        private void handlerUpdateUi(Message msg) {
            int n=msg.arg1;
            textview.setText(String.valueOf(n));
            n++;
            //再次发送消息，一个消息只能发送一次，所以要重新新建消息'
            Message m=Message.obtain();
            m.what=MSG_UPDATE_UI;
            m.arg1=n;
            sendMessageDelayed(m,1000);

        }
    }

}
