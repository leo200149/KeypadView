package tw.com.hycleo.keypad.model;

/**
 * Created by chenhanyu on 15/9/19.
 */
public class KeypadItem {

    private String id;

    private String word;

    private int weigth;

    private int wordSize;

    public KeypadItem() {

    }

    public KeypadItem(String id, String word) {
        this.id = id;
        this.word = word;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getWeigth() {
        return weigth;
    }

    public void setWeigth(int weigth) {
        this.weigth = weigth;
    }

    public int getWordSize() {
        return wordSize;
    }

    public void setWordSize(int wordSize) {
        this.wordSize = wordSize;
    }

}