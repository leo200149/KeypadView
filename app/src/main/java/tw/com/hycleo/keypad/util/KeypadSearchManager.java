package tw.com.hycleo.keypad.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import tw.com.hycleo.keypad.model.KeypadItem;

/**
 * Created by chenhanyu on 15/9/19.
 */
public class KeypadSearchManager {

    public static Map<String, MappingIndex> indexMap = new HashMap<>();

    private List<String> tableContent;

    private List<KeypadItem> currentKeyItems;

    public KeypadSearchManager(InputContent content) {
        this.tableContent = content.getTableContent();
        currentKeyItems = new ArrayList<>();
    }

    public boolean addCurrentKeyItem(KeypadItem item) {
        boolean result = false;
        if (currentKeyItems.size() < 4) {
            currentKeyItems.add(item);
            result = true;
        }
        return result;
    }

    public boolean removeCurrentKeyItemLast() {
        boolean result = false;
        if (currentKeyItems.size() > 0) {
            currentKeyItems.remove(currentKeyItems.size() - 1);
            result = true;
        }
        return result;
    }

    public boolean haveCurrentKey(){
        return currentKeyItems.size()>0;
    }

    public void removeCurrentKey(){
        currentKeyItems.clear();
    }

    public String getCurrentKeyId() {
        StringBuilder str = new StringBuilder();
        for (KeypadItem item : currentKeyItems) {
            str.append(item.getId());
        }
        return str.toString();
    }

    public String getCurrentKeyWord() {
        StringBuilder str = new StringBuilder();
        for (KeypadItem item : currentKeyItems) {
            str.append(item.getWord());
        }
        return str.toString();
    }


    public List<KeypadItem> findResult() {
        List<String> results = new ArrayList<>();
        String key = getCurrentKeyId();
        if (key != null && key.length() > 0) {
            List<String> content = tableContent;
            MappingIndex mappingIndex = indexMap.get(key);
            if (key.startsWith(key)) {
                mappingIndex = indexMap.get(key);
            }
            if (mappingIndex != null) {
                results = content.subList(mappingIndex.startIndex, mappingIndex.endIndex);
            } else {
                results = find(content, key);
            }
        }
        return convertResultsToItem(results);
    }

    private List<String> find(List<String> content, String key) {
        List<String> results = new ArrayList<>();
        MappingIndex mappingIndex = new MappingIndex();
        Iterator<String> it = content.iterator();
        int index = 0;
        while (it.hasNext()) {
            String value = it.next();
            if (value.startsWith(key)) {
                results.add(value);
                if (mappingIndex.startIndex == null) {
                    mappingIndex.startIndex = index;
                }
            } else {
                if (mappingIndex.startIndex != null) {
                    mappingIndex.endIndex = index - 1;
                    indexMap.put(key, mappingIndex);
                    break;
                }
            }
            index++;
        }
        return results;
    }

    private List<KeypadItem> convertResultsToItem(List<String> results) {
        List<KeypadItem> items = new ArrayList<>();
        for (String result : results) {
            String id = result.split(" ")[0];
            String word = result.split(" ")[1];
            KeypadItem item = new KeypadItem(id, word);
            items.add(item);
        }
        return items;
    }

    public static interface InputContent {
        public List<String> getTableContent();
    }

    private class MappingIndex{
        Integer startIndex;
        Integer endIndex;
    }
}
