package utils;


import java.io.PrintWriter;

public class TermControl {

    public static final int RESET = 0;
    public static final int BLACK = 30;
    public static final int RED = 31;
    public static final int GREEN = 32;
    public static final int YELLOW = 33;
    public static final int BLUE = 34;
    public static final int PURPLE = 35;
    public static final int CYAN = 36;
    public static final int WHITE = 37;

    PrintWriter out;

    public TermControl(PrintWriter out) {
        this.out = out;
    }

    public void set_color(int color) {
        out.print(String.format("\u001b[%d;1m", color));
        out.flush();
    }

    public void use_red() {
        set_color(RED);
    }

    public void use_green() {
        set_color(GREEN);
    }

    public void use_blue() {
        set_color(BLUE);
    }

    public void use_yellow() {
        set_color(YELLOW);
    }

    public void use_cyan() {
        set_color(CYAN);
    }

    public void use_black() {
        set_color(BLACK);
    }

    public void use_purple() {
        set_color(PURPLE);
    }

    public void use_white() {
        set_color(WHITE);
    }

    public void reset() {
        set_color(RESET);
    }
}
