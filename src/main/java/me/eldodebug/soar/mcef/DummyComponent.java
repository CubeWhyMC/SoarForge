package me.eldodebug.soar.mcef;

import java.awt.*;

public class DummyComponent extends Component {

    private static final long serialVersionUID = 1L;

    @Override
    public Point getLocationOnScreen() {
        return new Point(0, 0);
    }
}