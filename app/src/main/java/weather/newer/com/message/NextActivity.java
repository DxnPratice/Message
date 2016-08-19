package weather.newer.com.message;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class NextActivity extends AppCompatActivity {
    MathThraed    maththread;
    EditText  edittext;
    TextView  textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        maththread=new MathThraed();
        edittext= (EditText) findViewById(R.id.editText);
        textview= (TextView) findViewById(R.id.textView2);
        maththread.start();
    }

    public void getTwo(View view) {
    }

    public void getThree(View view) {
        String input=edittext.getText().toString();
        int n=Integer.parseInt(input);
        Message msg=Message.obtain();
        msg.what= MathThraed.MathHandler.MSG_THREE;
        msg.arg1=n;
        maththread.handler.sendMessage(msg);

    }

    /**
     *
     *
     * 消息驱动的线程
     * 发送消息做事情 不发消息即停止
     */
    class  MathThraed extends Thread{
    MathHandler    handler;
        @Override
        public void run() {
            //创建消息循环：创建一个消息队列，绑定到当前线程
            Looper.prepare();
            //创建消息处理器，绑定了当前线程的消息循环和消息队列
           handler=new MathHandler();

            //开始循环
            Looper.loop();
        }


        class MathHandler extends Handler {
            private static final int MSG_TWO = 1;
            private static final int MSG_THREE =2 ;

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch(msg.what){
                    case MSG_TWO:

                        break;
                    case MSG_THREE:
                        handlerTwo(msg);
                        break;
                }
            }

            private void handlerTwo(Message msg) {
                int n=msg.arg1;
                //运算
                final int  r=n*n*n;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //显示结果，发送广播
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textview.setText(String.valueOf(r));
                    }
                });


            }
        }

    }


}
