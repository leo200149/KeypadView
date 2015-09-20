package tw.com.hycleo.keypad.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tw.com.hycleo.keypad.R;
import tw.com.hycleo.keypad.model.KeypadItem;
import tw.com.hycleo.keypad.util.FileUtil;
import tw.com.hycleo.keypad.util.KeypadSearchManager;

/**
 * Created by chenhanyu on 15/9/19.
 */
public class KeypadView extends LinearLayout {

    private final int[] btnIds = {R.id.btn_00, R.id.btn_01, R.id.btn_02, R.id.btn_03, R.id.btn_04, R.id.btn_05, R.id.btn_06, R.id.btn_07, R.id.btn_08, R.id.btn_09,
            R.id.btn_10, R.id.btn_11, R.id.btn_12, R.id.btn_13, R.id.btn_14, R.id.btn_15, R.id.btn_16, R.id.btn_17, R.id.btn_18, R.id.btn_19,
            R.id.btn_20, R.id.btn_21, R.id.btn_22, R.id.btn_23, R.id.btn_24, R.id.btn_25, R.id.btn_26, R.id.btn_27, R.id.btn_28, R.id.btn_29,
            R.id.btn_30, R.id.btn_31, R.id.btn_32, R.id.btn_33, R.id.btn_34, R.id.btn_35, R.id.btn_36, R.id.btn_37, R.id.btn_38, R.id.btn_39,
            R.id.btn_45};

    private List<KeypadItem> items;

    private Map<Button, KeypadItem> buttonMap;

    private LinearLayout resultView;

    private KeypadSearchManager keypadSearchManager;

    public KeypadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.keypad_view, this);
        resultView = (LinearLayout) findViewById(R.id.resultView);
        keypadSearchManager = new KeypadSearchManager(new KeypadSearchManager.InputContent() {
            @Override
            public List<String> getTableContent() {
                InputStream keyFileIs = getContext().getResources().openRawResource(R.raw.chineses);
                return FileUtil.readFile(keyFileIs);
            }
        });
        buttonMap = new HashMap<>();
        initFuntionKeyItem();
    }

    public void initFuntionKeyItem() {
        //TODO wait to implement all function key.
//        Button btnKeyToNum = (Button) findViewById(R.id.btn_40);
//        Button btnKeyToPage = (Button) findViewById(R.id.btn_41);
//        Button btnKeyComma = (Button) findViewById(R.id.btn_42);
//        Button btnKeyDot = (Button) findViewById(R.id.btn_43);
//        Button btnKeySpace = (Button) findViewById(R.id.btn_44);
        Button btnKeyBackspace = (Button) findViewById(R.id.btn_46);
        btnKeyBackspace.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (keypadSearchManager.removeCurrentKeyItemLast()) {
                    List<KeypadItem> results = keypadSearchManager.findResult();
                    refreshResultShow(results);
                }
            }
        });
//        Button btnKeyEnter = (Button) findViewById(R.id.btn_47);
    }

    public void initDefaultKeypadItem() {
        items = new ArrayList<>();
        String[] keypadItems = getContext().getResources().getStringArray(R.array.keypadItems);
        for (String keypadItem : keypadItems) {
            String id = keypadItem.split(" ")[0];
            String word = keypadItem.split(" ")[1];
            items.add(new KeypadItem(id, word));
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
                btn.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Button btn = (Button) v;
                        KeypadItem item = buttonMap.get(btn);
                        if (keypadSearchManager.addCurrentKeyItem(item)) {
                            List<KeypadItem> results = keypadSearchManager.findResult();
                            refreshResultShow(results);
                        }
                    }
                });
                buttonMap.put(btn, item);
            }
            index++;
        }
    }

    private void refreshResultShow(List<KeypadItem> items) {
        resultView.removeAllViews();
        if (items != null && items.size() > 0) {
            for (KeypadItem item : items) {
                Button btn = new Button(getContext());
                btn.setText(item.getWord());
                btn.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                btn.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Button btn = (Button) v;
                        Toast.makeText(getContext(), btn.getText(), Toast.LENGTH_SHORT).show();
                        keypadSearchManager.removeCurrentKey();
                        resultView.removeAllViews();
                    }
                });
                resultView.addView(btn);
            }
        } else if (keypadSearchManager.haveCurrentKey()) {
            Button btn = new Button(getContext());
            btn.setText(keypadSearchManager.getCurrentKeyWord());
            resultView.addView(btn);
        }
    }
}
