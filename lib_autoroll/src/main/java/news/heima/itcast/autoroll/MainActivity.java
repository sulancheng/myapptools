package news.heima.itcast.autoroll;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import news.heima.itcast.mylibrary.CommenUtils;

public class MainActivity extends Activity {

    private AutoRollLayout mAutoRollLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAutoRollLayout = (AutoRollLayout) findViewById(R.id.arl);

        //当ViewPager有ImageView孩子的时候，AutoRollLayout点击事件就不会被调用
        //OnClickListener 里面的onClick方法被  方法public boolean performClick()调用】

        // onTouchEvent----> performClick()----->onClick


        //onTouchEvent  当前View是CLICKABLE调用performClick方法并返回true， 不可点击返回flase

        //当View设置setOnClickListener的时候控件变为可以点击setClickable(true);


        //这里AutoRollLayout的onClick不被调用的正真原因是onTouchEvent没有被调用

        //如果一个控件是可以点击的，当点击的时候，onTouchEvent返回true,就消费了这个事件


        //View可以设置setOnTouchListener --->onTouch方法，这个方法被事件分发的方法dispatchTouchEvent调用先与View自己的OnTouchEvent方法调用
        //注意：如果onTouch方法返回了true, View方法的时候就认为onTouch方法消费了事件就不再调用自己的OnTouchEvent方法调用方法


        mAutoRollLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CommenUtils.showSingleToast(getApplicationContext(), "AutoRollLayout: onClick");
            }
        });

        mAutoRollLayout.setOnItemClickListener(new AutoRollLayout.OnItemClickListener() {
            @Override
            public void onItemClick(RollItem rollItem) {

                CommenUtils.showSingleToast(getApplicationContext(), rollItem.title);
            }
        });
    }

    public void show1(View view) {

        List<RollItem> list = new ArrayList<RollItem>();
        list.add(new RollItem("美女1", "http://192.168.56.1:8080/zhbj/photos/images/46728356JBUO.jpg"));
//        list.add(new RollItem("美女2", "http://192.168.56.1:8080/zhbj/photos/images/271633973PFBD.jpg"));
//        list.add(new RollItem("美女3", "http://192.168.56.1:8080/zhbj/photos/images/2075270486S6OP.jpg"));
//        list.add(new RollItem("美女4", "http://192.168.56.1:8080/zhbj/photos/1651807854W6KO.jpg"));
//        list.add(new RollItem("美女5", "http://192.168.56.1:8080/zhbj/photos/images/4672835675CV.jpg"));

        mAutoRollLayout.setData(list);
    }


    public void show2(View view) {

        List<RollItem> list = new ArrayList<RollItem>();
        list.add(new RollItem("美女1", "http://192.168.56.1:8080/zhbj/photos/images/46728356PCLM.jpg"));
        list.add(new RollItem("美女2", "http://192.168.56.1:8080/zhbj/photos/images/1159137654MWR8.jpg"));
        //list.add(new RollItem("美女3", "http://192.168.56.1:8080/zhbj/photos/images/1475338884JOAT.jpg"));
        //list.add(new RollItem("美女4", "http://192.168.56.1:8080/zhbj/photos/1477262385KXA2.jpg"));
        //list.add(new RollItem("美女5", "http://192.168.56.1:8080/zhbj/photos/images/1571461527JCBR.jpg"));

        mAutoRollLayout.setData(list);
    }

    public void show3(View view) {

        List<RollItem> list = new ArrayList<RollItem>();
        list.add(new RollItem("美女1", "http://192.168.56.1:8080/zhbj/photos/images/4672835620H3.jpg"));
        list.add(new RollItem("美女2", "http://192.168.56.1:8080/zhbj/photos/images/271633973PFBD.jpg"));
        list.add(new RollItem("美女3", "http://192.168.56.1:8080/zhbj/photos/images/4672835624SS.jpg"));
        list.add(new RollItem("美女4", "http://192.168.56.1:8080/zhbj/photos/153821477101GE.jpg"));
        list.add(new RollItem("美女5", "http://192.168.56.1:8080/zhbj/photos/images/160009067810KV.jpg"));

        mAutoRollLayout.setData(list);
    }

    public void startAuto(View view) {

        mAutoRollLayout.startAuto();
    }

    public void stopAuto(View view) {

        mAutoRollLayout.stopAuto();
    }
}
