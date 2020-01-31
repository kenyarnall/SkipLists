import java.util.ArrayList;

public class USet {
    private ArrayList<Integer> data = new ArrayList<>();

    public void add(int x) {
        if (find(x) == null)
            data.add(x);
    }

    public Integer remove(int x) {
        for (int i = 0; i < data.size(); ++i)
            if (x == data.get(i)) {
                int ret = data.get(i);
                data.set(i, data.size()-1);
                data.remove(data.size()-1);
                return ret;
            }

        return null;
    }

    public Integer find(int x) {
        for (var y: data)
            if (y == x)
                return y;
        return null;
    }
}
