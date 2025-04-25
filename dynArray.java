package cz.cuni.mff.java.example02;

public class DynArray implements MyCollection {

    private static final int PLUS = 10;
    private Object[] content = new Object[PLUS];
    private int count = 0;

    @Override
    public void add(Object o) {
        if (content.length == count){
            Object[] newContent = new Object[content.length + PLUS];
            for (int i=0; i < content.length; i++) {
                newContent[i] = content[i];
            }
            content = newContent;
        }
        content[count++] = o;
    }

    @Override
    public Object get(int i) {
        if (i >= 0 && i <count){
            return content[i];
        }
        return null;
    }

    @Override
    public void remove(Object o) {
        for (int i = 0; i < count; i++){
            if (content[i] == (o)){
                remove(i);
                return;
            }
        }
    }

    @Override
    public void remove(int i) {
        if ( i < 0 || i > count-1){
            return;
        }
        content[i] = null;
        for (int j = i+1; j < count; j++){
            content[j-1] = content[j];
        }
        content[count-1] = null;
        count--;
    }

    @Override
    public int get_size() {
        return count;
    }
}
