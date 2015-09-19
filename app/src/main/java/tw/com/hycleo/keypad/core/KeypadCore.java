package tw.com.hycleo.keypad.core;

import android.content.Context;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import tw.com.hycleo.keypad.R;
import tw.com.hycleo.keypad.util.FileUtil;

/**
 * Created by chenhanyu on 15/9/19.
 */
public class KeypadCore {

    public static Map<String,Integer[]> indexMap = new HashMap<String, Integer[]>();

    private Context context;

    private List<String> tableContent;

    private String currentKey;

    public KeypadCore(Context context){
        this.context = context;
        currentKey = "";
        loadTableFile();
    }

    public void loadTableFile(){
        InputStream is = context.getResources().openRawResource(R.raw.chineses);
        this.tableContent = FileUtil.readFile(is);
    }

    public List<String> find(String key){
        List<String> results = new ArrayList<>();
        if(key!=null && key.length()>0) {
            List<String> content = tableContent;
            Integer[] indexs = indexMap.get(key);
            if(key.startsWith(currentKey)){
                indexs = indexMap.get(currentKey);
            }
            if (indexs != null) {
                results = content.subList(indexs[0], indexs[1]);
            } else {
                results = find(content, key);
            }
        }
        return results;
    }

    public List<String> find(List<String>content,String key){
        List<String> results = new ArrayList<>();
        Integer[] indexs = new Integer[2];
        Iterator<String> it = content.iterator();
        int index = 0;
        while(it.hasNext()){
            String value = it.next();
            if(value.startsWith(key)){
                results.add(value);
                if(indexs[0]==null){
                    indexs[0] = index;
                }
            }else{
                if(indexs[0]!=null){
                    indexs[1] = index-1;
                    indexMap.put(key, indexs);
                    break;
                }
            }
            index++;
        }
        return results;
    }
}
