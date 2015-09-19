package tw.com.hycleo.keypad.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import tw.com.hycleo.keypad.R;
import tw.com.hycleo.keypad.core.KeypadCore;
import tw.com.hycleo.keypad.model.KeypadItem;

/**
 * Created by chenhanyu on 15/9/19.
 */
public class KeypadView extends LinearLayout {

    private LayoutInflater layoutInflater;

    private List<KeypadItem> items;

    private KeypadCore keypadCore;

    private LinearLayout resultView;

    private String currentInput;

    private String currentInputStr;

    private final int[] btnIds = {R.id.btn_00, R.id.btn_01, R.id.btn_02, R.id.btn_03, R.id.btn_04, R.id.btn_05, R.id.btn_06, R.id.btn_07, R.id.btn_08, R.id.btn_09,
            R.id.btn_10, R.id.btn_11, R.id.btn_12, R.id.btn_13, R.id.btn_14, R.id.btn_15, R.id.btn_16, R.id.btn_17, R.id.btn_18, R.id.btn_19,
            R.id.btn_20, R.id.btn_21, R.id.btn_22, R.id.btn_23, R.id.btn_24, R.id.btn_25, R.id.btn_26, R.id.btn_27, R.id.btn_28, R.id.btn_29,
            R.id.btn_30, R.id.btn_31, R.id.btn_32, R.id.btn_33, R.id.btn_34, R.id.btn_35, R.id.btn_36, R.id.btn_37, R.id.btn_38, R.id.btn_39,
            R.id.btn_45};

    public KeypadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        layoutInflater = LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.keypad_view, this);
        resultView = (LinearLayout) findViewById(R.id.resultView);
        keypadCore = new KeypadCore(context);
        currentInput="";
        currentInputStr="";
        initFuntionKeyItem();
    }

    public void initFuntionKeyItem() {
        Button btnKeyToNum = (Button) findViewById(R.id.btn_40);
        Button btnKeyToPage = (Button) findViewById(R.id.btn_41);
        Button btnKeyComma = (Button) findViewById(R.id.btn_42);
        Button btnKeyDot = (Button) findViewById(R.id.btn_43);
        Button btnKeySpace = (Button) findViewById(R.id.btn_44);
        Button btnKeyBackspace = (Button) findViewById(R.id.btn_46);
        btnKeyBackspace.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentInput.length()>0) {
                    currentInput = currentInput.substring(0, currentInput.length() - 1);
                    currentInputStr = currentInputStr.substring(0, currentInputStr.length() - 1);
                    List<String> results = keypadCore.find(currentInput);
                    refreshResultShow(results);
                }
            }
        });
        Button btnKeyEnter = (Button) findViewById(R.id.btn_47);
    }

    public void initDefaultKeypadItem(){
        items = new ArrayList<>();
        String[] keypadItems = getContext().getResources().getStringArray(R.array.keypadItems);
        for(String keypadItem:keypadItems){
            String id = keypadItem.split(" ")[0];
            String word = keypadItem.split(" ")[1];
            items.add(new KeypadItem(id,word));
        }
        initKeypadItem(items);
    }

    public void initKeypadItem(List<KeypadItem> items) {
        this.items = items;
        int index = 0;
        for (KeypadItem item : items) {
            if (index + 1 <= btnIds.length) {
                Button btn = (Button) findViewById(btnIds[index]);
                btn.setText(item.getWord());
                btn.setTag(item.getId());
                btn.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Button btn = (Button)v;
                        if(currentInput.length()<4) {
                            currentInput += btn.getTag().toString();
                            currentInputStr += btn.getText().toString();
                            List<String> results = keypadCore.find(currentInput);
                            refreshResultShow(results);
                        }
                    }
                });
            }
            index++;
        }
    }

    public void refreshResultShow(List<String> results){
        resultView.removeAllViews();
        if(results!=null&&results.size()>0){
            for(String result:results){
                String word = result.split(" ")[1];
                Button btn = new Button(getContext());
                btn.setText(word);
                resultView.addView(btn);
            }
        }else if(currentInput.length()>0){
            Button btn = new Button(getContext());
            btn.setText(currentInputStr);
            resultView.addView(btn);
        }
    }
}
