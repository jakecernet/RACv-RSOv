
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.Group;

/**
 * Segment with embedded JavaFX 7-segment visualization.
 */
public class Segment {
    private int stanje;

    Segment nasl, pred;

    private Pane view;
    private Rectangle[] seg = new Rectangle[7];

    public Segment() {
        stanje = 0;
        nasl = null;
        pred = null;
        initView();
    }

    public Segment(Segment pred) {
        stanje = 0;
        nasl = null;
        this.pred = pred;
        initView();
    }

    public int getStanje() {
        return stanje;
    }

    public void overflow() {

        stanje = 0;

        updateView();

        try {
            nasl.up();
        } catch (NullPointerException e) {

        } catch (MOverFlowException e) {
            System.out.println(e);
            nasl.overflow();
        }

    }

    public void up() throws MOverFlowException {
        stanje++;
        if (stanje > 9)
            throw new MOverFlowException();
        updateView();
    }

    public void down() {
        stanje--;
        if (stanje < 0) {
            // wrap and propagate to next (more significant) segment
            stanje = 9;
            try {
                nasl.down();
            } catch (NullPointerException e) {
                // nothing to propagate to
            }
        }
        updateView();
    }

    /**
     * Set this segment's value directly (0..9) and update view.
     */
    public void setValue(int v) {
        if (v < 0)
            v = 0;
        if (v > 9)
            v = 9;
        stanje = v;
        updateView();
    }

    public Pane getView() {
        return view;
    }

    private void initView() {
        double segLen = 40;
        double segTh = 8;
        double gap = 6;

        view = new Pane();
        view.setPrefSize(segLen + segTh * 2 + gap * 2, segLen * 2 + segTh * 3 + gap * 2);

        for (int i = 0; i < 7; i++) {
            Rectangle r = new Rectangle();
            r.setArcWidth(4);
            r.setArcHeight(4);
            r.setFill(Color.web("#330000"));
            seg[i] = r;
            view.getChildren().add(r);
        }

        double w = segLen;
        double t = segTh;
        double x0 = gap;
        double y0 = gap;

        // a: top horizontal
        seg[0].setX(x0 + t);
        seg[0].setY(y0);
        seg[0].setWidth(w);
        seg[0].setHeight(t);

        // b: top-right vertical
        seg[1].setX(x0 + t + w);
        seg[1].setY(y0 + t);
        seg[1].setWidth(t);
        seg[1].setHeight(w);

        // c: bottom-right vertical
        seg[2].setX(x0 + t + w);
        seg[2].setY(y0 + t + w + t);
        seg[2].setWidth(t);
        seg[2].setHeight(w);

        // d: bottom horizontal
        seg[3].setX(x0 + t);
        seg[3].setY(y0 + t + w + t + w);
        seg[3].setWidth(w);
        seg[3].setHeight(t);

        // e: bottom-left vertical
        seg[4].setX(x0);
        seg[4].setY(y0 + t + w + t);
        seg[4].setWidth(t);
        seg[4].setHeight(w);

        // f: top-left vertical
        seg[5].setX(x0);
        seg[5].setY(y0 + t);
        seg[5].setWidth(t);
        seg[5].setHeight(w);

        // g: middle horizontal
        seg[6].setX(x0 + t);
        seg[6].setY(y0 + t + w);
        seg[6].setWidth(w);
        seg[6].setHeight(t);

        updateView();
    }

    private static final boolean[][] DIGITS = {
            { true, true, true, true, true, true, false }, // 0
            { false, true, true, false, false, false, false }, // 1
            { true, true, false, true, true, false, true }, // 2
            { true, true, true, true, false, false, true }, // 3
            { false, true, true, false, false, true, true }, // 4
            { true, false, true, true, false, true, true }, // 5
            { true, false, true, true, true, true, true }, // 6
            { true, true, true, false, false, false, false }, // 7
            { true, true, true, true, true, true, true }, // 8
            { true, true, true, true, false, true, true } // 9
    };

    private void updateView() {
        int d = stanje;
        if (d < 0 || d > 9)
            return;
        for (int i = 0; i < 7; i++) {
            seg[i].setFill(DIGITS[d][i] ? Color.BLACK : Color.WHITE);
        }
    }

    public void setNasl(Segment nasl) {
        this.nasl = nasl;
    }

    public void setPrev(Segment pred) {
        this.pred = pred;
    }

    public String toString() {
        return "" + stanje;
    }

}
