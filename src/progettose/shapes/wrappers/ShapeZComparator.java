package progettose.shapes.wrappers;

import java.util.Comparator;

public class ShapeZComparator implements Comparator<ShapeInterface> {

    @Override
    public int compare(ShapeInterface t, ShapeInterface t1) {
        if (t.getZ() > t1.getZ()) {
            return 1;
        } else if (t.getZ() < t1.getZ()) {
            return -1;
        }
        return 0;
    }

}
